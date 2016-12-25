package com.dummies.tasks.activity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toolbar;

import com.dummies.tasks.R;

public class TaskEditActivity extends Activity {

    public static final String EXTRA_TASKID = "taskId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        setActionBar((Toolbar) findViewById(R.id.toolbar));
    }

}
