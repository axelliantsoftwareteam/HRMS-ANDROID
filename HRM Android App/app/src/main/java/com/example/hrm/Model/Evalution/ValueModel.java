package com.example.hrm.Model.Evalution;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValueModel
{
    @SerializedName("question_id")
    @Expose
    private Integer question_id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("rating")
    @Expose
    private String rating;

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
