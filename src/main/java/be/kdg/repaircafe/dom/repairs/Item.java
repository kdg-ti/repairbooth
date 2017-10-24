package be.kdg.repaircafe.dom.repairs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Item that needs repairing.
 *
 * @author wouter
 */
@Entity
public class Item implements Serializable {
    @Column(nullable = false)
    @Id
    @GeneratedValue
    private Integer itemId;

    @Column
    private String productName;

    @Column
    private String brand;

    @Column
    private String category;

    public Item() {
        this("", "", "");
    }

    /**
     * A thing that needs fixing. Characterized by it's
     * product name, brand and category
     *
     * @param productName
     * @param brand
     * @param category
     */
    public Item(String productName, String brand, String category) {
        this.productName = productName;
        this.brand = brand;
        this.category = category;
    }

    /**
     * Get product name of item
     *
     * @return product name
     */
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Get brand of item
     *
     * @return item brand name
     */
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get category of item
     *
     * @return item category
     */
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getId() {
        return itemId;
    }
}
