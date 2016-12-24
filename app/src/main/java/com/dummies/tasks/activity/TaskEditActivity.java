package com.dummies.tasks.activity;

import android.os.Bundle;
import android.app.Activity;

import com.dummies.tasks.R;

public class TaskEditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
