
package com.example.hrm.Model.Evalution;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Detail {

    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_id")
    @Expose
    private Integer questionId;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

}
