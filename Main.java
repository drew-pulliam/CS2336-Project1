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
        String userInput = "sample_expressions.txt";//userIn.nextLine();
        Scanner fileIn = new Scanner(new File(userInput));

        // loop through the entire input file, saving pilot names and coordinates
        while(fileIn.hasNextLine()){
            String operand0 = fileIn.next();
            String operation = fileIn.next();
            String operand1 = fileIn.next();
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
                op0Real = false;
            }else{
                // operand0 is real
                operand0Real = new Number(Double.parseDouble(operand0));
            }
            if(operand1.contains("i")){
                // operand1 is complex or imaginary
                operand1Complex = parseComplexOperand(operand1);
                op1Real = false;
            }else{
                // operand1 is real
                operand1Real = new Number(Double.parseDouble(operand1));
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
        String result = "";
        boolean op0Complex = operand0 instanceof Complex;

        if(op0Complex){
            result = ((Complex) operand0).getOperationResult(operation,operand1);
        }else if(op0Complex == false){
            result = ((Number) operand0).getOperationResult(operation,operand1);
        }
        return result;
    }

    private static void printOutputToFile(String operation, Object operand0, Object operand1, 
                                                PrintWriter output, String originalExpression){
        String result = getOperationResult(operation, operand0, operand1);
        if(result.isEmpty()){
            // ensures if the result is 0, it prints 0.00 and not nothing
            result = "0.00";
        }
        output.println(originalExpression + "	" + result);
    }

    private static Complex parseComplexOperand(String operand){
        int plus = operand.indexOf("+");
        int minus = operand.indexOf("-");
        double num = 0.0;
        double imaginary = 0.0;
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
            if(operand.substring(0, operand.length()-1).equals("-")){
                imaginary = -1.0;
            }else if(operand.substring(0, operand.length()-1).equals("")){
                imaginary = 1.0;
            }else{
                imaginary = Double.parseDouble(operand.substring(0, operand.length()-1));
            }
            return new Complex(imaginary);
        }
    }
    
}