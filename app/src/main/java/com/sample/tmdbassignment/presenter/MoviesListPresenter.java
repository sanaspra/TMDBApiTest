package com.sample.tmdbassignment.presenter;


import com.sample.tmdbassignment.model.Movie;
import com.sample.tmdbassignment.model.MovieResponse;
import com.sample.tmdbassignment.service.ServiceApi;
import com.sample.tmdbassignment.service.ServiceApiInterface;
import com.sample.tmdbassignment.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListPresenter {
    private final MoviesListPresenterListener listener;
    private final ServiceApiInterface service;

    public interface MoviesListPresenterListener {
        void onMoviesListDownloaded(List<Movie> moviesList);

        void onMoviesListDownloadFailed();
    }

    public MoviesListPresenter(MoviesListPresenterListener listener) {
        this.listener = listener;
        service = ServiceApi.getClient().create(ServiceApiInterface.class);
    }

    public void getMoviesList() {
        Call<MovieResponse> call = service.getTopRatedMovies(Constants.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (null != response.body())
                    listener.onMoviesListDownloaded(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listener.onMoviesListDownloadFailed();
            }
        });
    }

}
