package com.sample.tmdbassignment.presenter;


import com.sample.tmdbassignment.model.Movie;
import com.sample.tmdbassignment.service.ServiceApi;
import com.sample.tmdbassignment.service.ServiceApiInterface;
import com.sample.tmdbassignment.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter {

    private final MovieDetailListener listener;
    private final ServiceApiInterface service;

    public interface MovieDetailListener {
        void onMovieDetailLoaded(Movie movie);

        void onMovieDetailFailed();
    }

    public MovieDetailPresenter(MovieDetailListener listener) {
        this.listener = listener;
        service = ServiceApi.getClient().create(ServiceApiInterface.class);
    }

    public void getMovieDetail(final int movieId) {
        Call<Movie> call = service.getMovieDetails(movieId, Constants.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (null != response.body()) {
                    listener.onMovieDetailLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                listener.onMovieDetailFailed();
            }
        });
    }
}
