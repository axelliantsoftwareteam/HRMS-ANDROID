
package com.khazana.hrm.Model.Memberlist;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<MemberResponse> memberResponse = null;

    public List<MemberResponse> getResponse() {
        return memberResponse;
    }

    public void setResponse(List<MemberResponse> memberResponse) {
        this.memberResponse = memberResponse;
    }

}
