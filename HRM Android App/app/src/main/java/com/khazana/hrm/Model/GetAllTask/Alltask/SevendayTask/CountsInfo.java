
package com.khazana.hrm.Model.GetAllTask.Alltask.SevendayTask;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CountsInfo {

    @SerializedName("all_tasks")
    @Expose
    private Integer allTasks;
    @SerializedName("today")
    @Expose
    private Integer today;
    @SerializedName("days_7")
    @Expose
    private Integer days7;
    @SerializedName("pending")
    @Expose
    private Integer pending;
    @SerializedName("completed")
    @Expose
    private Integer completed;
    @SerializedName("high")
    @Expose
    private Integer high;
    @SerializedName("normal")
    @Expose
    private Integer normal;
    @SerializedName("low")
    @Expose
    private Integer low;

    public Integer getAllTasks() {
        return allTasks;
    }

    public void setAllTasks(Integer allTasks) {
        this.allTasks = allTasks;
    }

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getDays7() {
        return days7;
    }

    public void setDays7(Integer days7) {
        this.days7 = days7;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getNormal() {
        return normal;
    }

    public void setNormal(Integer normal) {
        this.normal = normal;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

}
