package com.neelima.todoapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.neelima.todoapplication.hello.AppDatabase;
import com.neelima.todoapplication.hello.Todo;

public class AddTodoViewModel extends ViewModel {

    private LiveData<Todo> todo;

    public AddTodoViewModel(AppDatabase database, int todoId){
        todo = database.todoDao().loadTodoById(todoId);
    }

    public LiveData<Todo> getTodo() {
        return todo;
    }


}
