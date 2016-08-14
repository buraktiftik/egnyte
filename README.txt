Resource Files Must be in egnyte/src/main/resources

When executing the program no other path value is necessary ie. just type file names no path

Changes Log:

A class named AuditReporter was created to contain the methods and variables

Certain method visibility has been changed to protected to allow testing and access to other methods

The output of certain methods (printFile, printUserHeader, printHeader etc.) changed to String, so that they are
not hard coded to print to screen, instead now they can be used to print to screen or to csv file (or any other source
in the future) for better modularity 

Setters and Getters has been generated to allow for testing for files and users fields in AuditReporter


Tests:

