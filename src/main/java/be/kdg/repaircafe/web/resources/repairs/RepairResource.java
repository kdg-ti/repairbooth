package be.kdg.repaircafe.web.resources.repairs;

import java.io.Serializable;

public class RepairResource implements Serializable {
    private Integer repairId;
    private Integer userId;

    private ItemResource itemResource;
    private RepairDetailsResource repairDetailsResource;

    public RepairResource() {
        itemResource = new ItemResource();
        repairDetailsResource = new RepairDetailsResource();
    }

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ItemResource getItemResource() {
        return itemResource;
    }

    public void setItemResource(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    public RepairDetailsResource getRepairDetailsResource() {
        return repairDetailsResource;
    }

    public void setRepairDetailsResource(RepairDetailsResource repairDetailsResource) {
        this.repairDetailsResource = repairDetailsResource;
    }
}
