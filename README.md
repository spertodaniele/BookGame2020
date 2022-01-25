# PREREQUISITES
- Java > 8
- Maven 3.8.x

# BUILD
```
mvn package
```

# RUN
- To build and run via Maven exec this command:
	```
	mvn clean install exec:java -Dexec.mainClass=it.sperto.book.App -Dexec.args="'<input_files_path>'"
	```
- To run via command line:
	```
	java.exe -Dfile.encoding=UTF-8 -jar BookGame2020-1.0-SNAPSHOT.jar <input_files_path>
	```
- The result file are stored in the *<input_files_path>* dir with same name of input file 
but with *.solution* extension
