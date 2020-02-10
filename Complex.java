
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

    public double getMagnitude(){
        // getter
        return Math.sqrt((number*number) + (imaginary*imaginary));
    }

    public void setImaginary(double i){
        // setter
        imaginary = i;
    }

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
    
    public boolean equals(Object x){
        // if the object passed into equals is also a Complex object, check that the numbers match
        // otherwise, return false as the two objects are different
        if(x instanceof Complex){
            return number == ((Complex) x).getNum() && imaginary == ((Complex) x).getImaginary();
        }
        return false;
    }
}