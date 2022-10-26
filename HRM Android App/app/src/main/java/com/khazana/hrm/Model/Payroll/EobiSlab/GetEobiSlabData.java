
package com.khazana.hrm.Model.Payroll.EobiSlab;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GetEobiSlabData {

    @SerializedName("serial_id")
    @Expose
    private String serialId;
    @SerializedName("minimum_age")
    @Expose
    private Integer minimumAge;
    @SerializedName("maximum_age")
    @Expose
    private Integer maximumAge;
    @SerializedName("deduction")
    @Expose
    private Integer deduction;
    @SerializedName("effective_from")
    @Expose
    private String effectiveFrom;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Integer getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(Integer maximumAge) {
        this.maximumAge = maximumAge;
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

}
