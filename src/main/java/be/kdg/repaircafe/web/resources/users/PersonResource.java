package be.kdg.repaircafe.web.resources.users;

import org.hibernate.validator.constraints.NotEmpty;

public class PersonResource {
    @NotEmpty(message = "First name cannot be empty")
    private String firstname;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastname;
    private AddressResource addressResource;

    public PersonResource() {
        this.addressResource = new AddressResource();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public AddressResource getAddressResource() {
        return addressResource;
    }

    public void setAddressResource(AddressResource addressResource) {
        this.addressResource = addressResource;
    }
}
