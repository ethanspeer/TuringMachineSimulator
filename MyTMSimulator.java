//Ethan Speer
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MyTMSimulator {

    ArrayList<String> TM_data = new ArrayList<String>();
    ArrayList<Character> InputString = new ArrayList<Character>();
    Scanner key = new Scanner(System.in);
    int nextState = 0;
    char writeSymbol;
    char nextMove;
    int currentPosition = 1;
    int numStates;
    boolean foundLine = false;


//Main method creates a TM object, reads in data from the TM file, and simulates.
    public static void main(String[] args) {

	MyTMSimulator TM = new MyTMSimulator();

        while(true) {
	    TM.resetVars();
            TM.readFile(args[0]);
            TM.run();
        }
    }

//Resets global variables
   public void resetVars() {
	nextState = 0;
	currentPosition = 1;
	foundLine = false;
	TM_data.clear();
	InputString.clear();
  }

//Method to read in data from the file into an arrayList, throws an exception
    public void readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner inputReader = new Scanner(file);
            while (inputReader.hasNextLine()) {
              String data = inputReader.nextLine();
              TM_data.add(data);
            }
            inputReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

//Finds a line in the arrayList from the file with a given state and input symbol
    public boolean findLine(int state, char inputSymbol) {
	foundLine = false;
        for(int i = 2; i < TM_data.size(); i++) {
	    if(foundLine) {
		return foundLine;
	    }
            String line = TM_data.get(i);
            int endStateIndex = line.indexOf(" ");
            String lineState = line.substring(0, endStateIndex);
            int matchState = Integer.parseInt(lineState);
            char lineSymbol = line.charAt(endStateIndex + 1);
            if((matchState == state) && (lineSymbol == inputSymbol)) {
                int slashIndex = line.indexOf("/");
                String splitLine = line.substring(slashIndex, line.length());
                int spaceIndex1 = splitLine.indexOf(" ");
                int spaceIndex2 = splitLine.indexOf(" ", spaceIndex1 + 1);
                String miniSplit = splitLine.substring(spaceIndex1 + 1, spaceIndex2);
                nextState = Integer.parseInt(miniSplit);
                String temp = splitLine.substring(spaceIndex2 + 1, splitLine.length() - 2);
                writeSymbol = temp.charAt(0);
                nextMove = line.charAt(line.length() - 1);
		foundLine = true;
            }
        }
        return foundLine;
    }

//Finds the number of states from the file data, receives input from standard input and adds to arrayList, performs
//main functionality of program by printing formatted output
    public void run() {

            String line = TM_data.get(0);
            int colonIndex = line.indexOf(':');
            String stateString = line.substring(colonIndex + 2, line.length());
            numStates = Integer.parseInt(stateString);

        boolean running = true;
        String inputString = key.nextLine();
	if(inputString.isEmpty()) {
	inputString = "_";
	}
        for(int i = 0; i < inputString.length(); i++) {
            InputString.add(inputString.charAt(i));
        }

        while(running) {
            highlight(currentPosition);
            if((InputString.get(0) == '_')) {
                InputString.remove(0);
                currentPosition--;
            }
            if(InputString.get(InputString.size() - 1) == '_') {
                InputString.remove(InputString.size() - 1);
            }
            printID();
            if(nextState == numStates) {
                System.out.println("accept");
                break;
            }
            if((findLine(nextState, getInputSymbol()) == false) && (nextState != numStates)) {
              System.out.println("reject");
               break;
            }
            formatID();
        }
    }

//returns the current input symbol
    public char getInputSymbol() {
        for(int i = 0; i < InputString.size(); i++) {
            if(InputString.get(i) == '(') {
                return InputString.get(i+1);
            }
        }
        return ' ';
    }

//"highlights" character in ID by putting parenthases around it. If highlight already exists, removes it and adds to new
//position.
    public void highlight(int position) {
        boolean highlightExists = InputString.contains('(');
        if(highlightExists) {
            for(int i = 0; i < InputString.size(); i++) {
                if((InputString.get(i) == '(') || (InputString.get(i) == ')')) {
                    InputString.remove(i);
                }
            }
        }
        highlightExists = false;
        if(highlightExists == false) {
            InputString.add((position-1), '(');
            InputString.add((position+1), ')');
        }
    }

//returns the current value of an arrayList of characters, InputString, which gives the instantaneous description of the TM
    public String ID() {
        String IDoutput = "";
        for(int i = 0; i < InputString.size(); i++) {
            IDoutput += InputString.get(i);
        }
        return IDoutput;
    }

//adds blanks if the TM extends past the written characters on either side, writes the output symbol to tape
    public void formatID() {
        if(nextMove == '>') {
            currentPosition++;
            if(currentPosition >= InputString.size() - 1) {
                InputString.add('_');
            }
        }
        if(nextMove == '<') {
            currentPosition--;
            if(currentPosition == 0) {
                InputString.add(0, '_');
                currentPosition++;
            }
        }
        for(int i = 0; i < InputString.size(); i++) {
            if(InputString.get(i) == '(') {
                InputString.set(i+1, writeSymbol);
            }
        }
    }

//prints properly formatted instantaneous description
    public void printID() {
	if(nextState < 10) {
	System.out.println(" " + nextState + ":" + ID());
	} else {
	System.out.println(nextState + ":" + ID());
	}
    }
}

