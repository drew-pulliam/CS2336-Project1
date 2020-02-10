import java.util.Scanner;
import java.io.*;

public class Main {
    /*
    Project 1
    Drew Pulliam
    dtp180003
    */

    public static void main(String[] args) throws IOException{
        // initialize user input scanner and file output
        PrintWriter fileOut = new PrintWriter(new File("results.txt"));
        Scanner userIn = new Scanner(System.in);

        // ask user for input file name and initialize file input scanner
        System.out.println("Please input filename: ");
        String userInput = userIn.nextLine();
        Scanner fileIn = new Scanner(new File(userInput));

        // loop through the entire input file, saving pilot names and coordinates
        while(fileIn.hasNextLine()){
            
            String line = fileIn.nextLine();
            String expressions[] = line.split(" ");

            if(expressions.length != 3){
                // too many, or not enough expressions/operations
                // ignore this line
                continue;
            }

            String operand0 = expressions[0];
            String operation = expressions[1];
            String operand1 = expressions[2];

            if(isValidOperation(operation) == false){
                // not a valid operation
                // ignore this line
                continue;
            }

            // save the original expression to print later (with the correct formatting)
            String originalExpression = operand0 + " " + operation + " " + operand1;

            Complex operand0Complex = null;
            Complex operand1Complex = null;
            Number operand0Real = null;
            Number operand1Real = null;

            boolean op0Real = true;
            boolean op1Real = true;

            if(operand0.contains("i")){
                // operand0 is complex or imaginary
                operand0Complex = parseComplexOperand(operand0);
                if(operand0Complex == null){
                    // invalid operand, skip this line
                    continue;
                }
                op0Real = false;
            }else{
                // operand0 is real
                try{
                    operand0Real = new Number(Double.parseDouble(operand0));
                }catch(Exception e){
                    System.out.println(e);
                    continue; // this is not a valid number, skip this line
                }
            }
            if(operand1.contains("i")){
                // operand1 is complex or imaginary
                operand1Complex = parseComplexOperand(operand1);
                if(operand1Complex == null){
                    // invalid operand, skip this line
                    continue;
                }
                op1Real = false;
            }else{
                // operand1 is real
                try{
                    operand1Real = new Number(Double.parseDouble(operand1));
                }catch(Exception e){
                    System.out.println(e);
                    continue; // this is not a valid number, skip this line
                }
            }

            // print output to file, passing real or complex operand(s)
            printOutputToFile(  operation,
                                op0Real ? operand0Real : operand0Complex,
                                op1Real ? operand1Real : operand1Complex,
                                fileOut, originalExpression);
        }

        userIn.close();
        fileIn.close();
        fileOut.close();
    }

    private static String getOperationResult(String operation, Object operand0, Object operand1){
        // perform the desired operation and return the result as a string
        String result = "";
        boolean op0Complex = operand0 instanceof Complex;

        if(op0Complex){
            result = ((Complex) operand0).getOperationResult(operation,operand1);
        }else if(op0Complex == false){
            result = ((Number) operand0).getOperationResult(operation,operand1);
        }
        return result;
    }

    private static void printOutputToFile(String operation, Object operand0, Object operand1, PrintWriter output, String originalExpression){
        String result = getOperationResult(operation, operand0, operand1);
        if(result.isEmpty()){
            // ensures if the result is 0, it prints 0.00 and not nothing
            result = "0.00";
        }
        // print the original expresssion to save formatting
        output.println(originalExpression + "	" + result);
    }

    private static Complex parseComplexOperand(String operand){
        int plus = operand.indexOf("+", 1);
        int minus = operand.indexOf("-",1);
        double num = 0.0;
        double imaginary = 0.0;
        try{
            if(plus > 0){
                // number is complex, not just imaginary
                num = Double.parseDouble(operand.substring(0, plus));
                if(operand.substring(plus, operand.length()-1).equals("+")){
                    imaginary = 1.0;
                }else{
                    imaginary = Double.parseDouble(operand.substring(plus, operand.length()-1));
                }
                return new Complex(num,imaginary);
            }else if(minus > 0){
                // number is complex, not just imaginary
                num = Double.parseDouble(operand.substring(0, minus));
                if(operand.substring(minus, operand.length()-1).equals("-")){
                    imaginary = -1.0;
                }else{
                    imaginary = Double.parseDouble(operand.substring(minus, operand.length()-1));
                }
                return new Complex(num,imaginary);
            }else{
                // number is just imaginary
                String sub = operand.substring(0, operand.length()-1);
                if(sub.equals("-")){
                    imaginary = -1.0;
                }else if(sub.equals("") || sub.equals("+")){
                    imaginary = 1.0;
                }else{
                    imaginary = Double.parseDouble(operand.substring(0, operand.length()-1));
                }
                return new Complex(imaginary);
            }
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    private static boolean isValidOperation(String operation){
        // check if the operation is valid, return false if not
        // then skip the line
        if(operation.equals("+") ||
        operation.equals("-") ||
        operation.equals("*") ||
        operation.equals("/") ||
        operation.equals("<") ||
        operation.equals(">") ||
        operation.equals("=")){
            return true;
        }else{
            return false;
        }
    }
    
}