package com.dummies.tasks.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dummies.tasks.R;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //0x7f0c006d = 2131492973
        setActionBar(toolbar);
        //toolbar.setTitle("Hallo");
        //setActionBar ((android.widget.Toolbar) findViewById(R.id.toolbar));
    }


}
