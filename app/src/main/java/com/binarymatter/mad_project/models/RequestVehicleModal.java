package com.binarymatter.mad_project.models;

public class RequestVehicleModal {
    String id;
    String title;
    String category;
    String requiredDate;
    public Double budget;

    public RequestVehicleModal() {}

    public RequestVehicleModal(String id, String title, String category, String requiredDate, Double budget) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.requiredDate = requiredDate;
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(String requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
