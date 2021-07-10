package com.example.android1_module3_tmdb.api;

import com.example.android1_module3_tmdb.models.GetMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    String apiKey = "accee5970d455d9fa99984523512f1fa";

    @GET("discover/movie?api_key=" + apiKey)
    Call<GetMoviesResponse> getMovies();
}
