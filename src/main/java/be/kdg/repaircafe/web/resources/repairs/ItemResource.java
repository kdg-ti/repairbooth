package be.kdg.repaircafe.web.resources.repairs;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemResource {
    @NotEmpty(message = "Please provide a product name")
    private String productName;
    @NotEmpty(message = "Please provide a brand name")
    private String brand;
    @NotEmpty(message = "Please provide a category")
    private String category;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
