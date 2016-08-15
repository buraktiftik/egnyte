Author: Burak Tiftik

Assumption: The example csv files had headers, but the expected outputs did not, thus it is assumed the outputs do not
require headers



Usage: Improperly formatted input parameters should print the usage to screen

Usage:Runner.java users.csv files.csv -top 3 c

arg[0]: users.csv file
arg[1]:files. csv file

optional:

--top N

last argument should be -c

Example:

java -jar egnyte-1.0-jar-with-dependencies.jar users.csv files.csv 
java -jar egnyte-1.0-jar-with-dependencies.jar users.csv files.csv --top 3
java -jar egnyte-1.0-jar-with-dependencies.jar users.csv files.csv -c
java -jar egnyte-1.0-jar-with-dependencies.jar users.csv files.csv --top 5 -c


Resource Files Must be in egnyte/src/main/resources

When executing the program no other path value is necessary ie. just type file names no path

Changes Log:

A class named AuditReporter was created to contain the methods and variables

Certain method visibility has been changed to protected to allow testing and access to other methods

The output of certain methods (printFile, printUserHeader, printHeader etc.) changed to String, so that they are
not hard coded to print to screen, instead now they can be used to print to screen or to csv file (or any other source
in the future) for better modularity 

Setters and Getters has been generated to allow for testing for files and users fields in AuditReporter

If the TOP N order is executed with too high value the program should throw an exception

For Maven built fat jar file the input for files must be in the same folder or given appropriate path
For testing in eclipse change the code in AuditReporter.java loadData method (indicated with caps comments), this will
allow Maven to read the data from resources folder as usual, or edit the arguments appropriately

The code should be more error resistant, various improper input problems have been handled.


Tests:

Basic creation tests are created called AuditReporterTest
Additional tests created including Exception Testing 




Build

Can be built easily with Maven
I will also upload a fatjar file to the github, as well as pretty much anything I have on my folder just to be extra sure

Fatjar file should read the input files in the same folder or with appropriate path (similar to example code)

egnyte-1.0-jar-with-dependencies.jar is the name of the fat-jar file. It is in the target folder.