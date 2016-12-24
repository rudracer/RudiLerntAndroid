package com.dummies.tasks.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout;

import com.dummies.tasks.R;
import com.squareup.picasso.Picasso;
//import android.R;

/**
 * Created by Rudi on 19.12.2016.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    static String[] fakeData = new String[] {
            "Eins",
            "Zwei",
            "Drei",
            "Vier",
            "Fünf",
            "Ah ... ah ... ah!"
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        //eine neue View erstellen
         CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_task, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Context context = viewHolder.titleView.getContext();

        viewHolder.titleView.setText(fakeData[i]);

        //Miniaturbild festlegen
        Picasso.with(context)
                .load(getImageUrlForTask(i))
                .into(viewHolder.imageView);
    }

    public static String getImageUrlForTask(long taskId) {
        return "http://lorempixel.com/600/400/cats/?fakeId=" + taskId;
    }

    @Override
    public int getItemCount() {
        return fakeData.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView;
        ImageView imageView;

        public ViewHolder (CardView card) {
            super(card);
            cardView = card;
            titleView = (TextView)card.findViewById(R.id.text1);
            imageView = (ImageView) card.findViewById(R.id.image);
        }
    }
}
