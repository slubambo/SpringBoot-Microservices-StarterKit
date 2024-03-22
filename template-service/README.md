**Steps for setting up a microservice from this boiler plate**

_Pre-requistes:_

1. Clone and run the Naming Service Nexus
2. Clone and run the Gateway Service Nexus

_Steps:_

1. Create a Repository on github under the ucu organization
2. Clone the Repo to your PC
3. Copy the src and target folders, the gitignore and pom files from this repo to your repo
4. From your IDE, refactor the parent package from mis.microservices.canvaservice to mis.microservices.[the name of your service]
5. In the application.properties, change the application name change the database to your set database for the service. Change the port number too, preferable and 100 to the previous taken port
6. Refactor the name of your main application, change it from CanvaServiceApplication to [Name]ServiceApplication
7. In the pom file, edit the artifactId and any other prefered changes

_Other Notes:_

1. The Canva Controller has end-points that connect to the user proxy which communicates to the user service
2. Set up your models, the Date Audit and UserDateAudit are supported
3. Authentication in communicating with other services is configured and works well
   
