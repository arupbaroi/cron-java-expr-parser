# Java Cron Expression Parser
## Overview

Parses Cron Expressions of the below format:

*   (Minute) (hour) (day of month) (month) (day of week) (command)
*   `*` means all possible time units
*   `-` a range of time units
*   `,` a comma separated list of individual time units
*   `/` intervals time units, the left value is the starting value and the right value is the max value

For example given the input argument:

`*/15 0 1,15 * 1-5 /usr/bin/find`

The output should be:

```zsh
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Running built jar

The build will build two jars:

1.  A plain jar that is built for use a library at build/libs/cron-java-expr-parser-1.0-SNAPSHOT.jar
2.  A shadow jar that is built for running as a standalone application build/libs/cron-java-expr-parser-1.0-SNAPSHOT-all.jar

To run the cron parser from command prompt,
1. Build this project with gradle setup. For gradle setup, the recommendation is to download and install IntelliJ IDEA IDE
2. Open the project with IntelliJ IDEA IDE and install JDK 1.8 or above.
3. Setup JAVA_HOME and path for JDK 1.8. Follow this link `https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux` 
to set up JAVA_HOME for different OS.
4. Project will auto sync, if you find any difficulties in auto sync, build the project using IDE build tab
5. Once all dependencies are downloaded in your local workspace, do a gradle build from gradle build script
6. On latest IntelliJ IDEA IDE, gradle build script are available on the right conner 2 vertical tab.
7. In gradle script under tasks>build>build, right click and run this script
8. It will build your local project, and you will be able to find bundled jar under build/libs folder of you main project folder.
9. Open command prompt/terminal and go to your project folder root. Then go to build/libs and run :
10. `java -jar build/libs/cron-java-expr-parser-1.0-SNAPSHOT-all.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`
11. Following output will be displayed in your command prompt/terminal:

```zsh
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```