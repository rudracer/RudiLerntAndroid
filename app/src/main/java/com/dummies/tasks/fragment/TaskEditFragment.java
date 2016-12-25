package com.dummies.tasks.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.dummies.tasks.R;
import com.dummies.tasks.activity.TaskEditActivity;

/**
 * Created by Rudi on 25.12.2016.
 */

public class TaskEditFragment extends Fragment {

    static final String TASK_ID = "taskId";

    public static final String DEFAULT_FRAGMENT_TAG = "taskEditFragment";

    //Views
    View rootView;
    EditText titleText;
    EditText notesText;
    ImageView imageView;

    long taskId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getLong(TaskEditActivity.EXTRA_TASKID, 0L);
        }

        if (savedInstanceState != null) {
            taskId = savedInstanceState.getLong(TASK_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        Dieses Feld kann sich geänder haben, während
        unsere Aktivität ausgeführt wurde, wir müssen also sicherstellen,
        dass wir sie in unserem outState-Bundle speichern, sodass wir sie
        später in onCreate wiederherstellen können.
         */
        outState.putLong(TASK_ID, taskId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_edit, container, false);

        rootView = v.getRootView();
        titleText = (EditText) v.findViewById(R.id.title);
        notesText = (EditText) v.findViewById(R.id.notes);
        imageView = (ImageView) v.findViewById(R.id.image);

        return v;
    }
}
