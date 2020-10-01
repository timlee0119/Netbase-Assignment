## Offsite test for Netbase Quid - Java Backend Software Engineer
### Requirements
- Java version 1.8
### File Structure
    .
    ├── *.java                  # Source Code
    ├── *.class                 # Complied runnable files
    ├── answer.txt              # My answer to question 1
    ├── documents     
        └── SampleData.txt  
    └── README.md
### How to compile
- For V1: `javac TheSpamFilterV1.java`  
- For V2: `javac TheSpamFilterV2.java`  
### How to run
- For V1: `java TheSpamFilterV1 input_file output_file`
    - input_file is the path of the input document
    - output_file is the path where the bi-gram probability and k would be written to
- For V2: `java TheSpamFilterV2 input_directory`
    - input_directory is the path of directory containing all input documents
### Output format for V1
The content in output_file would look like this:  
1.16653 (represent the bi-gram probability of document)  
9 (represent k)