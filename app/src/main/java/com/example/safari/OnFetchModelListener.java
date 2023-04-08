package com.example.safari;

import com.example.safari.Model.ResultsForModels;

import java.util.List;

public interface OnFetchModelListener {

    public void OnFetchModels(int Count, String Message
    , String SearchCriteria, List<ResultsForModels> Results,String message);

    public void OnError(String message);


}
