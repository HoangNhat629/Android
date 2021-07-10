package com.example.android1_module3_tmdb.api;

import com.example.android1_module3_tmdb.models.DeleteSessionIdRequest;
import com.example.android1_module3_tmdb.models.DeleteSessionIdResponse;
import com.example.android1_module3_tmdb.models.GetCreateRequestTokenResponse;
import com.example.android1_module3_tmdb.models.GetMovieAccountStatesResponse;
import com.example.android1_module3_tmdb.models.GetMovieDetailResponse;
import com.example.android1_module3_tmdb.models.GetMoviesResponse;
import com.example.android1_module3_tmdb.models.PostCreateSessionRequest;
import com.example.android1_module3_tmdb.models.PostCreateSessionResponse;
import com.example.android1_module3_tmdb.models.PostCreateSessionWithLoginRequest;
import com.example.android1_module3_tmdb.models.PostCreateSessionWithLoginResponse;
import com.example.android1_module3_tmdb.models.SetFavouriteMovieRequest;
import com.example.android1_module3_tmdb.models.SetFavouriteMovieResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String apiKey = "a5753dd20d8bf169dbcdff9e7d730148";

    @GET("discover/movie?api_key=" + apiKey)
    Call<GetMoviesResponse> getMovies(@Query("page") int page);

    @GET("movie/{movie_id}?api_key=" + apiKey + "&append_to_response=videos")
    Call<GetMovieDetailResponse> getMovieDetail(@Path("movie_id") int movieId);

    @GET("authentication/token/new?api_key=" + apiKey)
    Call<GetCreateRequestTokenResponse> getCreateRequestToken();

    @POST("authentication/token/validate_with_login?api_key=" + apiKey)
    Call<PostCreateSessionWithLoginResponse> postCreateSessionWithLogin(@Body PostCreateSessionWithLoginRequest body);

    @POST("authentication/session/new?api_key=" + apiKey)
    Call<PostCreateSessionResponse> postCreateSession(@Body PostCreateSessionRequest body);

//    @DELETE("authentication/session?api_key=" + apiKey)
    @HTTP(method = "DELETE", path = "authentication/session?api_key=" + apiKey, hasBody = true)
    Call<DeleteSessionIdResponse> deleteSessionId(@Body DeleteSessionIdRequest body);

    @POST("account/{account_id}/favorite?api_key=" + apiKey)
    Call<SetFavouriteMovieResponse> setFavouriteMovie(@Body SetFavouriteMovieRequest body, @Query("session_id") String sessionId);

    @GET("movie/{movie_id}/account_states?api_key=" + apiKey)
    Call<GetMovieAccountStatesResponse> getMovieAccountStates(@Path("movie_id") int movieId, @Query("session_id") String sessionId);
}
