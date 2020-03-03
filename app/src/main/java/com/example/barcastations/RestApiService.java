package com.example.barcastations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApiService {

   // @GET("bus/nearstation/latlon/41.3985182/2.1917991/1.json")
    @GET("bus/nearstation/latlon/{lat}/{lon}/1.json")
    Call<RemoteData> getData(@Path("lat") double lat,@Path("lon") double lon);


}
