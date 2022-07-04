
package com.example.hrm.Model.LeaveBalanceModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Response {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("total")
    @Expose
    private String total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
