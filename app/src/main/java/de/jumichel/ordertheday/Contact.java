package de.jumichel.ordertheday;
//Klasse von Kontakten
public class Contact {

    private String forname;
    private String surname;
    private String phonenumber;
    private Address address;

    //default Konstruktor
    public Contact() {
        forname = "Max";
        surname = "Mustermann";
        phonenumber = "016011111111";
        address = new Address();
    }

    //Konstruktor alle Informationen von Kontakten werden hinzugefügt
    public Contact(String forname, String surname, String phonenumber, String street, String number, String postcode, String city) {
        this.forname = forname;
        this.surname = surname;
        this.phonenumber = phonenumber;
        this.address = new Address(street, number, postcode, city);
    }

    //Getters und Setters
    public String getForname() {
        return forname;
    }

    public void setForname(String forname) {
        this.forname = forname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address.toString();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //toString Methode für die Ausgabe
    public String toString() {
        return forname + " " + surname + ", " + phonenumber + ", " + address.toString();
    }

}
