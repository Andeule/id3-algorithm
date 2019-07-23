## General
This is my implementation of the ID3 algorithm in Java. It uses Entropy to calculate the Information Gain.

After the algorithm got executed the generated tree will be printed on the std out.

- Java 8
- Lombok Plugin is used for convenience


##What's provided?
- In the project root you can find a jar file of the version 1.0. See section **Execute** to get help on executing it.
- Moreover, you can find a jpg of the printed result tree in the projects root directory.
- The root directory also contain the *mushrooms.csv* file. 

##Execute 

To execute the jar file you have to pass two parameters:
1. Absolute path to the csv file on which you want to apply the ID3-algorithm
2. The index of the column, which determine the target values (e.g. in the provided csv it is column 0)

Example call:

```
java -jar id3algorithmn-1.0-SNAPSHOT.jar C:\Users\ahr\Desktop\mushrooms.csv 0
```

##Build
You don't need to build the project, if you just want to try version 1.0. There is already the jar file of 1.0 in the directory root.


An executable jar file will be created in the target folder with the following command:
```
mvn clean install
```
