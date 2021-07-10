package com.example.android1_module3_tmdb.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.example.android1_module3_tmdb.R;
import com.example.android1_module3_tmdb.api.APIService;
import com.example.android1_module3_tmdb.api.RetrofitConfiguration;
import com.example.android1_module3_tmdb.models.GetMovieAccountStatesResponse;
import com.example.android1_module3_tmdb.models.GetMovieDetailResponse;
import com.example.android1_module3_tmdb.models.SetFavouriteMovieRequest;
import com.example.android1_module3_tmdb.models.SetFavouriteMovieResponse;
import com.example.android1_module3_tmdb.utils.Utils;

import java.text.NumberFormat;
import java.util.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = "MovieDetailActivity";

    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_genres)
    TextView tvGenres;
    @BindView(R.id.tv_rating_score)
    TextView tvRatingScore;
    @BindView(R.id.rb_rating_star)
    RatingBar rbRatingStar;
    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_run_time)
    TextView tvRunTime;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.tv_revenue)
    TextView tvRevenue;
    @BindView(R.id.tv_prod_companies)
    TextView tvProdCompanies;
    @BindView(R.id.tv_prod_countries)
    TextView tvProdCountries;
    @BindView(R.id.ns_detail)
    NestedScrollView nsDetail;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.iv_favourite)
    ImageView ivFavourite;

    private GetMovieDetailResponse movie;
    private APIService apiService;
    private boolean isFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        apiService = RetrofitConfiguration.getInstance().create(APIService.class);

        if (Utils.getSessionId(this) == null) {
            ivFavourite.setVisibility(View.INVISIBLE);
        } else {
            ivFavourite.setVisibility(View.VISIBLE);
        }

        setupUI();
        loadData();
    }

    private void getMovieAccountStates() {
        llLoading.setVisibility(View.VISIBLE);
        Call<GetMovieAccountStatesResponse> call = apiService.getMovieAccountStates(
                movie.getId(),
                Utils.getSessionId(this)
        );
        call.enqueue(new Callback<GetMovieAccountStatesResponse>() {
            @Override
            public void onResponse(Call<GetMovieAccountStatesResponse> call, Response<GetMovieAccountStatesResponse> response) {
                llLoading.setVisibility(View.INVISIBLE);
                if (response.code() == 200) {
                    isFavourite = response.body().isFavorite();
                    updateImageView();
                } else {
                    Utils.showErrorFromServer(response, MovieDetailActivity.this);
                }
            }

            @Override
            public void onFailure(Call<GetMovieAccountStatesResponse> call, Throwable t) {
                llLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(MovieDetailActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        int id = getIntent().getIntExtra("movie_id", -1);

        Call<GetMovieDetailResponse> call = apiService.getMovieDetail(id);
        call.enqueue(new Callback<GetMovieDetailResponse>() {
            @Override
            public void onResponse(Call<GetMovieDetailResponse> call, Response<GetMovieDetailResponse> response) {
                if (response.code() == 200) {
                    movie = response.body();
                    setupUIWithData();
                    if (Utils.getSessionId(MovieDetailActivity.this) != null) getMovieAccountStates();
                } else {
                    Utils.showErrorFromServer(response, MovieDetailActivity.this);
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<GetMovieDetailResponse> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUIWithData() {
        Glide.with(this)
                .load(RetrofitConfiguration.getImageBaseUrlOriginal() + movie.getPoster_path())
                .centerCrop()
                .into(ivBackground);

        tvTitle.setText(movie.getTitle());

        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < movie.getGenres().size(); i++) {
            genres.append(movie.getGenres().get(i).getName());
            if (i != movie.getGenres().size() - 1) {
                genres.append(", ");
            }
        }
        tvGenres.setText(genres);

        tvRatingScore.setText(String.valueOf(movie.getVote_average()));
        rbRatingStar.setProgress((int) movie.getVote_average());
        tvVoteCount.setText(String.valueOf(movie.getVote_count()));
        tvRunTime.setText(movie.getRuntime() + "m");

        tvReleaseDate.setText(movie.getRelease_date());
        tvOverview.setText(movie.getOverview());

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(Currency.getInstance("USD"));
        tvRevenue.setText(numberFormat.format(movie.getRevenue()));

        StringBuilder prodCompanies = new StringBuilder();
        for (int i = 0; i < movie.getProduction_companies().size(); i++) {
            prodCompanies.append(movie.getProduction_companies().get(i).getName());
            if (i != movie.getProduction_companies().size() - 1) {
                prodCompanies.append(", ");
            }
        }
        tvProdCompanies.setText(prodCompanies);

        StringBuilder prodCountries = new StringBuilder();
        for (int i = 0; i < movie.getProduction_countries().size(); i++) {
            prodCountries.append(movie.getProduction_countries().get(i).getName());
            if (i != movie.getProduction_countries().size() - 1) {
                prodCountries.append(", ");
            }
        }
        tvProdCountries.setText(prodCountries);
    }

    private void setupUI() {
        //1. get screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;

        //2. set max height for NestedScrollView
        ViewGroup.LayoutParams params = nsDetail.getLayoutParams();
        params.height = (int) (screenHeight * 0.85);
        nsDetail.setLayoutParams(params);
    }

    @OnClick({R.id.iv_back, R.id.iv_favourite, R.id.ll_watch_trailer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_favourite:
                updateFavouriteMovie();
                break;
            case R.id.ll_watch_trailer:
                watchTrailer();
                break;
        }
    }

    private void updateFavouriteMovie() {
        llLoading.setVisibility(View.VISIBLE);
        SetFavouriteMovieRequest body = new SetFavouriteMovieRequest(movie.getId(), !isFavourite);
        Call<SetFavouriteMovieResponse> call = apiService.setFavouriteMovie(body, Utils.getSessionId(this));
        call.enqueue(new Callback<SetFavouriteMovieResponse>() {
            @Override
            public void onResponse(Call<SetFavouriteMovieResponse> call, Response<SetFavouriteMovieResponse> response) {
                llLoading.setVisibility(View.INVISIBLE);
                if (response.code() == 201 || response.code() == 200) {
                    isFavourite = !isFavourite;
                    updateImageView();
                } else {
                    Utils.showErrorFromServer(response, MovieDetailActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SetFavouriteMovieResponse> call, Throwable t) {
                llLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(MovieDetailActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateImageView() {
        if (isFavourite) {
            ivFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            ivFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    private void watchTrailer() {
        if (movie.getVideos().getResults().size() == 0) {
            Toast.makeText(this, "No trailers", Toast.LENGTH_SHORT).show();
        } else {
            String trailerKey = movie.getVideos().getResults().get(0).getKey();
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailerKey));
            try {
                startActivity(appIntent);
            } catch (Exception e) {
                startActivity(webIntent);
            }
        }
    }
}
