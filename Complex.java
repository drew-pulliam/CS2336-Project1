
public class Complex extends Number{

    /*
    Project 1
    Drew Pulliam
    dtp180003
    */

    private double imaginary;

    public Complex(){
        // default constructor
        super();
        imaginary = 0.0;
    }

    public Complex(double i){
        // overloaded constructor for imaginary number
        super();
        imaginary = i;
    }

    public Complex(double real, double i){
        // overloaded constructor for complex number
        super(real);
        imaginary = i;
    }

    public double getImaginary(){
        // getter
        return imaginary;
    }

    @Override
    public double getMagnitude(){
        // getter
        return Math.sqrt((number*number) + (imaginary*imaginary));
    }

    public void setImaginary(double i){
        // setter
        imaginary = i;
    }

    @Override
    public String toString(){
        // return formatted string version of imaginary or complex number
        String i = String.format("%.2f", imaginary);
        String r = String.format("%.2f", number);
        if(number == 0 && imaginary == 0)
            return "0.00";
        else if(number == 0)
            return i + "i";
        else if(imaginary == 0)
            return r;
        else{
            String operator = "";
            if(imaginary >= 0)
                operator = "+";
            return r + operator + i + "i";
        }
    }

    @Override
    public Object add(Object x){
        // check if object passed is a complex or real number
        // then add it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return new Complex(number + ((Number) x).getNum(), imaginary);
        }else if(x instanceof Complex){
            return new Complex(number + ((Complex) x).getNum(), imaginary + ((Complex) x).getImaginary());
        }
        return null; // invalid object passed
    }

    @Override
    public Object subtract(Object x){
        // check if object passed is a complex or real number
        // then add it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return new Complex(number - ((Number) x).getNum(), imaginary);
        }else if(x instanceof Complex){
            return new Complex(number - ((Complex) x).getNum(), imaginary - ((Complex) x).getImaginary());
        }
        return null; // invalid object passed
    }

    @Override
    public Object multiply(Object x){
        // check if object passed is a complex or real number
        // then multiply it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            return new Complex(number * ((Number) x).getNum(), imaginary);
        }else if(x instanceof Complex){
            double r = number * ((Complex) x).getNum() - imaginary * ((Complex) x).getImaginary();
            double i = number * ((Complex) x).getImaginary() + imaginary * ((Complex) x).getNum();
            return new Complex(r, i);
        }
        return null; // invalid object passed
    }

    @Override
    public Object divide(Object x){
        // check if object passed is a complex or real number
        // then multiply it and return the appropriate object
        if(x instanceof Number && x instanceof Complex == false){
            if(((Number) x).getNum() == 0.0)
                return null; // eliminate division by zero
            return new Complex(number / ((Number) x).getNum(), imaginary / ((Number) x).getNum());
        }else if(x instanceof Complex){
            double numeratorReal = number * ((Complex) x).getNum() - imaginary * -((Complex) x).getImaginary();
            double numeratorImaginary = number * -((Complex) x).getImaginary() + imaginary * ((Complex) x).getNum();
            double denominatorReal = Math.pow(((Complex) x).getNum(), 2) + Math.pow(((Complex) x).getImaginary(), 2);
            if(denominatorReal == 0.0)
                return null; // eliminate division by zero
            return new Complex(numeratorReal / denominatorReal, numeratorImaginary / denominatorReal);
        }
        return null; // invalid object passed
    }
    
    @Override
    public boolean equals(Object x){
        // if the object passed into equals is also a Complex object, check that the numbers match
        // otherwise, return false as the two objects are different
        if(x instanceof Complex){
            return number == ((Complex) x).getNum() && imaginary == ((Complex) x).getImaginary();
        }
        return false;
    }
}