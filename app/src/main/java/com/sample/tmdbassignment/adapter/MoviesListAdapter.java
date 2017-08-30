package com.sample.tmdbassignment.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.tmdbassignment.R;
import com.sample.tmdbassignment.model.Movie;
import com.sample.tmdbassignment.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private final List<Movie> movies;
    private final OnMovieItemClicked itemClicked;

    public MoviesListAdapter(List<Movie> movies, OnMovieItemClicked itemClicked) {
        this.movies = movies;
        this.itemClicked = itemClicked;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(String.valueOf(movies.get(position).getVoteAverage()));
        Picasso.with(holder.poster.getContext()).load(Constants.IMAGE_BASE_URL + "/w92" + movies.get(position).getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        final TextView movieTitle;
        final TextView data;
        final TextView movieDescription;
        final TextView rating;
        final ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.title);
            data = (TextView) itemView.findViewById(R.id.subtitle);
            movieDescription = (TextView) itemView.findViewById(R.id.description);
            rating = (TextView) itemView.findViewById(R.id.rating);
            poster = (ImageView) itemView.findViewById(R.id.poster);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    itemClicked.onMovieSelected(movies.get(getAdapterPosition()));
                }
            }, 200); // <--- Giving time to the ripple effect finish before opening a new activity
        }
    }

    public interface OnMovieItemClicked {
        void onMovieSelected(@NonNull Movie movie);
    }
}
