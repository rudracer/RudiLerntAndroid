package com.dummies.tasks.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toolbar;

import com.dummies.tasks.R;
import com.dummies.tasks.fragment.TaskEditFragment;
import com.dummies.tasks.interfaces.OnEditFinished;

public class TaskEditActivity extends Activity implements OnEditFinished {

    public static final String EXTRA_TASKID = "taskId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);
        setActionBar((Toolbar) findViewById(R.id.toolbar));

        long id = getIntent().getLongExtra(TaskEditActivity.EXTRA_TASKID, 0L); //11
        Fragment fragment = TaskEditFragment.newInstance(id);
        String fragmentTag = TaskEditFragment.DEFAULT_FRAGMENT_TAG; //14

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(
                    R.id.container,
                    fragment,
                    fragmentTag).commit();
        }
    }

    @Override
    public void finishEditingTask() {
        /* Wenn der Benutzer den Editor schließt,
        finish aufrufen, um die Aktivität zu zerstören.
         */
        finish();
    }

}
