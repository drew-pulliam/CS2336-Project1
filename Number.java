
public class Number {

    /*
    Project 1
    Drew Pulliam
    dtp180003
    */

    protected double number;

    public Number(){
        // default constructor
        number = 0.0;
    }

    public Number(double num){
        // overloaded constructor
        number = num;
    }

    public double getNum(){
        // getter
        return number;
    }

    public double getMagnitude(){
        // getter
        return number;
    }

    public void setNum(double num){
        // setter
        number = num;
    }

    @Override
    public String toString(){
        // return formatted string version of number
        return String.format("%.2f", number);
    }

    public String getOperationResult(String operation, Object x){
        String result = "";
        switch (operation){
            case "+":
                // add
                result = add(x).toString();
                break;
            case "-":
                // subtract
                result = subtract(x).toString();
                break;
            case "*":
                // multiply
                result = multiply(x).toString();
                break;
            case "/":
                // divide
                result = divide(x).toString();
                break;
            case "<":
                // less than
                result = lessThan(x) + "";
                break;
            case ">":
                // greater than
                result = greaterThan(x) + "";
                break;
            case "=":
                // equal
                result = "" + equals(x);
                break;
        }
        return result;
    }

    public Object add(Object x){
        // check if object passed is a complex or real number
        // then add it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return new Number(number + ((Number) x).getNum());
        }else if(x instanceof Complex){
            return new Complex(number + ((Complex) x).getNum(), ((Complex) x).getImaginary());
        }
        return null; // invalid object passed
    }

    public Object subtract(Object x){
        // check if object passed is a complex or real number
        // then add it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return new Number(number - ((Number) x).getNum());
        }else if(x instanceof Complex){
            return new Complex(number - ((Complex) x).getNum(), ((Complex) x).getImaginary());
        }
        return null; // invalid object passed
    }

    public Object multiply(Object x){
        // check if object passed is a complex or real number
        // then multiply it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return new Number(number * ((Number) x).getNum());
        }else if(x instanceof Complex){
            return new Complex(number * ((Complex) x).getNum(), number * ((Complex) x).getImaginary());
        }
        return null; // invalid object passed
    }

    public Object divide(Object x){
        // check if object passed is a complex or real number
        // then multiply it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            if(((Number) x).getNum() == 0.0)
                return null; // eliminate division by zero
            return new Number(number / ((Number) x).getNum());
        }else if(x instanceof Complex){
            double numeratorReal = number * ((Complex) x).getNum();
            double numeratorImaginary = number * -((Complex) x).getImaginary();
            double denominatorReal = Math.pow(((Complex) x).getNum(), 2) - Math.pow(((Complex) x).getImaginary(), 2);
            if(denominatorReal == 0.0)
                return null; // eliminate division by zero
            return new Complex(numeratorReal / denominatorReal, numeratorImaginary / denominatorReal);
        }
        return null; // invalid object passed
    }

    public boolean lessThan(Object x){
        // check if object passed is a complex or real number
        // then add it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return (getMagnitude() < ((Number) x).getMagnitude());
        }else if(x instanceof Complex){
            return (getMagnitude() < ((Complex) x).getMagnitude());
        }
        return false; // invalid object passed
    }

    public boolean greaterThan(Object x){
        // check if object passed is a complex or real number
        // then add it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return (getMagnitude() > ((Number) x).getMagnitude());
        }else if(x instanceof Complex){
            return (getMagnitude() > ((Complex) x).getMagnitude());
        }
        return false; // invalid object passed
    }
    
    @Override
    public boolean equals(Object x){
        // if the object passed into equals is also a Number object, check that the numbers match
        // otherwise, return false as the two objects are different
        if(x instanceof Number && x instanceof Complex == false){
            return number == ((Number) x).getNum();
        }
        return false;
    }
}