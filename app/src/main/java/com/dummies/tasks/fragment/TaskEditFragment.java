package com.dummies.tasks.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dummies.tasks.R;
import com.dummies.tasks.activity.TaskEditActivity;
import com.dummies.tasks.adapter.TaskListAdapter;
import com.dummies.tasks.interfaces.OnEditFinished;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Rudi on 25.12.2016.
 */

public class TaskEditFragment extends Fragment {

    private static final int MENU_SAVE = 1;

    static final String TASK_ID = "taskId";
    static final String TASK_DATE_AND_TIME = "taskDateAndTime";

    public static final String DEFAULT_FRAGMENT_TAG = "taskEditFragment";

    //Views
    View rootView;
    EditText titleText;
    EditText notesText;
    ImageView imageView;
    TextView dateButton;
    TextView timeButton;

    //Informationen über diesen Task, den wir hier speichern,
    //bis wir ihn in der Datenbank speichern
    long taskId;
    Calendar taskDateAndTime;

    public static TaskEditFragment newInstance(long id) {
        TaskEditFragment fragment = new TaskEditFragment();
        Bundle args = new Bundle();
        args.putLong(TaskEditActivity.EXTRA_TASKID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getLong(TaskEditActivity.EXTRA_TASKID, 0L);
        }

        if (savedInstanceState != null) {
            taskId = savedInstanceState.getLong(TASK_ID);
            //Datum wiederherstellen
            taskDateAndTime = (Calendar) savedInstanceState.getSerializable(TASK_DATE_AND_TIME);
        }
        //Wenn kein vorheriges Datum, "now" verwenden
        if (taskDateAndTime == null) {
            taskDateAndTime = Calendar.getInstance();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        Dieses Feld kann sich geändert haben, während
        unsere Aktivität ausgeführt wurde, wir müssen also sicherstellen,
        dass wir sie in unserem outState-Bundle speichern, sodass wir sie
        später in onCreate wiederherstellen können.
         */
        outState.putLong(TASK_ID, taskId);
        outState.putSerializable(TASK_DATE_AND_TIME, taskDateAndTime);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_edit, container, false);

        //Aus dem Layout Views abrufen, die wir verwenden werden
        rootView = v.getRootView();
        titleText = (EditText) v.findViewById(R.id.title);
        notesText = (EditText) v.findViewById(R.id.notes);
        imageView = (ImageView) v.findViewById(R.id.image);
        dateButton = (TextView) v.findViewById(R.id.task_date);
        timeButton = (TextView) v.findViewById(R.id.task_time);

        updateDateAndTimeButtons();


        //Miniaturbild festlegen
        Picasso.with(getActivity())
                .load(TaskListAdapter.getImageUrlForTask(taskId))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Activity activity = getActivity();

                        if (activity == null) {
                            return;
                        }

                        //Farben der Aktivität auf den Farben des Bildes setzen, falls verfügbar
                        Bitmap bitmap = ((BitmapDrawable) imageView
                        .getDrawable())
                                .getBitmap();
                        Palette.Builder builder = new Palette.Builder(bitmap);
                        Palette palette = builder.generate();
                        int bgColor = palette.getLightMutedColor(0);

                        if (bgColor != 0) {
                            rootView.setBackgroundColor(bgColor);
                        }
                    }

                    @Override
                    public void onError() {
                        //Nichts machen; wir verwenden die Standardfarben
                    }
                });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, MENU_SAVE, 0, R.string.confirm)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Speichern-Schaltfläche wurde gedrückt
            case MENU_SAVE:
                //save();

                ((OnEditFinished) getActivity()).finishEditingTask();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Diese Methode aufrufen, wenn sich Datum/Zeit des Tasks geändert haben und wir
     * unsere Datums- und Zeitschaltflächen aktualisieren müssen.
     */
    private void updateDateAndTimeButtons() {
        /**
         *  Text für die Zeitschaltfläche sicherstellen
         *  Sicherstellen, dass nachfolgend java.text.DateFormat importiert wird
         */
        DateFormat timeFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String timeForButton = timeFormat.format(taskDateAndTime.getTime());
        timeButton.setText(timeForButton);

        //Text für Datumsschaltfläche festlegen
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String dateForButton = dateFormat.format(taskDateAndTime.getTime());
        dateButton.setText(dateForButton);
    }
}
