package com.example.mvpdemo2.feature.movies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpdemo2.BaseFragment;
import com.example.mvpdemo2.R;
import com.example.mvpdemo2.models.GetMoviesResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends BaseFragment implements MoviesContract.View {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;

    private MoviesAdapter moviesAdapter;
    private List<GetMoviesResponse.ResultsBean> movies = new ArrayList<>();

    private int page = 1;
    private int totalItemCount, lastVisibleItem;
    private int visibleThreshold = 5;
    private boolean isLoading;

    private MoviesPresenter presenter;

    public MoviesFragment() {
        // Required empty public constructor
    }



    private void loadData() {
        presenter.getMovies(page);
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

                if (!isLoading && lastVisibleItem >= totalItemCount - visibleThreshold) {
                    page++;
                    loadData();
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public void setDataToRecyclerView(List<GetMoviesResponse.ResultsBean> movies) {
        isLoading = false;
        this.movies.addAll(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        isLoading = false;
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        llLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movies;
    }

    @Override
    protected void onInit() {
        presenter = new MoviesPresenter(this);

        setupUI();
        loadData();
    }
}
