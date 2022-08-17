
package com.example.hrm.Model.GetAllTask.Alltask.MarkComplet;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private MarkData markData;

    public MarkData getResponse() {
        return markData;
    }

    public void setResponse(MarkData markData) {
        this.markData = markData;
    }

}
