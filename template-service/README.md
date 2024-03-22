**Steps for setting up a microservice from this template service**

_Steps:_

1. From your IDE, refactor the parent package from com.microservices.templateservice to com.microservices.[the name of your service]
5. In the application.properties, change the application name and the database to your set database for the service.
6. Refactor the name of your main application, change it from TemplateServiceApplication to [Name]ServiceApplication
7. In the pom file, edit the artifactId and any other prefered changes

_Other Notes:_

1. The Template Controller has end-points that connect to the user proxy which communicates to the user service
2. Set up your models, the Date Audit and UserDateAudit are supported
3. Authentication in communicating with other services is configured and works well
   
