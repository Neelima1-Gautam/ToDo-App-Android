package com.neelima.todoapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.neelima.todoapplication.hello.AppDatabase;
import com.neelima.todoapplication.hello.Todo;

public class TodoDetail extends AppCompatActivity {

    private static final int DEFAULT_TODO_ID = -1;
    private int mTodoId = DEFAULT_TODO_ID;
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private AppDatabase mDb;

    TextView todo_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        todo_title = findViewById(R.id.todo_title);



        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTodoId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TODO_ID);
        }
        mDb = AppDatabase.getInstance(getApplicationContext());


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            if (mTodoId == DEFAULT_TODO_ID) {
                // populate the UI
                mTodoId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TODO_ID);

                AddTodoViewModelFactory factory = new AddTodoViewModelFactory(mDb, mTodoId);
                final AddTodoViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTodoViewModel.class);

                viewModel.getTodo().observe(TodoDetail.this, new Observer<Todo>() {
                    @Override
                    public void onChanged(@Nullable Todo todoEntry) {
                        viewModel.getTodo().removeObserver(this);
                        todo_title.setText(todoEntry.getTitle());
                    }
                });


            }
        }
    }
}
