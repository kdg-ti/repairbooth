package be.kdg.repaircafe.web.resources.users;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressResource {
    @NotEmpty(message = "Street cannot be empty")
    private String street;
    @NotEmpty(message = "House number cannot be empty")
    private String nr;
    @NotEmpty(message = "Provide a your ZIP code")
    private String zip;
    @NotEmpty(message = "Fill in your city name")
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
