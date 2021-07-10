package com.example.mvpdemo2.feature.movies;

import com.example.mvpdemo2.api.APIService;
import com.example.mvpdemo2.api.RetrofitConfiguration;
import com.example.mvpdemo2.models.GetMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesModel implements MoviesContract.Model {
    @Override
    public void getMovies(OnFinishGetMovies onFinishGetMovies, int page) {
        APIService service = RetrofitConfiguration.getInstance().create(APIService.class);
        Call<GetMoviesResponse> call = service.getMovies(page);
        call.enqueue(new Callback<GetMoviesResponse>() {
            @Override
            public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {

                onFinishGetMovies.onRespone(response);
            }

            @Override
            public void onFailure(Call<GetMoviesResponse> call, Throwable t) {

            }
        });
    }
}





