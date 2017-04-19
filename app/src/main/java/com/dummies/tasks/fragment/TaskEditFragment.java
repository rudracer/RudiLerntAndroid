package com.dummies.tasks.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dummies.tasks.R;
import com.dummies.tasks.activity.TaskEditActivity;
import com.dummies.tasks.adapter.TaskListAdapter;
import com.dummies.tasks.interfaces.OnEditFinished;
import com.dummies.tasks.provider.TaskProvider;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Rudi on 25.12.2016.
 */

public class TaskEditFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    //Dies ist die Methode, die unser DatePicker-Dialog aufruft, wenn der Benuntzer im Dialog ein
    //Datum auswählt:
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        taskDateAndTime.set(Calendar.YEAR, year);
        taskDateAndTime.set(Calendar.MONTH, month);
        taskDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDateAndTimeButtons();
    }

    //Dies ist die Methode, die unser TimePicker-Dialog aufruft, wenn der Benuntzer im Dialog eine
    //Zeit auswählt:
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        taskDateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        taskDateAndTime.set(Calendar.MINUTE, minute);
        updateDateAndTimeButtons();
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

        //Teilt den Datums- und Zeitschaltflächen mit, was sie tun sollen,
        //wenn sie angeklickt werden
        dateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker();
                    }
                }
        );

        timeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTimePicker();
                    }
                }
        );


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

    //Hilfsmethode, die unseren Datumswähler anzeigt
    private void showDatePicker() {
        //Eine Fragment-Transaktion erstellen
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        DatePickerDialogFragment newFragment = DatePickerDialogFragment.newInstance(taskDateAndTime);

        newFragment.show(ft, "datePicker");
    }

    private void showTimePicker() {
        //Eine Fragment-Transaktion erstellen
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        TimePickerDialogFragment fragment = TimePickerDialogFragment.newInstance(taskDateAndTime);

        fragment.show(ft, "timePicker");
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
                save();

                //((OnEditFinished) getActivity()).finishEditingTask(); //Später vielleicht

                return true;
        }
        //Wenn wir dieses Menüelement nicht verarbeiten können, prüfen, ob die
        //übergeordnete Routine das kann
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
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        String timeForButton = timeFormat.format(taskDateAndTime.getTime());
        timeButton.setText(timeForButton);

        //Text für Datumsschaltfläche festlegen
        DateFormat dateFormat = DateFormat.getDateInstance();
        String dateForButton = dateFormat.format(taskDateAndTime.getTime());
        dateButton.setText(dateForButton);
    }

    private void save() {
        //Alle vom Benutzer eingegebenen Werte in ein ContentValues-Objekt einfügen
        String title = titleText.getText().toString();
        ContentValues values = new ContentValues();
        values.put(TaskProvider.COLUMN_TITLE, title);
        values.put(TaskProvider.COLUMN_NOTES,
                notesText.getText().toString());

        values.put(TaskProvider.COLUMN_DATE_TIME,
                taskDateAndTime.getTimeInMillis());

        //taskId == 0, wenn wir einen neuen Task erstellen, andernfalls die id des zu
        //bearbeitenden Tasks.
        if (taskId == 0) {
            //Den neuen Task erstellen und die taskId auf die Id des neuen Tasks setzen.
            Uri itemUri = getActivity().getContentResolver()
                    .insert(TaskProvider.CONTENT_URI, values);
            taskId = ContentUris.parseId(itemUri);

        } else {
            //Vorhandenen Task aktualisieren
            Uri uri = ContentUris.withAppendedId(TaskProvider.CONTENT_URI, taskId);
            int count = getActivity().getContentResolver().update(
                    uri, values, null, null);

            //Wenn wir irgendwie nicht genau einen Task bearbeitet haben, einen Fehler werfen
            if (count != 1) {
                throw new IllegalStateException("Aktualisieren nicht möglich: " + taskId);
            }

        }

        Toast.makeText(
                getActivity(),
                getString(R.string.task_saved_message),
                Toast.LENGTH_SHORT).show();
    }
}
