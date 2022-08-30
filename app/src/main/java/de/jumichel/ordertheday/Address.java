package de.jumichel.ordertheday;
//Klasse zur Adresse Erstellung
public class Address {

    //Hier gilt auch, hier wird es genauer beschrieben und bei aneren methodisch gleichen Klassen, nur adaptiert

    //Strings für die Klassen
    private String street;
    private String number;
    private String postcode;
    private String city;

    //Konstruktor ohne Übergabe von Eingabewerten (default)
    public Address(){
        street = "Technikumplatz";
        number = String.valueOf(17);
        postcode = "09648";
        city = "Mittweida";
    }

    //Konstruktor mit Übergabe von den Strings der Klasse
    public Address(String street, String number, String postcode, String city) {
        this.street = street;
        this.number = number;
        this.postcode =  postcode;
        this.city = city;
    }
    //Straße erhalten
    public String getStreet() {
        return street;
    }

    //die Straße setzen
    public void setStreet(String street) {
        this.street = street;
    }

    //die Hausnummer bekommen
    public String getNumber() {
        return number;
    }

    //die Hausnumemr setzen
    public void setNumber(String number) {
        this.number = number;
    }

    //die Postleitzahl bekommen
    public String getPostcode() {
        return postcode;
    }

    //die Postleitzahl setzen
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    //die Stadt bekommen
    public String getCity() {
        return city;
    }

    //die Stadt setzen
    public void setCity(String city) {
        this.city = city;
    }

    //alle Strings in ein String konvertieren
    @Override
    public String toString() {
        return street + " " + number + ", " + postcode + " " + city;
    }
}
