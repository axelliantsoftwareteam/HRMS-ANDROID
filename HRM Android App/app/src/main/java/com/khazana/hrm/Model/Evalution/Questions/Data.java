
package com.khazana.hrm.Model.Evalution.Questions;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetEvalQuesData> getEvalQuesData = null;

    public List<GetEvalQuesData> getResponse() {
        return getEvalQuesData;
    }

    public void setResponse(List<GetEvalQuesData> getEvalQuesData) {
        this.getEvalQuesData = getEvalQuesData;
    }

}
