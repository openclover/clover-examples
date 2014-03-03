Running spock-example from Ant:

    ant clean
    ant report validate
    ant report-with-xml validate

Running spock-example from Gradle:

    gradle clean test cloverGenerateReport

Running spock-example from Maven:

    mvn clean clover2:setup test clover2:clover
