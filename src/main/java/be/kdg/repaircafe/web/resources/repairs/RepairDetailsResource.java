package be.kdg.repaircafe.web.resources.repairs;

import be.kdg.repaircafe.dom.repairs.RepairDetails;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class RepairDetailsResource {
    @NotEmpty(message = "Please provide the defect")
    private String defect;
    @NotEmpty(message = "Please provide a description of the defect")
    private String description;
    private String evaluation;
    private String rebuttal;
    private boolean assigned;

    private RepairDetails.Rating rating;
    private RepairDetails.Status status;

    @NotEmpty
    private RepairDetails.PriceModel priceModel;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dueDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime submitDate;

    public RepairDetailsResource() {
        submitDate = LocalDateTime.now();
        dueDate = submitDate.plusWeeks(2);
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

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public RepairDetails.Rating getRating() {
        return rating;
    }

    public void setRating(RepairDetails.Rating rating) {
        this.rating = rating;
    }

    public RepairDetails.Status getStatus() {
        return status;
    }

    public void setStatus(RepairDetails.Status status) {
        this.status = status;
    }

    public RepairDetails.PriceModel getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(RepairDetails.PriceModel priceModel) {
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
}
