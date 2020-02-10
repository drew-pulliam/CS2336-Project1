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
        String userInput = userIn.nextLine();//"sample_expressions.txt";
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
        String result = "bananas";
        boolean op0Real = operand0 instanceof Number && operand0 instanceof Complex == false;
        boolean op1Real = operand1 instanceof Number && operand1 instanceof Complex == false;
        boolean op0Complex = operand0 instanceof Complex;
        boolean op1Complex = operand1 instanceof Complex;

        // check to make sure the operands are either real or complex
        if(op0Real == false && op0Complex == false)
            return "ERROR, op0 not real or complex";
        if(op1Real == false && op1Complex == false)
            return "ERROR, op1 not real or complex";

        // save magnitude, real, and imaginary components as local variables
        double op0Mag;
        double op0Num;
        double op0Imaginary;
        if(op0Real){
            op0Mag = ((Number)operand0).getMagnitude();
            op0Num = ((Number)operand0).getNum();
            op0Imaginary = 0;
        }else{
            op0Mag = ((Complex)operand0).getMagnitude();
            op0Num = ((Complex)operand0).getNum();
            op0Imaginary = ((Complex)operand0).getImaginary();
        }

        // save magnitude, real, and imaginary components as local variables
        double op1Mag;
        double op1Num;
        double op1Imaginary;
        if(op1Real){
            op1Mag = ((Number)operand1).getMagnitude();
            op1Num = ((Number)operand1).getNum();
            op1Imaginary = 0;
        }else{
            op1Mag = ((Complex)operand1).getMagnitude();
            op1Num = ((Complex)operand1).getNum();
            op1Imaginary = ((Complex)operand1).getImaginary();
        }

        double i = 0.0;
        double r = 0.0;
        switch (operation){
            case "+":
                // add
                r = op0Num + op1Num;
                i = op0Imaginary + op1Imaginary;
                result = formatResult(r, i);
                break;
            case "-":
                // subtract
                r = op0Num - op1Num;
                i = op0Imaginary - op1Imaginary;
                result = formatResult(r, i);
                break;
            case "*":
                // multiply
                if (op0Imaginary != 0 || op1Imaginary != 0){
                    // complex multiply
                    r = (op0Num * op1Num) - (op0Imaginary * op1Imaginary);
                    i = (op0Num * op1Imaginary) + (op0Imaginary * op1Num);
                    result = formatResult(r, i);
                }else{
                    result = String.format("%.2f", (op0Num * op1Num));
                }
                break;
            case "/":
                // divide
                if (op0Imaginary != 0 || op1Imaginary != 0){
                    // complex divide
                    // multiply numerator and denom by complex conjugate of denom
                    double numeratorReal = (op0Num * op1Num) - (op0Imaginary * -op1Imaginary);
                    double numeratorImaginary = (op0Num * -op1Imaginary) + (op0Imaginary * op1Num);
                    double denominatorReal = (op1Num * op1Num) - (op1Imaginary * -op1Imaginary);

                    r = numeratorReal / denominatorReal;
                    i = numeratorImaginary / denominatorReal;
                    result = formatResult(r, i);
                }else{
                    result = String.format("%.2f", (op0Num / op1Num));
                }
                break;
            case "<":
                // less than
                result = "" + (op0Mag < op1Mag);
                break;
            case ">":
                // greater than
                result = "" + (op0Mag > op1Mag);
                break;
            case "=":
                // equal
                result = "" + operand0.equals(operand1);
                break;
        }

        return result;
    }

    private static String formatResult(double r, double i){
        String result = "";
        if(r != 0.0){
            // has a real component
            result = String.format("%.2f", r);
        }else{
            // does not have a real component
            result = "";
        }
        if(i != 0.0) {
            // has an imaginary component
            result = result + ((i>0.0 && r!=0.0) ? "+" : "") + String.format("%.2f",i) + "i";
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