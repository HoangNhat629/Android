package com.example.mvpdemo2.feature.movies;

import com.example.mvpdemo2.models.GetMoviesResponse;

import java.util.List;

import retrofit2.Response;


//1. create contract interface
public interface MoviesContract {
    interface View {
        void setDataToRecyclerView(List<GetMoviesResponse.ResultsBean> movies);

        void showError(String error);

        void showLoadingIndicator();

        void hideLoadingIndicator();
    }

    interface Presenter {
        void getMovies(int page);
    }

    interface Model {
        interface OnFinishGetMovies {
            void onRespone(Response response);

            void onFailure(String error);
        }

        void getMovies(OnFinishGetMovies onFinishGetMovies, int page);

    }
}
