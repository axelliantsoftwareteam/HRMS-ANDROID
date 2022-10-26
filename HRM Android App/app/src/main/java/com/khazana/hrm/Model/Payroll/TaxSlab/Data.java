
package com.khazana.hrm.Model.Payroll.TaxSlab;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetTaxSlabsData> getTaxSlabsData = null;

    public List<GetTaxSlabsData> getResponse() {
        return getTaxSlabsData;
    }

    public void setResponse(List<GetTaxSlabsData> getTaxSlabsData) {
        this.getTaxSlabsData = getTaxSlabsData;
    }

}
