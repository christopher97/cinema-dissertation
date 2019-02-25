package com.example.cinema_mobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cinema_mobile.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<Movie> movieList;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView ratingTextView;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            // bind the elements
            titleTextView = itemView.findViewById(R.id.listTitle);
            ratingTextView = itemView.findViewById(R.id.listRating);
            posterImageView = itemView.findViewById(R.id.listImage);
        }
    }

    public MovieAdapter(ArrayList<Movie> movies) { this.movieList = movies; }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // we need to inflate our layout for each row, and then pass it along
        // to our view holder
        View row = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_adapter, viewGroup, false);
        MovieViewHolder viewHolder = new MovieViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        // we customize our row here
        final Movie movie = movieList.get(position);

        holder.titleTextView.setText(movie.getTitle());
        holder.ratingTextView.setText(movie.getRating());
        Picasso.get().load(movie.getImageURL()).into(holder.posterImageView);

        final int currPos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(currPos);
            }
        });
    }

    @Override
    public int getItemCount() { return movieList.size(); }

    public void setOnClick(OnItemClicked onClick) { this.onClick = onClick; }
}
