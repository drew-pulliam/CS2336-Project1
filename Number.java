
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

    public String toString(){
        // return formatted string version of number
        return String.format("%.2f", number);
    }
    
    public boolean equals(Object x){
        // if the object passed into equals is also a Number object, check that the numbers match
        // otherwise, return false as the two objects are different
        if(x instanceof Number){
            return number == ((Number) x).getNum();
        }
        return false;
    }
}