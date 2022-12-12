package DataCreation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ParametersController {

    private String keyFilePath;     // -k
    private int numOfLines;         // -n
    private int maxNestingLevel;    // -d
    private int maxKeyNumOfValue;   // -m
    private int maxStringLength;    // -l


    public void readParameters(String [] args){
        if (!hasCorrectNumOfParameters(args.length)) {
            System.err.println("Error at number of parameters. The correct number of arguments is 10. " +
                    "You gave: " + args.length);
            System.exit(-1);
        }
        assignParameters(args);
    }

    private boolean hasCorrectNumOfParameters(int argsLength) {
        return argsLength == 10;
    }

    private void assignParameters(String [] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-k":
                    keyFilePath = args[i + 1];
                    break;
                case "-n":
                    try {
                        numOfLines = Integer.parseInt(args[i+1]);
                    } catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Couldn't convert numOfLines " + args[i+1] + " to Integer.");
                    }
                    break;
                case "-d":
                    try {
                        maxNestingLevel = Integer.parseInt(args[i+1]);
                    } catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Couldn't convert maxNestingLevel " + args[i+1] + " to Integer.");
                    }
                    break;
                case "-m":
                    try {
                        maxKeyNumOfValue = Integer.parseInt(args[i+1]);
                    } catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Couldn't convert maxKeyNumOfValue " + args[i+1] + " to Integer.");
                    }
                    break;
                case "-l":
                    try {
                        maxStringLength = Integer.parseInt(args[i+1]);
                    } catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Couldn't convert maxStringLength " + args[i+1] + " to Integer.");
                    }
                    break;
                default:
                    System.err.println("Wrong parameter flag: " + args[i]);
                    System.exit(-1);
            }
        }
    }

    public String getKeyFilePath() {return keyFilePath;}

    public int getNumOfLines() {return numOfLines;}

    public int getMaxNestingLevel() {return maxNestingLevel;}

    public int getMaxKeyNumOfValue() {return maxKeyNumOfValue;}

    public void setMaxKeyNumOfValue(int newSize) {
        if (newSize < getMaxKeyNumOfValue()) {
            System.out.println("Max number of keys inside values must not be greater than " +
                    "number of keys. It casted to " + newSize + ", from " + getMaxKeyNumOfValue());
            maxKeyNumOfValue = newSize;
        }
    }

    public int getMaxStringLength() {return maxStringLength;}
}
