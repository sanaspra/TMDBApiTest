package com.sample.tmdbassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sample.tmdbassignment.adapter.MoviesListAdapter;
import com.sample.tmdbassignment.model.Movie;
import com.sample.tmdbassignment.presenter.MoviesListPresenter;
import com.sample.tmdbassignment.presenter.SearchMoviePresenter;
import com.sample.tmdbassignment.util.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesListAdapter.OnMovieItemClicked, MoviesListPresenter.MoviesListPresenterListener,
        SearchMoviePresenter.SearchMovieListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SearchMoviePresenter searchMoviePresenter;
    private CoordinatorLayout coordinatorLayout;
    private AppCompatTextView noMoviesMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.movies_layout_container);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        noMoviesMessage = (AppCompatTextView) findViewById(R.id.no_movies_message);
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);

        MoviesListPresenter presenter = new MoviesListPresenter(this);
        presenter.getMoviesList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMoviePresenter = new SearchMoviePresenter(MainActivity.this);
                searchMoviePresenter.searchMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onMovieSelected(@NonNull Movie movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra(Constants.KEY_MOVIE_ID, movie.getId());
        startActivity(detailIntent);
    }

    @Override
    public void onMoviesListDownloaded(List<Movie> moviesList) {
        progressBar.setVisibility(View.GONE);
        noMoviesMessage.setVisibility(View.GONE);
        recyclerView.setAdapter(new MoviesListAdapter(moviesList, MainActivity.this));
    }

    @Override
    public void onMoviesListDownloadFailed() {
        progressBar.setVisibility(View.GONE);
        noMoviesMessage.setVisibility(View.VISIBLE);
        Snackbar.make(coordinatorLayout, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onMovieSearchCompleted(List<Movie> movies) {
        progressBar.setVisibility(View.GONE);
        noMoviesMessage.setVisibility(View.GONE);
        recyclerView.setAdapter(new MoviesListAdapter(movies, MainActivity.this));
    }

    @Override
    public void onMovieSearchFailed() {
        progressBar.setVisibility(View.GONE);
        noMoviesMessage.setVisibility(View.VISIBLE);
        Snackbar.make(coordinatorLayout, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }
}
