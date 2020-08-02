package com.example.guestapp.model;

import org.parceler.Parcel;

@Parcel
public class Request implements Cloneable{

    private Complaint complaint;
    private String description, generatedDate, approvedDate;
    private int availabilityOfParts;
    private long requestId;
    private float cost;
    private boolean status;

    public Object clone() throws
            CloneNotSupportedException
    {
        return super.clone();
    }

    public Request() {
    }

    public Request(Complaint complaint, String description, String generatedDate, String approvedDate, long requestId, int availabilityOfParts, float cost) {
        this.complaint = complaint;
        this.description = description;
        this.generatedDate = generatedDate;
        this.approvedDate = approvedDate;
        this.requestId = requestId;
        this.availabilityOfParts = availabilityOfParts;
        this.cost = cost;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getAvailabilityOfParts() {
        return availabilityOfParts;
    }

    public void setAvailabilityOfParts(int availabilityOfParts) {
        this.availabilityOfParts = availabilityOfParts;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

