
package com.example.hrm.Model.Evalution;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GetEvaluationData {

    @SerializedName("evaluation_id")
    @Expose
    private Integer evaluationId;
    @SerializedName("employee_name")
    @Expose
    private String employeeName;
    @SerializedName("report_to_name")
    @Expose
    private String reportToName;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("generated_by")
    @Expose
    private String generatedBy;
    @SerializedName("details")
    @Expose
    private List<Object> details = null;

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getReportToName() {
        return reportToName;
    }

    public void setReportToName(String reportToName) {
        this.reportToName = reportToName;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public List<Object> getDetails() {
        return details;
    }

    public void setDetails(List<Object> details) {
        this.details = details;
    }

}
