package com.example.safari.Model;

import android.content.Context;
import android.widget.Toast;

import com.example.safari.OnFetchModelListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManagerForModels {

    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://vpic.nhtsa.dot.gov/api/vehicles/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // public static String id =  "11233";

    public RequestManagerForModels(Context context) {
        this.context = context;
    }

    public void getData(OnFetchModelListener listener,String make_id,String format){

        String id = make_id;


        getModelsApi api = retrofit.create(getModelsApi.class);
        Call<GetModelsForMakeId> callApi = api.callApi(id,format);


        callApi.enqueue(new Callback<GetModelsForMakeId>() {
            @Override
            public void onResponse(Call<GetModelsForMakeId> call, Response<GetModelsForMakeId> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(context, "Error3", Toast.LENGTH_SHORT).show();
                }

                if(response.body()!=null) {
                    listener.OnFetchModels(response.body().Count, response.body().Message, response.body().SearchCriteria
                            , response.body().Results, response.message());
                }

            }

            @Override
            public void onFailure(Call<GetModelsForMakeId> call, Throwable t) {

                listener.OnError("Error");

            }
        });



    }



  interface getModelsApi{

       // String apiEndpoint = "GetModelsForMakeId/" + id;

        @GET("GetModelsForMakeId/{id}")
            //?format=json

        Call<GetModelsForMakeId> callApi(

                //  @Query("make_id") int make_id
                @Path("id") String id,
                @Query("format") String format

        );






    }




}

