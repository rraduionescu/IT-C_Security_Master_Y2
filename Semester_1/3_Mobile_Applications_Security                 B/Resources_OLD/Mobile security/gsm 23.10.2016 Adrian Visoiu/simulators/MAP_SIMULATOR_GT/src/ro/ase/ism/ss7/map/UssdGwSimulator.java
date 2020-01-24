package ro.ase.ism.ss7.map;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.mobicents.protocols.api.IpChannelType;
import org.mobicents.protocols.sctp.ManagementImpl;
import org.mobicents.protocols.ss7.indicator.NatureOfAddress;
import org.mobicents.protocols.ss7.indicator.RoutingIndicator;
import org.mobicents.protocols.ss7.m3ua.ExchangeType;
import org.mobicents.protocols.ss7.m3ua.Functionality;
import org.mobicents.protocols.ss7.m3ua.IPSPType;
import org.mobicents.protocols.ss7.m3ua.As;
import org.mobicents.protocols.ss7.m3ua.Asp;
import org.mobicents.protocols.ss7.m3ua.AspFactory;
import org.mobicents.protocols.ss7.m3ua.impl.M3UAManagementImpl;
import org.mobicents.protocols.ss7.m3ua.M3UAManagement;
import org.mobicents.protocols.ss7.m3ua.parameter.RoutingContext;
import org.mobicents.protocols.ss7.m3ua.parameter.TrafficModeType;
import org.mobicents.protocols.ss7.map.MAPStackImpl;
import org.mobicents.protocols.ss7.map.api.MAPDialog;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPMessage;
import org.mobicents.protocols.ss7.map.api.MAPProvider;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAbortProviderReason;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAbortSource;
import org.mobicents.protocols.ss7.map.api.dialog.MAPNoticeProblemDiagnostic;
import org.mobicents.protocols.ss7.map.api.dialog.MAPRefuseReason;
import org.mobicents.protocols.ss7.map.api.dialog.MAPUserAbortChoice;
import org.mobicents.protocols.ss7.map.api.errors.MAPErrorMessage;
import org.mobicents.protocols.ss7.map.api.primitives.AddressNature;
import org.mobicents.protocols.ss7.map.api.primitives.AddressString;
import org.mobicents.protocols.ss7.map.api.primitives.IMSI;
import org.mobicents.protocols.ss7.map.api.primitives.ISDNAddressString;
import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.primitives.NumberingPlan;
import org.mobicents.protocols.ss7.map.api.primitives.USSDString;
import org.mobicents.protocols.ss7.map.api.service.supplementary.MAPDialogSupplementary;
import org.mobicents.protocols.ss7.map.api.service.supplementary.ProcessUnstructuredSSRequest;
import org.mobicents.protocols.ss7.map.api.service.supplementary.ProcessUnstructuredSSResponse;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyRequest;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyResponse;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSRequest;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSResponse;
import org.mobicents.protocols.ss7.sccp.OriginationType;
import org.mobicents.protocols.ss7.sccp.RuleType;
//import org.mobicents.protocols.ss7.sccp.impl.RemoteSignalingPointCode;
//import org.mobicents.protocols.ss7.sccp.impl.RemoteSubSystem;
import org.mobicents.protocols.ss7.sccp.SccpResource;
import org.mobicents.protocols.ss7.sccp.impl.SccpStackImpl;
import org.mobicents.protocols.ss7.sccp.impl.router.Mtp3Destination;
import org.mobicents.protocols.ss7.sccp.impl.router.Mtp3ServiceAccessPoint;
import org.mobicents.protocols.ss7.sccp.parameter.GlobalTitle;
import org.mobicents.protocols.ss7.sccp.parameter.SccpAddress;
import org.mobicents.protocols.ss7.tcap.asn.ApplicationContextName;
import org.mobicents.protocols.ss7.tcap.asn.comp.Problem;

public class UssdGwSimulator extends BaseConfiguration {

	private static Logger logger = Logger.getLogger(UssdGwSimulator.class);

	private static final int PROCESS_USS_REQ_RECEIVED = 1;
	private static final int OPTION_RECEIVED = 2;
	private static final int CHALLANGE_RECEIVED = 3;
	private int state = -1;

	// SCTP
	private ManagementImpl sctpManagement;

	// M3UA
	private M3UAManagementImpl serverM3UAMgmt;

	// SCCP
	private SccpStackImpl sccpStack;
	private SccpResource sccpResource;

	// MAP
	private MAPStackImpl mapStack;
	private MAPProvider mapProvider;
	
	protected SccpAddress SCCP_CLIENT_ADDRESS = null;
    protected SccpAddress SCCP_SERVER_ADDRESS = null;
	
	public UssdGwSimulator() {
		super();
		
		try {
			SCCP_CLIENT_ADDRESS = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE,
                    CLIENT_SPC, GlobalTitle.getInstance(0,
                            org.mobicents.protocols.ss7.indicator.NumberingPlan.valueOf(1),
                            NatureOfAddress.valueOf(4), "40766000100"), SSN);
            SCCP_SERVER_ADDRESS = new SccpAddress(RoutingIndicator.ROUTING_BASED_ON_GLOBAL_TITLE,
                    SERVET_SPC, GlobalTitle.getInstance(0,
                            org.mobicents.protocols.ss7.indicator.NumberingPlan.valueOf(1), NatureOfAddress.valueOf(4), "40766000200"), SSN);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	private void initSCTP(IpChannelType ipChannelType) throws Exception {
		logger.debug("Initializing SCTP Stack ....");
		this.sctpManagement = new ManagementImpl("Server");
		this.sctpManagement.setSingleThread(true);
		this.sctpManagement.setConnectDelay(10000);
		this.sctpManagement.start();
		this.sctpManagement.removeAllResourses();

		// 1. Create SCTP Server
		sctpManagement.addServer(SERVER_NAME, SERVER_IP, SERVER_PORT,
				ipChannelType, null);

		// 2. Create SCTP Server Association
		sctpManagement.addServerAssociation(CLIENT_IP, CLIENT_PORT,
				SERVER_NAME, SERVER_ASSOCIATION_NAME, ipChannelType);

		// 3. Start Server
		sctpManagement.startServer(SERVER_NAME);
		logger.debug("Initialized SCTP Stack ....");
	}

	private void initM3UA() throws Exception {
		logger.debug("Initializing M3UA Stack ....");
		this.serverM3UAMgmt = new M3UAManagementImpl("Server");
		this.serverM3UAMgmt.setTransportManagement(this.sctpManagement);
		this.serverM3UAMgmt.start();
		this.serverM3UAMgmt.removeAllResourses();

		// Step 1 : Create App Server

		RoutingContext rc = factory.createRoutingContext(new long[] { 100l });
		TrafficModeType trafficModeType = factory
				.createTrafficModeType(TrafficModeType.Loadshare);
		// As as = this.serverM3UAMgmt.createAs("RAS1", Functionality.SGW,
		// ExchangeType.SE, IPSPType.CLIENT, rc,
		// trafficModeType, 1, null);
		As as = this.serverM3UAMgmt.createAs("RAS1", Functionality.IPSP,
				ExchangeType.SE, IPSPType.SERVER, rc, trafficModeType, 1, null);

		// Step 2 : Create ASP
		AspFactory aspFactor = this.serverM3UAMgmt.createAspFactory("RASP1",
				SERVER_ASSOCIATION_NAME);

		// Step3 : Assign ASP to AS
		Asp asp = this.serverM3UAMgmt.assignAspToAs("RAS1", "RASP1");

		// Step 4: Add Route. Remote point code is 2
		this.serverM3UAMgmt.addRoute(CLIENT_SPC, -1, -1, "RAS1");
		logger.debug("Initialized M3UA Stack ....");
	}

	private void initSCCP() {
		logger.debug("Initializing SCCP Stack ....");
		this.sccpStack = new SccpStackImpl("MapLoadServerSccpStack");
		this.sccpStack.setMtp3UserPart(1, this.serverM3UAMgmt);

		this.sccpStack.start();
		this.sccpStack.removeAllResourses();

		try {
			// RemoteSignalingPointCode rspc = new
			// RemoteSignalingPointCode(CLIENT_SPC, 0, 0);
			// RemoteSubSystem rss = new RemoteSubSystem(CLIENT_SPC, SSN, 0,
			// false);
			// this.sccpStack.getSccpResource().addRemoteSpc(0, rspc);
			// this.sccpStack.getSccpResource().addRemoteSsn(0, rss);

			this.sccpStack.getSccpResource().addRemoteSpc(0, CLIENT_SPC, 0, 0);
			this.sccpStack.getSccpResource().addRemoteSsn(0, CLIENT_SPC, SSN,
					0, false);

			// Mtp3ServiceAccessPoint sap = new Mtp3ServiceAccessPoint(1,
			// SERVET_SPC, NETWORK_INDICATOR);
			// Mtp3Destination dest = new Mtp3Destination(CLIENT_SPC,
			// CLIENT_SPC, 0, 255, 255);
			// this.sccpStack.getRouter().addMtp3ServiceAccessPoint(1, sap);
			// this.sccpStack.getRouter().addMtp3Destination(1, 1, dest);

			this.sccpStack.getRouter().addMtp3ServiceAccessPoint(1, 1,
					SERVET_SPC, NETWORK_INDICATOR);
			this.sccpStack.getRouter().addMtp3Destination(1, 1, CLIENT_SPC,
					CLIENT_SPC, 0, 255, 255);
			
			//GT RULES
	        this.sccpStack.getRouter().addRoutingAddress(1, SCCP_CLIENT_ADDRESS);
	        this.sccpStack.getRouter().addRoutingAddress(2, SCCP_SERVER_ADDRESS);
	        
	        String mask = "K";	        
	        this.sccpStack.getRouter().addRule(1, RuleType.Solitary,  null, OriginationType.All, SCCP_CLIENT_ADDRESS,mask,
	                1,1, null);
	        this.sccpStack.getRouter().addRule(2, RuleType.Solitary,  null, OriginationType.All, SCCP_SERVER_ADDRESS,mask,
	                2,2, null);
		} catch (Exception e) {
			logger.error("Error while configuring pc and ssn");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Initialized SCCP Stack ....");
	}

	private void initMAP() {
		logger.debug("Initializing MAP Stack ....");
		this.mapStack = new MAPStackImpl(this.sccpStack.getSccpProvider(), SSN);
		this.mapProvider = this.mapStack.getMAPProvider();

		this.mapProvider.addMAPDialogListener(this);
		this.mapProvider.getMAPServiceSupplementary().addMAPServiceListener(
				this);

		this.mapProvider.getMAPServiceSupplementary().acivate();

		this.mapStack.start();
		logger.debug("Initialized MAP Stack ....");
	}

	protected void initializeStack(IpChannelType ipChannelType)
			throws Exception {

		this.initSCTP(ipChannelType);

		// Initialize M3UA first
		this.initM3UA();

		// Initialize SCCP
		this.initSCCP();

		// Initialize MAP
		this.initMAP();

		// 7. Start ASP
		serverM3UAMgmt.startAsp("RASP1");

		logger.debug("[[[[[[[[[[    Started SctpServer       ]]]]]]]]]]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogAccept(
	 * org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
	 */
	public void onDialogAccept(MAPDialog mapDialog,
			MAPExtensionContainer extensionContainer) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format(
					"onDialogAccept for DialogId=%d MAPExtensionContainer=%s",
					mapDialog.getLocalDialogId(), extensionContainer));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogClose(org
	 * .mobicents.protocols.ss7.map.api.MAPDialog)
	 */
	public void onDialogClose(MAPDialog mapDialog) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("onDialogClose for Dialog=%d",
					mapDialog.getLocalDialogId()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogDelimiter
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog)
	 */
	public void onDialogDelimiter(MAPDialog mapDialog) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("onDialogDelimiter for DialogId=%d",
					mapDialog.getLocalDialogId()));
		}
		if (state == PROCESS_USS_REQ_RECEIVED || state == OPTION_RECEIVED) {
			try {
				mapDialog.send();
			} catch (MAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if (state == CHALLANGE_RECEIVED) {
		// try {
		// mapDialog.closeDelayed(true);
		// } catch (MAPException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogNotice(
	 * org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * org.mobicents.protocols.ss7.map.api.dialog.MAPNoticeProblemDiagnostic)
	 */
	public void onDialogNotice(MAPDialog mapDialog,
			MAPNoticeProblemDiagnostic noticeProblemDiagnostic) {
		logger.error(String
				.format("onDialogNotice for DialogId=%d MAPNoticeProblemDiagnostic=%s ",
						mapDialog.getLocalDialogId(), noticeProblemDiagnostic));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogProviderAbort
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * org.mobicents.protocols.ss7.map.api.dialog.MAPAbortProviderReason,
	 * org.mobicents.protocols.ss7.map.api.dialog.MAPAbortSource,
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
	 */
	public void onDialogProviderAbort(MAPDialog mapDialog,
			MAPAbortProviderReason abortProviderReason,
			MAPAbortSource abortSource, MAPExtensionContainer extensionContainer) {
		logger.error(String
				.format("onDialogProviderAbort for DialogId=%d MAPAbortProviderReason=%s MAPAbortSource=%s MAPExtensionContainer=%s",
						mapDialog.getLocalDialogId(), abortProviderReason,
						abortSource, extensionContainer));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogRelease
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog)
	 */
	public void onDialogRelease(MAPDialog mapDialog) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("onDialogResease for DialogId=%d",
					mapDialog.getLocalDialogId()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogRequest
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
	 * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
	 */
	public void onDialogRequest(MAPDialog mapDialog,
			AddressString destReference, AddressString origReference,
			MAPExtensionContainer extensionContainer) {
		if (logger.isDebugEnabled()) {
			logger.debug(String
					.format("onDialogRequest for DialogId=%d DestinationReference=%s OriginReference=%s MAPExtensionContainer=%s",
							mapDialog.getLocalDialogId(), destReference,
							origReference, extensionContainer));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogRequestEricsson
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
	 * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
	 * org.mobicents.protocols.ss7.map.api.primitives.IMSI,
	 * org.mobicents.protocols.ss7.map.api.primitives.AddressString)
	 */
	public void onDialogRequestEricsson(MAPDialog mapDialog,
			AddressString destReference, AddressString origReference,
			IMSI imsi, AddressString vlr) {
		if (logger.isDebugEnabled()) {
			logger.debug(String
					.format("onDialogRequestEricsson for DialogId=%d DestinationReference=%s OriginReference=%s ",
							mapDialog.getLocalDialogId(), destReference,
							origReference));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogTimeout
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog)
	 */
	public void onDialogTimeout(MAPDialog mapDialog) {
		logger.error(String.format("onDialogTimeout for DialogId=%d",
				mapDialog.getLocalDialogId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogUserAbort
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * org.mobicents.protocols.ss7.map.api.dialog.MAPUserAbortChoice,
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
	 */
	public void onDialogUserAbort(MAPDialog mapDialog,
			MAPUserAbortChoice userReason,
			MAPExtensionContainer extensionContainer) {
		logger.error(String
				.format("onDialogUserAbort for DialogId=%d MAPUserAbortChoice=%s MAPExtensionContainer=%s",
						mapDialog.getLocalDialogId(), userReason,
						extensionContainer));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
	 * MAPServiceSupplementaryListener
	 * #onProcessUnstructuredSSRequest(org.mobicents
	 * .protocols.ss7.map.api.service
	 * .supplementary.ProcessUnstructuredSSRequest)
	 */
	public void onProcessUnstructuredSSRequest(
			ProcessUnstructuredSSRequest procUnstrReqInd) {

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(String
						.format("onProcessUnstructuredSSRequestIndication for DialogId=%d. Ussd String=%s",
								procUnstrReqInd.getMAPDialog()
										.getLocalDialogId(), procUnstrReqInd
										.getUSSDString().getString(null)));
			}
			this.state = PROCESS_USS_REQ_RECEIVED;
			long invokeId = procUnstrReqInd.getInvokeId();

			USSDString ussdStrObj = this.mapProvider.getMAPParameterFactory()
					.createUSSDString(
							"USSD String : 1. OTP 2. Challange Response");
			byte ussdDataCodingScheme = (byte) 0x0F;
			MAPDialogSupplementary dialog = procUnstrReqInd.getMAPDialog();

			dialog.setUserObject(invokeId);

			ISDNAddressString msisdn = this.mapProvider
					.getMAPParameterFactory().createISDNAddressString(
							AddressNature.international_number,
							NumberingPlan.ISDN, "31628838002");

			dialog.addUnstructuredSSRequest(
					procUnstrReqInd.getDataCodingScheme(), ussdStrObj, null,
					msisdn);

		} catch (MAPException e) {
			logger.error("Error while sending UnstructuredSSRequest ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
	 * MAPServiceSupplementaryListener
	 * #onProcessUnstructuredSSResponse(org.mobicents
	 * .protocols.ss7.map.api.service
	 * .supplementary.ProcessUnstructuredSSResponse)
	 */
	public void onProcessUnstructuredSSResponse(
			ProcessUnstructuredSSResponse procUnstrResInd) {
		// Server shouldn't be getting ProcessUnstructuredSSResponseIndication
		logger.error(String
				.format("onProcessUnstructuredSSResponseIndication for Dialog=%d and invokeId=%d",
						procUnstrResInd.getMAPDialog().getLocalDialogId(),
						procUnstrResInd.getInvokeId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
	 * MAPServiceSupplementaryListener
	 * #onUnstructuredSSNotifyRequest(org.mobicents
	 * .protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyRequest)
	 */
	public void onUnstructuredSSNotifyRequest(
			UnstructuredSSNotifyRequest unstrNotifyInd) {
		// This error condition. Client should never receive the
		// UnstructuredSSNotifyRequestIndication
		logger.error(String.format(
				"onUnstructuredSSNotifyRequest for Dialog=%d and invokeId=%d",
				unstrNotifyInd.getMAPDialog().getLocalDialogId(),
				unstrNotifyInd.getInvokeId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
	 * MAPServiceSupplementaryListener
	 * #onUnstructuredSSNotifyResponse(org.mobicents
	 * .protocols.ss7.map.api.service
	 * .supplementary.UnstructuredSSNotifyResponse)
	 */
	public void onUnstructuredSSNotifyResponse(
			UnstructuredSSNotifyResponse unstrNotifyInd) {
		// This error condition. Client should never receive the
		// UnstructuredSSNotifyRequestIndication
		logger.error(String.format(
				"onUnstructuredSSNotifyResponse for Dialog=%d and invokeId=%d",
				unstrNotifyInd.getMAPDialog().getLocalDialogId(),
				unstrNotifyInd.getInvokeId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
	 * MAPServiceSupplementaryListener
	 * #onUnstructuredSSRequest(org.mobicents.protocols
	 * .ss7.map.api.service.supplementary.UnstructuredSSRequest)
	 */
	public void onUnstructuredSSRequest(UnstructuredSSRequest unstrReqInd) {
		// Server shouldn't be getting UnstructuredSSRequestIndication
		logger.error(String
				.format("onUnstructuredSSRequestIndication for Dialog=%d and invokeId=%d",
						unstrReqInd.getMAPDialog().getLocalDialogId(),
						unstrReqInd.getInvokeId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
	 * MAPServiceSupplementaryListener
	 * #onUnstructuredSSResponse(org.mobicents.protocols
	 * .ss7.map.api.service.supplementary.UnstructuredSSResponse)
	 */
	public void onUnstructuredSSResponse(UnstructuredSSResponse unstrResInd) {

		try {
			if (logger.isDebugEnabled()) {
				logger.debug(String
						.format("onUnstructuredSSResponseIndication for DialogId=%d Ussd String=%s",
								unstrResInd.getMAPDialog().getLocalDialogId(),
								unstrResInd.getUSSDString().getString(null)));
			}

			// we assume it's option 2 - challange response
			int oldState = state;
			if (oldState == PROCESS_USS_REQ_RECEIVED)
				this.state = OPTION_RECEIVED;
			if (oldState == OPTION_RECEIVED)
				this.state = CHALLANGE_RECEIVED;

			if (state == OPTION_RECEIVED) {
				USSDString ussdStrObj = this.mapProvider
						.getMAPParameterFactory().createUSSDString(
								"Enter challange please !");
				byte ussdDataCodingScheme = (byte) 0x0F;
				MAPDialogSupplementary dialog = unstrResInd.getMAPDialog();

				ISDNAddressString msisdn = this.mapProvider
						.getMAPParameterFactory().createISDNAddressString(
								AddressNature.international_number,
								NumberingPlan.ISDN, "31628838002");

				dialog.addUnstructuredSSRequest(
						unstrResInd.getDataCodingScheme(), ussdStrObj, null,
						msisdn);

			}
			if (state == CHALLANGE_RECEIVED) {
				USSDString ussdStrObj = this.mapProvider
						.getMAPParameterFactory().createUSSDString(
								"Response is 4321");
				byte ussdDataCodingScheme = (byte) 0x0F;
				MAPDialogSupplementary dialog = unstrResInd.getMAPDialog();

				AddressString msisdn = this.mapProvider
						.getMAPParameterFactory().createAddressString(
								AddressNature.international_number,
								NumberingPlan.ISDN, "31628838002");

				dialog.addProcessUnstructuredSSResponse(
						((Long) dialog.getUserObject()).longValue(),
						unstrResInd.getDataCodingScheme(), ussdStrObj);

				dialog.send();
				dialog.closeDelayed(false);
			}
		} catch (MAPException e) {
			logger.error("Error while sending UnstructuredSSRequest ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onErrorComponent
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog, java.lang.Long,
	 * org.mobicents.protocols.ss7.map.api.errors.MAPErrorMessage)
	 */
	public void onErrorComponent(MAPDialog mapDialog, Long invokeId,
			MAPErrorMessage mapErrorMessage) {
		logger.error(String
				.format("onErrorComponent for Dialog=%d and invokeId=%d MAPErrorMessage=%s",
						mapDialog.getLocalDialogId(), invokeId, mapErrorMessage));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onInvokeTimeout
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog, java.lang.Long)
	 */
	public void onInvokeTimeout(MAPDialog mapDialog, Long invokeId) {
		logger.error(String.format(
				"onInvokeTimeout for Dialog=%d and invokeId=%d",
				mapDialog.getLocalDialogId(), invokeId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onMAPMessage(org
	 * .mobicents.protocols.ss7.map.api.MAPMessage)
	 */
	public void onMAPMessage(MAPMessage arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.MAPServiceListener#
	 * onProviderErrorComponent(org.mobicents.protocols.ss7.map.api.MAPDialog,
	 * java.lang.Long,
	 * org.mobicents.protocols.ss7.map.api.dialog.MAPProviderError)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onRejectComponent
	 * (org.mobicents.protocols.ss7.map.api.MAPDialog, java.lang.Long,
	 * org.mobicents.protocols.ss7.tcap.asn.comp.Problem)
	 */
	public void onRejectComponent(MAPDialog mapDialog, Long invokeId,
			Problem problem) {
		logger.error(String.format(
				"onRejectComponent for Dialog=%d and invokeId=%d Problem=%s",
				mapDialog.getLocalDialogId(), invokeId, problem));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("*************************************");
		System.out.println("***          SctpServer           ***");
		System.out.println("*************************************");
		IpChannelType ipChannelType = IpChannelType.SCTP;
		if (args.length >= 1 && args[0].toLowerCase().equals("tcp"))
			ipChannelType = IpChannelType.TCP;

		final UssdGwSimulator server = new UssdGwSimulator();
		try {
			server.initializeStack(ipChannelType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDialogReject(MAPDialog paramMAPDialog,
			MAPRefuseReason paramMAPRefuseReason,
			ApplicationContextName paramApplicationContextName,
			MAPExtensionContainer paramMAPExtensionContainer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRejectComponent(MAPDialog paramMAPDialog, Long paramLong,
			Problem paramProblem, boolean paramBoolean) {
		// TODO Auto-generated method stub

	}

}
