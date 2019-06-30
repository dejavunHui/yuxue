package com.example.androidmvp.mvp.entity;

import java.util.List;

public class DataResults {
    private boolean error;
    private List<UserResult> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<UserResult> getResults() {
        return results;
    }

    public void setResults(List<UserResult> results) {
        this.results = results;
    }
}
