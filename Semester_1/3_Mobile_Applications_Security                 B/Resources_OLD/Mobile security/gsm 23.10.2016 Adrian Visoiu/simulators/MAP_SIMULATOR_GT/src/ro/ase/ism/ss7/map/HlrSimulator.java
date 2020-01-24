package ro.ase.ism.ss7.map;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.mobicents.protocols.ss7.indicator.NatureOfAddress;
import org.mobicents.protocols.api.IpChannelType;
import org.mobicents.protocols.sctp.ManagementImpl;
import org.mobicents.protocols.ss7.indicator.RoutingIndicator;
import org.mobicents.protocols.ss7.m3ua.ExchangeType;
import org.mobicents.protocols.ss7.m3ua.Functionality;
import org.mobicents.protocols.ss7.m3ua.IPSPType;
import org.mobicents.protocols.ss7.m3ua.Asp;
import org.mobicents.protocols.ss7.m3ua.M3UAManagement;
import org.mobicents.protocols.ss7.m3ua.impl.M3UAManagementImpl;
import org.mobicents.protocols.ss7.m3ua.parameter.RoutingContext;
import org.mobicents.protocols.ss7.m3ua.parameter.TrafficModeType;
import org.mobicents.protocols.ss7.map.MAPStackImpl;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContext;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContextName;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContextVersion;
import org.mobicents.protocols.ss7.map.api.MAPDialog;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPMessage;
import org.mobicents.protocols.ss7.map.api.MAPProvider;
import org.mobicents.protocols.ss7.map.api.datacoding.CBSDataCodingScheme;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAbortProviderReason;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAbortSource;
import org.mobicents.protocols.ss7.map.api.dialog.MAPNoticeProblemDiagnostic;
//import org.mobicents.protocols.ss7.map.api.dialog.MAPProviderError;
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
import org.mobicents.protocols.ss7.map.datacoding.CBSDataCodingSchemeImpl;
import org.mobicents.protocols.ss7.sccp.OriginationType;
import org.mobicents.protocols.ss7.sccp.RuleType;
import org.mobicents.protocols.ss7.sccp.SccpResource;
import org.mobicents.protocols.ss7.sccp.impl.SccpStackImpl;
import org.mobicents.protocols.ss7.sccp.impl.router.Mtp3Destination;
import org.mobicents.protocols.ss7.sccp.impl.router.Mtp3ServiceAccessPoint;
import org.mobicents.protocols.ss7.sccp.parameter.GlobalTitle;
import org.mobicents.protocols.ss7.sccp.parameter.SccpAddress;
import org.mobicents.protocols.ss7.tcap.TCAPStackImpl;
import org.mobicents.protocols.ss7.tcap.api.TCAPStack;
import org.mobicents.protocols.ss7.tcap.asn.ApplicationContextName;
import org.mobicents.protocols.ss7.tcap.asn.comp.Problem;

public class HlrSimulator extends BaseConfiguration {

	private static Logger logger = Logger.getLogger(HlrSimulator.class);

	private static final int PROCESS_USS_REQ_SENT = 1;
	private static final int MENU_RECEIVED = 2;
	private static final int CHALLANGE_ASKED = 3;
	private static final int RESPONSE_RECEIVED = 4;
	private int state = -1;
	
	protected SccpAddress SCCP_CLIENT_ADDRESS = null;
    protected SccpAddress SCCP_SERVER_ADDRESS = null;

	// SCTP
	private ManagementImpl sctpManagement;

	// M3UA
	private M3UAManagementImpl clientM3UAMgmt;

	// SCCP
	private SccpStackImpl sccpStack;
	private SccpResource sccpResource;

	// TCAP
	private TCAPStack tcapStack;

	// MAP
	private MAPStackImpl mapStack;
	private MAPProvider mapProvider;

	private MAPDialogSupplementary mapDialog;

	/**
         *
         */
	public HlrSimulator() {
		// initialize addresses for sccr routing
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

	protected void initializeStack(IpChannelType ipChannelType)
			throws Exception {

		this.initSCTP(ipChannelType);

		// Initialize M3UA first
		this.initM3UA();

		// Initialize SCCP
		this.initSCCP();

		// Initialize TCAP
		this.initTCAP();

		// Initialize MAP
		this.initMAP();

		// FInally start ASP
		// Set 5: Finally start ASP
		this.clientM3UAMgmt.startAsp("ASP1");
	}

	private void initSCTP(IpChannelType ipChannelType) throws Exception {
		logger.debug("Initializing SCTP Stack ....");
		this.sctpManagement = new ManagementImpl("Client");
		this.sctpManagement.setSingleThread(true);
		this.sctpManagement.setConnectDelay(10000);
		this.sctpManagement.start();
		this.sctpManagement.removeAllResourses();

		// 1. Create SCTP Association
		sctpManagement.addAssociation(CLIENT_IP, CLIENT_PORT, SERVER_IP,
				SERVER_PORT, CLIENT_ASSOCIATION_NAME, ipChannelType, null);
		logger.debug("Initialized SCTP Stack ....");
	}

	private void initM3UA() throws Exception {
		logger.debug("Initializing M3UA Stack ....");
		this.clientM3UAMgmt = new M3UAManagementImpl("Client");
		this.clientM3UAMgmt.setTransportManagement(this.sctpManagement);
		this.clientM3UAMgmt.start();
		// this.clientM3UAMgmt.removeAllResourses();

		// m3ua as create rc <rc> <ras-name>
		RoutingContext rc = factory.createRoutingContext(new long[] { 100l });
		TrafficModeType trafficModeType = factory
				.createTrafficModeType(TrafficModeType.Loadshare);
		// this.clientM3UAMgmt.createAs("AS1", Functionality.AS,
		// ExchangeType.SE, IPSPType.CLIENT, rc, trafficModeType,
		// 1, null);
		this.clientM3UAMgmt.createAs("AS1", Functionality.IPSP,
				ExchangeType.SE, IPSPType.CLIENT, rc, trafficModeType, 1, null);
		// Step 2 : Create ASP
		this.clientM3UAMgmt.createAspFactory("ASP1", CLIENT_ASSOCIATION_NAME);

		// Step3 : Assign ASP to AS
		Asp asp = this.clientM3UAMgmt.assignAspToAs("AS1", "ASP1");

		// Step 4: Add Route. Remote point code is 2
		clientM3UAMgmt.addRoute(SERVET_SPC, -1, -1, "AS1");
		logger.debug("Initialized M3UA Stack ....");

	}

	private void initSCCP() {
		logger.debug("Initializing SCCP Stack ....");
		this.sccpStack = new SccpStackImpl("MapLoadClientSccpStack");
		this.sccpStack.setMtp3UserPart(1, this.clientM3UAMgmt);

		this.sccpStack.start();
		this.sccpStack.removeAllResourses();

		// RemoteSignalingPointCode rspc = new
		// RemoteSignalingPointCode(SERVET_SPC, 0, 0);
		// RemoteSubSystem rss = new RemoteSubSystem(SERVET_SPC, SSN, 0, false);
		// this.sccpStack.getSccpResource().addRemoteSpc(0, rspc);
		// this.sccpStack.getSccpResource().addRemoteSsn(0, rss);

		try {
			this.sccpStack.getSccpResource().addRemoteSpc(0, SERVET_SPC, 0, 0);
			this.sccpStack.getSccpResource().addRemoteSsn(0, SERVET_SPC, SSN,
					0, false);

			// Mtp3ServiceAccessPoint sap = new Mtp3ServiceAccessPoint(1,
			// CLIENT_SPC, NETWORK_INDICATOR);
			// Mtp3Destination dest = new Mtp3Destination(SERVET_SPC,
			// SERVET_SPC, 0, 255, 255);
			// this.sccpStack.getRouter().addMtp3ServiceAccessPoint(1, sap);
			// this.sccpStack.getRouter().addMtp3Destination(1, 1, dest);

			this.sccpStack.getRouter().addMtp3ServiceAccessPoint(1, 1,
					CLIENT_SPC, NETWORK_INDICATOR);
			this.sccpStack.getRouter().addMtp3Destination(1, 1, SERVET_SPC,
					SERVET_SPC, 0, 255, 255);
			
			//GT RULES
	        this.sccpStack.getRouter().addRoutingAddress(1, SCCP_CLIENT_ADDRESS);
	        this.sccpStack.getRouter().addRoutingAddress(2, SCCP_SERVER_ADDRESS);
	        
	        String mask = "K";	        
	        this.sccpStack.getRouter().addRule(1, RuleType.Solitary,  null, OriginationType.All, SCCP_CLIENT_ADDRESS,mask,
	                1,1, null);
	        this.sccpStack.getRouter().addRule(2, RuleType.Solitary,  null, OriginationType.All, SCCP_SERVER_ADDRESS,mask,
	                2,2, null);
		} catch (Exception e) {
			logger.error("Failed to configure");
			e.printStackTrace();
		}
		logger.debug("Initialized SCCP Stack ....");
	}

	private void initTCAP() {
		logger.debug("Initializing TCAP Stack ....");
		this.tcapStack = new TCAPStackImpl(this.sccpStack.getSccpProvider(),
				SSN);
		this.tcapStack.setDialogIdleTimeout(60000);
		this.tcapStack.setInvokeTimeout(30000);
		this.tcapStack.setMaxDialogs(2000);
		this.tcapStack.start();
		logger.debug("Initialized TCAP Stack ....");
	}

	private void initMAP() {
		logger.debug("Initializing MAP Stack ....");
		// this.mapStack = new MAPStackImpl(this.sccpStack.getSccpProvider(),
		// SSN);
		this.mapStack = new MAPStackImpl(this.tcapStack.getProvider());
		this.mapProvider = this.mapStack.getMAPProvider();

		this.mapProvider.addMAPDialogListener(this);
		this.mapProvider.getMAPServiceSupplementary().addMAPServiceListener(
				this);

		this.mapProvider.getMAPServiceSupplementary().acivate();

		this.mapStack.start();
		logger.debug("Initialized MAP Stack ....");
	}

	private void initiateUSSD() throws MAPException {

		int ussdDataCodingSchemeCode = 0x0f;
		CBSDataCodingScheme ussdDataCodingScheme = new CBSDataCodingSchemeImpl(
				ussdDataCodingSchemeCode);

		// USSD String: *125*+31628839999#
		// The Charset is null, here we let system use default Charset (UTF-7 as
		// explained in GSM 03.38. However if MAP User wants, it can set its own
		// impl of Charset
		USSDString ussdString = this.mapProvider.getMAPParameterFactory()
				.createUSSDString("*125*+31628839999#");

		ISDNAddressString msisdn = this.mapProvider.getMAPParameterFactory()
				.createISDNAddressString(AddressNature.international_number,
						NumberingPlan.ISDN, "31628838002");

		ISDNAddressString msisdn2 = this.mapProvider.getMAPParameterFactory()
				.createISDNAddressString(AddressNature.international_number,
						NumberingPlan.ISDN, "31628838020");

		mapDialog = this.mapProvider
				.getMAPServiceSupplementary()
				.createNewDialog(
						MAPApplicationContext.getInstance(
								MAPApplicationContextName.networkUnstructuredSsContext,
								MAPApplicationContextVersion.version2),
						SCCP_CLIENT_ADDRESS, msisdn, SCCP_SERVER_ADDRESS,
						msisdn2);
		mapDialog.addProcessUnstructuredSSRequest(ussdDataCodingScheme,
				ussdString, null, msisdn);

		// mapDialog.setLocalAddress(arg0)
		// This will initiate the TC-BEGIN with INVOKE component
		mapDialog.send();
		state = PROCESS_USS_REQ_SENT;
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
			logger.debug(String.format("DialogClose for Dialog=%d",
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

		if (state == MENU_RECEIVED || state == CHALLANGE_ASKED) {
			try {
				mapDialog.send();
			} catch (MAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			try {
				mapDialog.close(false);
			} catch (MAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			IMSI arg3, AddressString arg4) {
		if (logger.isDebugEnabled()) {
			logger.debug(String
					.format("onDialogRequest for DialogId=%d DestinationReference=%s OriginReference=%s ",
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
		// This error condition. Client should never receive the
		// ProcessUnstructuredSSRequestIndication
		logger.error(String
				.format("onProcessUnstructuredSSRequestIndication for Dialog=%d and invokeId=%d",
						procUnstrReqInd.getMAPDialog().getLocalDialogId(),
						procUnstrReqInd.getInvokeId()));
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

		if (logger.isDebugEnabled()) {
			try {
				logger.debug(String
						.format("Rx ProcessUnstructuredSSResponseIndication.  USSD String=%s",
								procUnstrResInd.getUSSDString().getString(null)));
			} catch (MAPException e) {
				logger.error("error decoding");
				e.printStackTrace();
			}
		}
		this.state = RESPONSE_RECEIVED;
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
		logger.error(String
				.format("onUnstructuredSSNotifyRequestIndication for Dialog=%d and invokeId=%d",
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
		logger.error(String
				.format("onUnstructuredSSNotifyResponseIndication for Dialog=%d and invokeId=%d",
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
		if (logger.isDebugEnabled()) {
			try {
				logger.debug(String.format(
						"Rx UnstructuredSSRequestIndication. USSD String=%s ",
						unstrReqInd.getUSSDString().getString(null)));
			} catch (MAPException e) {
				// TODO Auto-generated catch block
				logger.error("error decoding");
				e.printStackTrace();
			}
		}

		int oldState = this.state;
		if (oldState == PROCESS_USS_REQ_SENT)
			this.state = MENU_RECEIVED;
		if (oldState == MENU_RECEIVED)
			this.state = CHALLANGE_ASKED;
		MAPDialogSupplementary mapDialog = unstrReqInd.getMAPDialog();

		try {
			byte ussdDataCodingScheme = 0x0f;
			USSDString ussdString = null;

			if (this.state == MENU_RECEIVED)
				ussdString = this.mapProvider.getMAPParameterFactory()
						.createUSSDString("2");
			if (this.state == CHALLANGE_ASKED)
				ussdString = this.mapProvider.getMAPParameterFactory()
						.createUSSDString("1234");

			AddressString msisdn = this.mapProvider.getMAPParameterFactory()
					.createAddressString(AddressNature.international_number,
							NumberingPlan.ISDN, "31628838002");

			mapDialog.addUnstructuredSSResponse(unstrReqInd.getInvokeId(),
					unstrReqInd.getDataCodingScheme(), ussdString);

		} catch (MAPException e) {
			logger.error(String.format(
					"Error while sending UnstructuredSSResponse for Dialog=%d",
					mapDialog.getLocalDialogId()));
		}

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
		// This error condition. Client should never receive the
		// UnstructuredSSResponseIndication
		logger.error(String
				.format("onUnstructuredSSResponseIndication for Dialog=%d and invokeId=%d",
						unstrResInd.getMAPDialog().getLocalDialogId(),
						unstrResInd.getInvokeId()));
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

	public static void main(String args[]) {
		System.out.println("*************************************");
		System.out.println("***          SctpClient           ***");
		System.out.println("*************************************");
		IpChannelType ipChannelType = IpChannelType.SCTP;
		if (args.length >= 1 && args[0].toLowerCase().equals("tcp"))
			ipChannelType = IpChannelType.TCP;

		final HlrSimulator client = new HlrSimulator();

		try {
			client.initializeStack(ipChannelType);

			// Lets pause for 20 seconds so stacks are initialized properly
			Thread.sleep(30000);

			client.initiateUSSD();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDialogReject(MAPDialog arg0, MAPRefuseReason arg1,
			ApplicationContextName arg2, MAPExtensionContainer arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRejectComponent(MAPDialog arg0, Long arg1, Problem arg2,
			boolean arg3) {
		// TODO Auto-generated method stub

	}
}
