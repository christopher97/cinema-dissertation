package com.example.cinema_mobile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cinema_mobile.models.Ticket;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    ArrayList<Ticket> ticketList;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {

        ImageView ticketImage;
        TextView ticketTitle;
        TextView ticketTime;
        TextView ticketDate;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            ticketImage = itemView.findViewById(R.id.ticketImage);
            ticketTitle = itemView.findViewById(R.id.ticketTitle);
            ticketTime = itemView.findViewById(R.id.ticketTime);
            ticketDate = itemView.findViewById(R.id.ticketDate);
        }
    }

    public TicketAdapter(ArrayList<Ticket> tickets) { this.ticketList = tickets; }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // we need to inflate our layout for each row, and then pass it along
        // to our view holder
        View row = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ticket_adapter, viewGroup, false);
        TicketViewHolder viewHolder = new TicketViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketViewHolder holder, int position) {
        // we customize our row here
        final Ticket ticket = ticketList.get(position);

        Picasso.get().load(ticket.getImageURL()).into(holder.ticketImage);
        holder.ticketTitle.setText(ticket.getMovieTitle());
        holder.ticketDate.setText(ticket.getDate());
        holder.ticketTime.setText(ticket.getTime());

        final int currPos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(currPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public void setOnClick(OnItemClicked onClick) { this.onClick = onClick; }
}
