package com.example.android1_module3_tmdb.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android1_module3_tmdb.R;
import com.example.android1_module3_tmdb.api.APIService;
import com.example.android1_module3_tmdb.api.RetrofitConfiguration;
import com.example.android1_module3_tmdb.models.GetMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private static final String TAG = "MoviesFragment";

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        APIService service = RetrofitConfiguration.getInstance().create(APIService.class);
        Call<GetMoviesResponse> call = service.getMovies();
        call.enqueue(new Callback<GetMoviesResponse>() {
            @Override
            public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                if (response.code() == 200) {

                }
            }

            @Override
            public void onFailure(Call<GetMoviesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network failed", Toast.LENGTH_SHORT).show();
            }
        });


        return inflater.inflate(R.layout.fragment_movies, container, false);
    }
}
