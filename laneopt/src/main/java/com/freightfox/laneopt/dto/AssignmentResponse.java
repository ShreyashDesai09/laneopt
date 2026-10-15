package com.freightfox.laneopt.dto;

import java.util.List;

public class AssignmentResponse {

    private String status;
    private Long totalCost;
    private List<LaneAssignmentResponse> assignments;
    private List<Long> selectedTransporters;

    public AssignmentResponse() {
    }

    public AssignmentResponse(
            String status,
            Long totalCost,
            List<LaneAssignmentResponse> assignments,
            List<Long> selectedTransporters) {

        this.status = status;
        this.totalCost = totalCost;
        this.assignments = assignments;
        this.selectedTransporters = selectedTransporters;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public List<LaneAssignmentResponse> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<LaneAssignmentResponse> assignments) {
        this.assignments = assignments;
    }

    public List<Long> getSelectedTransporters() {
        return selectedTransporters;
    }

    public void setSelectedTransporters(List<Long> selectedTransporters) {
        this.selectedTransporters = selectedTransporters;
    }
}