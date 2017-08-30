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

public class SearchMoviePresenter {

    private final SearchMovieListener listener;
    private final ServiceApiInterface service;

    public interface SearchMovieListener {
        void onMovieSearchCompleted(List<Movie> movies);

        void onMovieSearchFailed();
    }

    public SearchMoviePresenter(SearchMovieListener listener) {
        this.listener = listener;
        service = ServiceApi.getClient().create(ServiceApiInterface.class);
    }

    public void searchMovie(String searchQuery) {
        Call<MovieResponse> call = service.searchMovie(Constants.API_KEY, searchQuery);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (null != response.body()) {
                    listener.onMovieSearchCompleted(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listener.onMovieSearchFailed();
            }
        });
    }
}
