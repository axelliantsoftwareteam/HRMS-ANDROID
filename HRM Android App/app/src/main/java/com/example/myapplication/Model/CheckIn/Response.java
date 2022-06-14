
package com.example.myapplication.Model.CheckIn;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Response {

    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;
    @SerializedName("is_check_in_button")
    @Expose
    private Boolean isCheckInButton;
    @SerializedName("is_check_out_button")
    @Expose
    private Boolean isCheckOutButton;
    @SerializedName("check_in_details")
    @Expose
    private List<CheckInDetail> checkInDetails = null;
    @SerializedName("check_out_details")
    @Expose
    private List<CheckOutDetail> checkOutDetails = null;
    @SerializedName("pending_requests")
    @Expose
    private Integer pendingRequests;
    @SerializedName("absents")
    @Expose
    private String absents;
    @SerializedName("leaves")
    @Expose
    private Integer leaves;
    @SerializedName("total_leaves_allowed")
    @Expose
    private Integer totalLeavesAllowed;
    @SerializedName("leave_graph_info")
    @Expose
    private List<LeaveGraphInfo> leaveGraphInfo = null;
    @SerializedName("notice")
    @Expose
    private String notice;

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Boolean getIsCheckInButton() {
        return isCheckInButton;
    }

    public void setIsCheckInButton(Boolean isCheckInButton) {
        this.isCheckInButton = isCheckInButton;
    }

    public Boolean getIsCheckOutButton() {
        return isCheckOutButton;
    }

    public void setIsCheckOutButton(Boolean isCheckOutButton) {
        this.isCheckOutButton = isCheckOutButton;
    }

    public List<CheckInDetail> getCheckInDetails() {
        return checkInDetails;
    }

    public void setCheckInDetails(List<CheckInDetail> checkInDetails) {
        this.checkInDetails = checkInDetails;
    }

    public List<CheckOutDetail> getCheckOutDetails() {
        return checkOutDetails;
    }

    public void setCheckOutDetails(List<CheckOutDetail> checkOutDetails) {
        this.checkOutDetails = checkOutDetails;
    }

    public Integer getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(Integer pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public String getAbsents() {
        return absents;
    }

    public void setAbsents(String absents) {
        this.absents = absents;
    }

    public Integer getLeaves() {
        return leaves;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
    }

    public Integer getTotalLeavesAllowed() {
        return totalLeavesAllowed;
    }

    public void setTotalLeavesAllowed(Integer totalLeavesAllowed) {
        this.totalLeavesAllowed = totalLeavesAllowed;
    }

    public List<LeaveGraphInfo> getLeaveGraphInfo() {
        return leaveGraphInfo;
    }

    public void setLeaveGraphInfo(List<LeaveGraphInfo> leaveGraphInfo) {
        this.leaveGraphInfo = leaveGraphInfo;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

}
