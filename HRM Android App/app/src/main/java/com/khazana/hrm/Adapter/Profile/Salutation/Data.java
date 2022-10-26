
package com.khazana.hrm.Adapter.Profile.Salutation;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetSalutationData> getSalutationData = null;

    public List<GetSalutationData> getResponse() {
        return getSalutationData;
    }

    public void setResponse(List<GetSalutationData> getSalutationData) {
        this.getSalutationData = getSalutationData;
    }

}
