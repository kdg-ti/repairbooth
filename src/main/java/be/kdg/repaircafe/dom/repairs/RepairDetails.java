package be.kdg.repaircafe.dom.repairs;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Contains details about a repair.
 * <p/>
 * Details are: problem description, defect type, optional rebuttal by repairer,
 * pricing model, timestamps (submit and due date), time to expiration.
 *
 * @author wouter
 * @link(be.kdg.repaircafemodel.dom.repairs.Repair) details
 */
public class RepairDetails implements Serializable {

    private Integer repairDetailsId;

    private String defect;

    private String description;

    private String evaluation;

    private String rebuttal;

    private Rating rating;

    private Status status;

    private PriceModel priceModel;

    private LocalDateTime dueDate;

    private LocalDateTime submitDate;

    private boolean assigned;

    public RepairDetails(String defect, String description, PriceModel priceModel, LocalDateTime dueDate) {
        this();
        this.defect = defect;
        this.description = description;
        this.priceModel = priceModel;
        this.dueDate = dueDate;
    }


    public RepairDetails() {
        this.submitDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusWeeks(2);
        this.rating = Rating.NotSet;
        this.status = Status.Broken;
    }

    public Integer getRepairDetailsId() {
        return repairDetailsId;
    }

    public void setRepairDetailsId(Integer repairDetailsId) {
        this.repairDetailsId = repairDetailsId;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getRebuttal() {
        return rebuttal;
    }

    public void setRebuttal(String rebuttal) {
        this.rebuttal = rebuttal;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Don't use this method directly.
     *
     * @param newStatus new Status for repair. Status Fixed and Broken will only be accepted when Repair is assigned
     *                  to a Repairer
     */
    public void setStatus(Status newStatus) {
        if (newStatus == null)
            newStatus = Status.Broken;

        if ((newStatus.equals(Status.Fixed) || newStatus.equals(Status.Irreparable)) && !isAssigned()) {
            this.status = Status.Broken;
        } else {
            this.status = newStatus;
        }
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public PriceModel getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(PriceModel priceModel) {
        this.priceModel = priceModel;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * How much time is left for bidding on this repair
     *
     * @return Period. Represents time period
     */
    public Duration getExpirationTime() {
        return Duration.between(LocalDateTime.now(), dueDate);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.defect);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.priceModel);
        hash = 59 * hash + Objects.hashCode(this.dueDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RepairDetails other = (RepairDetails) obj;
        if (!Objects.equals(this.defect, other.defect)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (this.priceModel != other.priceModel) {
            return false;
        }
        return Objects.equals(this.dueDate, other.dueDate);
    }

    @Override
    public String toString() {
        return "RepairDetails{defect=" + defect + ", description=" + description + ", priceModel=" + priceModel + ", dueDate=" + dueDate + '}';
    }

    public enum PriceModel {
        PER_HOUR("Per Hour"), FIXED("Fixed");
        private final String str;

        PriceModel(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    /**
     * A repair is rated by the client. This rating is used to update the
     * repairers overall rating.
     */
    public enum Rating {
        NotSet(0), VeryPoor(1), Poor(2), OK(3), Good(4), Excellent(5);

        private final int rating;

        Rating(int rating) {
            this.rating = rating;
        }

        public static Rating toOrdinal(int rating) {
            switch (rating) {
                case 1:
                    return Rating.VeryPoor;
                case 2:
                    return Rating.Poor;
                case 3:
                    return Rating.OK;
                case 4:
                    return Rating.Good;
                case 5:
                    return Rating.Excellent;
                default:
                    return Rating.NotSet;
            }
        }
    }

    /**
     * Status of a repair.
     */
    public enum Status {
        Broken, Fixed, Irreparable
    }

}
