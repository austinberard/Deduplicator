#Deduplicator

##This project removes duplicate files from a directory

Example use:
Fork or download the repository

Navigate to the Deduplicator folder:$ cd example/path/Deduplicator

Make a bin folder inside the Deduplicator folder using:$ mkdir bin

Compile the java files using:$ javac -d bin support/SHA1.java support/FileWrapper.java src/Dedup.java

Run the main class file using:$ java Dedup example/folder/to/check/for/duplicates

##TODO

~~Allow it to search directories within the original one for duplicates~~

Add some type of GUI interface for this
