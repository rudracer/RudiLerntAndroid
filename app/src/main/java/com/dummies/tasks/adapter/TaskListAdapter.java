package com.dummies.tasks.adapter;

import android.support.v7.widget.RecyclerView;

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
}
