package com.example.hrm.Model.UserEvaluations.post;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("question_id")
    private int questionId;

    @SerializedName("question")
    private String question;

    @SerializedName("comments")
    private String comments;

    @SerializedName("rating")
    private int rating;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
