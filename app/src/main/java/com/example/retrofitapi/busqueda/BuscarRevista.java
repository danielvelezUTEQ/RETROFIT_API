package com.example.retrofitapi.busqueda;

import com.example.retrofitapi.constructorjson.Revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BuscarRevista {

    @GET("ws/issues.php?j_id=")
    Call<List<Revista>> find(@Query("j_id") String j_id);
}
