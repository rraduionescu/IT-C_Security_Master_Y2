-------------Contents of sat03nov2012:

UML.doc
 - introduction to UML 

Design Patterns.docx
 - design patterns explained with UML

OCRAServer design.docx
 - OCRAServer application described with UML

workspace
 - Eclipse workspace


---------------Preparations for running OCRAServer application:

working folder is
c:\design

extract eclipse.zip to c:\design (eclipse folder should be c:\design\eclipse)

Build OCRAServer and start from Eclipse. Watch logs in console.

Build OCRAClient.
Start a command prompt and move to bin folder:
>cd c:\design\workspace\OCRAClient\bin
start the client:
>java -cp . ism.design.otp.ocra.client.OCRAClient

Type in the messages to be sent to server:

successful case:
1001;adrian;1;ignore;ignore;

 server challange should be -->00000000

1001;adrian;1;ignore;237653;

server response should be "OK"

unsuccessful case:

1001;adrian;1;ignore;ignore;

 server challange should be -->00000000


1001;adrian;1;ignore;237654;

server response should be "NOK"

---------------Build your own eclipse
- start with java edition of Eclipse
- install modeling tools - should be able to create Papyrus(UML) projects and files