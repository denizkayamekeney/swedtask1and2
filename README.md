# swedtask1and2
In this version of application I have created three controller.

Example Screens are in ReadMeScreens.doc.

Projects highligths: 
- Spring boot application created. 
- Maven Build tool
- JPA (Hibernate under it) for ORM.
- Build three controller (Vehicle, Coefficients, CoefficientGroup)
- Thymleaf template engine is used.
- Bootstrap/4.2.1/css/bootstrap.min.css css library is used. 
- For special font Font Awesome is used.
- As database H2 database is used.
- As an embedded server Tomcat is used
- Jupiter is used for test. (26 Test case are written)
- Validation is used for Vehicle and coefficient entities.
- Vehicle Data is parsing according to Vehicle Validation rules.
- Pagination and sorting according to column is added.
- For coefficient, I have created two entity which can hold every possible coefficient
  in any primitive wrapper type.
- Default port of the tomcat (8080 is using)
 

Execution Flow :
 - Run the Assignment-1.1-SNAPSHOT.jar file which is also includes embedded tomcat. 
 - When the jar runs ot is reading vehicle csv and data.json file 
   specified in application.properties file.
 - When an error occurs during parsing it is written to 
   csvErrorFile specified in application.properties file(csvErrors.dat)
   (parsing is happening according to Vehicle Validation rules.) 
 - http://localhost:8080/vehicles/findall (http://localhost:8080) is the default url.
 - Addition deletion casco calculation is working.
 - Addition deletion of coefficient are also working. 
 
 What is Left : 
  - Coefficient and coefficient group controller not connected to CoeficientsData bean.
  in casco calculation, CoeficientsData bean is using.
  
  - For some of the Vehicle data, the formula is creating minus result. 
  I especially did not do something. Normally I should have used exception for minus values.
  
  -  There need to add some extra test cases for robustness.
  
