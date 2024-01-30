First Running parameterized-junit4-example from Maven:

    mvn clean clover:setup test clover:clover
    mvn clean clover:setup test clover:clover verifier:verify

Then Running parameterized-junit4-example from Ant:

On windows like this
    ant -lib  %HOME%'\.m2\repository\org\openclover\clover\4.5.0' clean test-with-clover-listener report
    ant -lib  %HOME%'\.m2\repository\org\openclover\clover\4.5.0' clean test-without-clover-listener report
    ant -lib  %HOME%'\.m2\repository\org\openclover\clover\4.5.0' clean validate

On unix or mac like this
    ant -lib  ~/.m2/repository/org/openclover/clover/4.5.0  clean test-with-clover-listener report
    ant -lib  ~/.m2/repository/org/openclover/clover/4.5.0  clean test-without-clover-listener report
    ant -lib  ~/.m2/repository/org/openclover/clover/4.5.0  clean validate
    
Note: "ant validate" will be failed, because the result is found in an other path ... (take a look into validate.xml)