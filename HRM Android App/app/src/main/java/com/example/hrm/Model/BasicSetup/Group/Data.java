
package com.example.hrm.Model.BasicSetup.Group;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetGroupData> getGroupData = null;

    public List<GetGroupData> getResponse() {
        return getGroupData;
    }

    public void setResponse(List<GetGroupData> getGroupData) {
        this.getGroupData = getGroupData;
    }

}
