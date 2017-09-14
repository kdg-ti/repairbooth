package be.kdg.repaircafe.dom.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Holds address for all types of users.
 * Can be used to better match Client and Repairer.
 *
 * @author wouter
 */
@Entity
public class Address implements Serializable
{
    @Column(nullable = false)
    @Id
    @GeneratedValue
    private Integer addressId;

    @Column
    private String street;

    @Column
    private String nr;

    @Column
    private String zip;

    @Column
    private String city;

    public Address() {
    }

    public Address(String street, String nr, String zip, String city) {
        this.street = street;
        this.nr = nr;
        this.zip = zip;
        this.city = city;
    }

    public Integer getId() {
        return addressId;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNr() {
        return this.nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (nr != null ? nr.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (nr != null ? !nr.equals(address.nr) : address.nr != null) return false;
        if (zip != null ? !zip.equals(address.zip) : address.zip != null) return false;
        return !(city != null ? !city.equals(address.city) : address.city != null);

    }
}