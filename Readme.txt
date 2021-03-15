Sean Ennis
sennis3, 653900061
CS 411
Assignment 8 - IDA* Search
April 3, 2020


File Input Format:

The program will accept the format the sample input was given in. The program was designed to read a file in this format.
The file format doesn't necessarily have to be formatted this way. If the input is to be given in a different format, it is important that all the keywords are present in the file before the values inputted and separated by a colon and spaces (" : "). If any of these words are misspelled or missing or if there are missing values that need to be inputted, the program will not function correctly.


Running the program with the file:

The file must a text file and it needs to be read in from the command line. The program expects a file as a parameter to the function.
When providing the file as a parameter when running the function, the absolute file path must be used. However, this isn't necessary if the file is located within the current working directory.

To run the program, navigate to the bin directory in sennis3_mdp and run it with "java MDP [file]"

Example: "java MDP C:\Users\sennis3\Desktop\mdp_input.txt"

If the input files are located within the bin directory, it can also be run with the example below.

Example: "java MDP mdp_input.txt"


Output:

The program will automatically run through the iterations and print the results. It prints the utilites at each iteration and then the final values once convergence is reached. Lastly, it prints the grid of the final policy at each state.