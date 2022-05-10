# TuringMachineSimulator
A java program which simulates the computational trace of a Turing machine.

INPUT: This program takes a file name as a command line argument and simulates a Turing Machine based on specifically formatted tuples.

First build the file:
javac MyTMSimulator.java
Then run the file:
java MyTMSimulator <File Name>
  
Then type a string of characters, and the simulator will execute based on the turing machine specified in the file.
  
OUTPUT: The program prints the computational trace based on the input file for the turing machine and prints either accept or reject.
  
Misc:
  "_" is a blank on the tape
  
  The first line of the TM files gives the number of nonhalting states.
  The second line gives the alphabet for the TM.
  The remaining lines gives tuples of the form:
 
  "<input state> <input symbol> / <output state> <output symbol> <movement direction>"
  
  where 0 is always the start state.
  
  input state is an integer
  input symbol is a character from the alphabet in line 2
  output state is an integer
  output symbol is a character from the alphabet in line 2
  movement direction is "<" or ">" indicating left and right respectively.
