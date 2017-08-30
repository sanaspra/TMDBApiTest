package com.sample.tmdbassignment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.sample.tmdbassignment.model.Movie;
import com.sample.tmdbassignment.presenter.MovieDetailPresenter;
import com.sample.tmdbassignment.util.Constants;
import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity implements MovieDetailPresenter.MovieDetailListener {

    private AppCompatTextView title, year, overview;
    private ImageView backgroundImage;
    private RatingBar ratingBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView((R.layout.activity_movie_detail));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.movie_detail_container);
        title = (AppCompatTextView) findViewById(R.id.title);
        year = (AppCompatTextView) findViewById(R.id.year);
        overview = (AppCompatTextView) findViewById(R.id.overview);
        backgroundImage = (ImageView) findViewById(R.id.cover);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
       MovieDetailPresenter presenter = new MovieDetailPresenter(this);

        Bundle data = getIntent().getExtras();
        if (null != data) {
            int movieId = data.getInt(Constants.KEY_MOVIE_ID);
            presenter.getMovieDetail(movieId);
        }
    }

    @Override
    public void onMovieDetailLoaded(Movie movie) {
        title.setText(movie.getTitle());
        year.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage().floatValue());
        Picasso.with(MovieDetailActivity.this).load(Constants.IMAGE_BASE_URL + "w780" + movie.getPosterPath()).into(backgroundImage);
    }

    @Override
    public void onMovieDetailFailed() {
        Snackbar.make(coordinatorLayout, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }
}
