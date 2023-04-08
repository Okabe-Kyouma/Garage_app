package com.example.safari;

import android.content.Context;
import android.widget.Toast;

import com.example.safari.Model.GetAllMakesApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RequestManager {

     Context context;
     Retrofit retrofit = new Retrofit.Builder().baseUrl("https://vpic.nhtsa.dot.gov/api/vehicles/")
             .addConverterFactory(GsonConverterFactory.create()).build();

     public RequestManager(Context context) {
          this.context = context;
     }

     public void getData(OnFetchDataListener listener){

          getApi api = retrofit.create(getApi.class);
          Call<GetAllMakesApi> call = api.callApi();

          call.enqueue(new Callback<GetAllMakesApi>() {
               @Override
               public void onResponse(Call<GetAllMakesApi> call, Response<GetAllMakesApi> response) {

                    if(!response.isSuccessful()){
                         Toast.makeText(context, "Response isn't SuccessFull!!", Toast.LENGTH_SHORT).show();
                    }

                      listener.OnFetchData(response.body().Count,response.body().Message,response.body().SearchCriteria
                       ,response.body().Results,response.message());


               }

               @Override
               public void onFailure(Call<GetAllMakesApi> call, Throwable t) {

                    Toast.makeText(context, "Response Failed!", Toast.LENGTH_SHORT).show();

                    listener.OnError(t.getMessage());


               }
          });


     }


}

interface getApi{

     @GET("getallmakes?format=json")

     Call<GetAllMakesApi> callApi();

}
