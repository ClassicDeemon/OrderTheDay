package de.jumichel.ordertheday;

public class Address {

    private String street;
    private String number;
    private String postcode;
    private String city;

    public Address(){
        street = "Technikumplatz";
        number = String.valueOf(17);
        postcode = "09648";
        city = "Mittweida";
    }

    public Address(String street, String number, String postcode, String city) {
        this.street = street;
        this.number = number;
        this.postcode =  postcode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return street + " " + number + ", " + postcode + " " + city;
    }
}
