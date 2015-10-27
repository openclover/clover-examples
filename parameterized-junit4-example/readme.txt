Running parameterized-junit4-example from Ant:

    ant clean test-with-clover-listener report
    ant clean test-without-clover-listener report
    ant clean validate

Running parameterized-junit4-example from Maven:

    mvn clean clover2:setup test clover2:clover
    mvn clean clover2:setup test clover2:clover verifier:verify
