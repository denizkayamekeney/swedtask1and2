# swedtask1and2
Cli application for kasko calculation. It is developed without using spring framework. 
Dependency injection prensiple is used.

Projects highligths: 
- Maven Build tool is used.
- A basic ORM is written for handlicg vehicle entity.
- Build three controller (Vehicle, Coefficients, CoefficientGroup)
- As database H2 database is used.
- Jupiter is used for test. (26 Test case are written)
- For validation, basic parser validation is used for Vehicle and coefficients.
- Very basic exceptions is created and possible exceptions are wrapped by that. Written in AppException,
DbAppException, FileException. 
 
Execution Flow :
 - Run the Assignment-1.0-SNAPSHOT.jar file under executables directory. 
 Everyhing is included in this jar file for running the system.
  
 - It is creating a vehicles table first.
 - When the jar runs, it is reading vehicle csv and data.json file 
   specified in Constants file.
 - When an error occurs during parsing it is written to 
   csvErrorFile specified in application.properties file(csvErrors.dat)
 - After data is uploaded to database, with indemnity and without indemnity 
   is running and the result is written to CascoWithoutIndemnity.csv and CascoWithIndemnity.csv output files.
 What is Left : 
  - For some of the Vehicle data, the formula is creating minus result. 
  I especially did not do something. Normally I should have used exception for minus values.
  
  -  There need to add some extra test cases for robustness.

  - For skip condation I am returning minus -1 value from casco calculation. 
  I could have handled by throwing Exception instead.  
