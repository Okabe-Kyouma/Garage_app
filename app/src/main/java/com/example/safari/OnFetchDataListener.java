package com.example.safari;

import com.example.safari.Model.GetAllMakesApi;
import com.example.safari.Model.Results;

import java.util.List;

public interface OnFetchDataListener {

    public void OnFetchData(int Count, String Message
            , String SearchCriteria, List<Results> Results
            , String message);

    public void OnError(String message);


}
