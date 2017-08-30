package com.sample.tmdbassignment.service;


import com.sample.tmdbassignment.model.Movie;
import com.sample.tmdbassignment.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApiInterface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@SuppressWarnings("SameParameterValue") @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @SuppressWarnings("SameParameterValue") @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@SuppressWarnings("SameParameterValue") @Query("api_key") String apiKey, @Query("query") String searchQuery);
}
