
package com.khazana.hrm.Model.Building.TaskAdded;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private TaskAddedData taskAddedData;

    public TaskAddedData getResponse() {
        return taskAddedData;
    }

    public void setResponse(TaskAddedData taskAddedData) {
        this.taskAddedData = taskAddedData;
    }

}
