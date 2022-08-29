package de.jumichel.ordertheday;
//Klasse des Datums
public class Date {

    private int day;
    private int month;
    private int year;
    //Konstrukter
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    //Getters und Setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //tostring Methode der Ausgabe
    public String toString() {
        return day + "." + month + "." + year;
    }

}
