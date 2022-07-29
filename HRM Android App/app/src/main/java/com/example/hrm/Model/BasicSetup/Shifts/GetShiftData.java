
package com.example.hrm.Model.BasicSetup.Shifts;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GetShiftData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;


    @SerializedName("holiday")
    @Expose
    private List<Integer> holiday = null;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("evaluation_id")
    @Expose
    private Integer evaluation_id;

    @SerializedName("employee_name")
    @Expose
    private String employee_name;

    @SerializedName("generated_by")
    @Expose
    private String generated_by;

    @SerializedName("is_completed")
    @Expose
    private boolean is_completed;


    public Integer getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(Integer evaluation_id) {
        this.evaluation_id = evaluation_id;
    }
    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    public String getGenerated_by() {
        return generated_by;
    }

    public void setGenerated_by(String generated_by) {
        this.generated_by = generated_by;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Integer> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<Integer> holiday) {
        this.holiday = holiday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
