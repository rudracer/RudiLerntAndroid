package com.dummies.tasks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;
import com.dummies.tasks.R;
import com.dummies.tasks.interfaces.OnEditTask;

public class TaskListActivity extends AppCompatActivity implements OnEditTask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
    }

    @Override
    public void editTask(long id) {
        /*Wenn wir aufgefordert werden, einen Task zu bearbeiten
        oder einzufügen, starten Sie TaskEditActivity mit der
        ID des zu bearbeitenden Tasks.
        */
        startActivity(new Intent(this, TaskEditActivity.class)
            .putExtra(TaskEditActivity.EXTRA_TASKID, id));
    }
}
