# PREREQUISITES
- Java > 8
- Maven 3.8.x
- Python (Optional, to run the score calculator)

# BUILD
```
mvn install
```

# RUN
- To buld and run via Maven exec this command:
	```
	mvn clean install exec:java -Dexec.mainClass=it.sperto.App -Dexec.args="'<input_files_path>' '<<score_py_path>>'"
	```
- To run via command line:
```
java.exe -Dfile.encoding=UTF-8 -jar BookGame2020-1.0-SNAPSHOT.jar <input_files_path> <<score_py_path>>
```
- The result file are stored in the *<input_files_path>* dir with same name of input file 
but with *.solution* extension
- The *<<score_py_dir>>* optional argument is the installation folder of the application at this URL: *https://github.com/PicoJr/2020-hashcode-score*

# Tips and Examples
- Install as score calculator the application at this URL: *https://github.com/PicoJr/2020-hashcode-score* and his path has *<<score_py_dir>>*