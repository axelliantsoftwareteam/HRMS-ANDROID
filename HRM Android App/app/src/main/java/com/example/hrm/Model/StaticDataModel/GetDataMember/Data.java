
package com.example.hrm.Model.StaticDataModel.GetDataMember;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetMemberList> getMemberList = null;

    public List<GetMemberList> getResponse() {
        return getMemberList;
    }

    public void setResponse(List<GetMemberList> getMemberList) {
        this.getMemberList = getMemberList;
    }

}
