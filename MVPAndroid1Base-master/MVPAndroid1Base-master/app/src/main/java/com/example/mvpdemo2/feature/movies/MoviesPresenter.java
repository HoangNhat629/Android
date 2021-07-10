package com.example.mvpdemo2.feature.movies;

import com.example.mvpdemo2.api.APIService;
import com.example.mvpdemo2.api.RetrofitConfiguration;
import com.example.mvpdemo2.models.GetMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenter implements MoviesContract.Presenter, MoviesContract.Model.OnFinishGetMovies {

    MoviesContract.View view;
    MoviesContract.Model model;


    public MoviesPresenter(MoviesContract.View view) {
        this.view = view;
        this.model = new MoviesModel();
    }

    @Override
    public void getMovies(int page) {
        view.showLoadingIndicator();

        model.getMovies(this, page);
    }


    @Override
    public void onRespone(Response response) {
        view.hideLoadingIndicator();
        if (response.code() == 200) {
            view.setDataToRecyclerView(((GetMoviesResponse) response.body()).getResults());
        }
    }

    @Override
    public void onFailure(String error) {
        view.hideLoadingIndicator();
        view.showError(error);
    }
}

