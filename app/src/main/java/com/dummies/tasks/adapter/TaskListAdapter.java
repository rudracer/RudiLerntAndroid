package com.dummies.tasks.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.FrameLayout;

import com.dummies.tasks.R;
import com.dummies.tasks.activity.TaskListActivity;
import com.dummies.tasks.interfaces.OnEditTask;
import com.squareup.picasso.Picasso;
//import android.R;

/**
 * Created by Rudi on 19.12.2016.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private static String[] fakeData = new String[] {
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Context context = viewHolder.titleView.getContext();

        viewHolder.titleView.setText(fakeData[i]);

        //Miniaturbild festlegen
        Picasso.with(context)
                .load(getImageUrlForTask(i))
                .into(viewHolder.imageView);

        //Klick-Aktion festlegen
        viewHolder.cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((OnEditTask) getActivity(context)).editTask(i);
                    }
                }
        );

        viewHolder.cardView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {

                        new AlertDialog.Builder(getActivity(context), R.style.MyDialogTheme) //7
                        .setTitle(R.string.delete_q) //8
                        .setMessage(viewHolder.titleView.getText()) //9
                        .setCancelable(true) //10
                        .setNegativeButton(android.R.string.cancel, null) //11
                        .setPositiveButton(R.string.delete,  //12
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteTask(getActivity(context), i); //20
                                    }
                                })
                                .show(); //23
                        return true;
                    }
                }
        );
    }

    void deleteTask(Context context, long id) {
        Log.d("TaskListAdapter", "Wir haben deleteTask aufgerufen :D");
    }

    private Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public static String getImageUrlForTask(long taskId) {
        return "http://lorempixel.com/600/400/cats/" + taskId%10;
    }

    @Override
    public int getItemCount() {
        return fakeData.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleView;
        ImageView imageView;

        ViewHolder(CardView card) {
            super(card);
            cardView = card;
            titleView = (TextView)card.findViewById(R.id.text1);
            imageView = (ImageView) card.findViewById(R.id.image);
        }
    }
}
