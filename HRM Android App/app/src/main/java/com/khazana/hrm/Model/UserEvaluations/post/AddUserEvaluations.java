package com.khazana.hrm.Model.UserEvaluations.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddUserEvaluations {

    @SerializedName("questions")
    private List<Question> questions = null;

    @SerializedName("evaluation_id")
    private int evaluationId;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }
}
