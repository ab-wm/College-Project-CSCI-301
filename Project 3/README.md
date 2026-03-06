The current form of the project is a Gradle build with the "Java" plugin. The build file has been modified to build an executable jar from the command line.

On Mac / Linux based systems, use the following command from the project's top-level directory:
```
./gradlew clean build jar
```
On Windows based systems, use the command:
```
./gradlew.bat clean build jar
```

The executable jar can be executed from command line with Java's -jvm option.

On all systems, use the following command from the project's top-level directory:
```
java -jar build/libs/GUISudoku-1.0-SNAPSHOT.jar
```
