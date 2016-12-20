package com.dummies.tasks.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.FrameLayout;

import com.dummies.tasks.R;
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
        viewHolder.titleView.setText(fakeData[i]);
    }

    @Override
    public int getItemCount() {
        return fakeData.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView;

        public ViewHolder (CardView card) {
            super(card);
            cardView = card;
            titleView = (TextView)card.findViewById(R.id.text1);
        }
    }
}
