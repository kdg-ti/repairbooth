package be.kdg.repaircafe.dom.repairs;

import be.kdg.repaircafe.dom.users.roles.Client;
import be.kdg.repaircafe.dom.users.roles.Repairer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Displays all information about a repair.  A repair has a unique number and is associated with an Item and a Client
 * who has filed it in the system. It also includes a list of bids. These bids are offered by different Repairers A
 * repair can have a status Fixed, Broken or irreparable.
 * It can be associated with a repairer too if a repairers bid was accepted.
 * Repairer can write a rebuttal if he is not satisfied with the evaluation by a Client
 *
 * @author wouter
 */
@Entity
@NamedQuery(name = "Repair.findRepairByClient", query = "SELECT r FROM Repair r WHERE r.client = :client")
public class Repair implements Comparable<Repair>, Serializable {
    @Column(nullable = false)
    @Id
    @GeneratedValue
    private Integer repairId;

    @OneToOne(targetEntity = Item.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Item_Id")
    private Item item;

    @OneToOne(targetEntity = RepairDetails.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Repair_Details_Id")
    private RepairDetails details;

    @ManyToOne(targetEntity = Repairer.class, fetch = FetchType.EAGER)
    private Repairer repairer;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER, optional = false)
    private Client client;

    @OneToMany(targetEntity = Bid.class, mappedBy = "repair", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Bid> bids;

    public Repair(Item item, RepairDetails repDetails) {
        this();
        this.item = item;
        this.details = repDetails;
    }

    public Repair() {
        this.item = new Item();
        this.details = new RepairDetails();
        this.bids = new ArrayList<>();
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public Integer getId() {
        return repairId;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Repairer getRepairer() {
        return repairer;
    }

    public void setRepairer(Repairer repairer) {
        this.repairer = repairer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    @Override
    public int compareTo(Repair o) {
        return this.details.getSubmitDate().compareTo(o.getDetails().getSubmitDate());
    }

    public RepairDetails getDetails() {
        return details;
    }

    public void setDetails(RepairDetails details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        return repairId != null ? repairId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repair)) return false;

        Repair repair = (Repair) o;

        return !(repairId != null ? !repairId.equals(repair.repairId) : repair.repairId != null);

    }

    @Override
    public String toString() {
        return String.format("%s -%s", this.getDetails().getDescription(), item.getProductName());
    }
}
