/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import static java.lang.Math.sqrt;

/**
 *
 * @author tomas
 */
public class Record {

    private double x;
    private double y;
    public boolean invalid = false;

    public Record(double x, double y) {
        this.x = x;
        this.y = y;
        /*System.out.print(x);
        System.out.print(" ");
        System.out.println(y);*/
    }

    public double Distance() {
        return sqrt(x * x + y * y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    
    @Override
    public String toString() {
        return " " + Double.toString(x) + " " + Double.toString(y) + " ";
    }

}
