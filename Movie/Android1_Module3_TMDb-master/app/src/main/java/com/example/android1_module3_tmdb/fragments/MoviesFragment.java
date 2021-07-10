package com.example.android1_module3_tmdb.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android1_module3_tmdb.R;
import com.example.android1_module3_tmdb.adapters.MoviesAdapter;
import com.example.android1_module3_tmdb.api.APIService;
import com.example.android1_module3_tmdb.api.RetrofitConfiguration;
import com.example.android1_module3_tmdb.models.GetMoviesResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private static final String TAG = "MoviesFragment";
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private MoviesAdapter moviesAdapter;
    private List<GetMoviesResponse.ResultsBean> movies = new ArrayList<>();

    private int page = 1;
    private int totalItemCount, lastVisibleItem;
    private int visibleThreshold = 5;
    private boolean isLoading;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);

        setupUI();
        loadData();

        return view;
    }

    private void setupUI() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvMovies.setLayoutManager(gridLayoutManager);

        moviesAdapter = new MoviesAdapter(movies);
        rvMovies.setAdapter(moviesAdapter);

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = gridLayoutManager.getItemCount();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                Log.d(TAG, "onScrolled: totalItemCount " + totalItemCount);
                Log.d(TAG, "onScrolled: lastVisibleItem " + lastVisibleItem);

                if (!isLoading && lastVisibleItem >= totalItemCount - visibleThreshold) {
                    page++;
                    loadData();
                    isLoading = true;
                }
            }
        });
    }

    private void loadData() {
        APIService service = RetrofitConfiguration.getInstance().create(APIService.class);
        Call<GetMoviesResponse> call = service.getMovies(page);
        call.enqueue(new Callback<GetMoviesResponse>() {
            @Override
            public void onResponse(Call<GetMoviesResponse> call, Response<GetMoviesResponse> response) {
                isLoading = false;
                if (response.code() == 200) {
                    movies.addAll(response.body().getResults());
                    moviesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetMoviesResponse> call, Throwable t) {
                isLoading = false;
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
