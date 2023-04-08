package com.example.safari.Model;

import java.io.Serializable;
import java.util.List;

public class GetAllMakesApi implements Serializable {

    public int Count;

    public String Message;

    public String SearchCriteria;

    public List<Results> Results;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSearchCriteria() {
        return SearchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        SearchCriteria = searchCriteria;
    }

    public List<com.example.safari.Model.Results> getResults() {
        return Results;
    }

    public void setResults(List<com.example.safari.Model.Results> results) {
        Results = results;
    }
}
