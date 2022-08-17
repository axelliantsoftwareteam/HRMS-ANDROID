
package com.example.hrm.Model.Payroll.TaxSlab;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GetTaxSlabsData {

    @SerializedName("serial_id")
    @Expose
    private String serialId;
    @SerializedName("minimum")
    @Expose
    private Integer minimum;
    @SerializedName("maximum")
    @Expose
    private Integer maximum;
    @SerializedName("deduction")
    @Expose
    private Integer deduction;
    @SerializedName("effective_from")
    @Expose
    private String effectiveFrom;
    @SerializedName("percentage_of_minimum")
    @Expose
    private Double percentageOfMinimum;
    @SerializedName("percantage")
    @Expose
    private Double percantage;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public void setDeduction(Integer deduction) {
        this.deduction = deduction;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Double getPercentageOfMinimum() {
        return percentageOfMinimum;
    }

    public void setPercentageOfMinimum(Double percentageOfMinimum) {
        this.percentageOfMinimum = percentageOfMinimum;
    }

    public Double getPercantage() {
        return percantage;
    }

    public void setPercantage(Double percantage) {
        this.percantage = percantage;
    }

}
