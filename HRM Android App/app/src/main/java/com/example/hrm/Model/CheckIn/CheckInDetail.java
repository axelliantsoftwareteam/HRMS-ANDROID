
package com.example.hrm.Model.CheckIn;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CheckInDetail {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("Response")
    @Expose
    private Response Response;

    public Response getResponse() {
        return Response;
    }

    public void setResponse(Response response) {
        Response = response;
    }

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("type")
    @Expose
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}