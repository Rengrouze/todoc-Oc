package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TodoListViewModel extends ViewModel {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final Executor executor;

    public TodoListViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.executor = executor;
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectRepository.getAllProjects();
    }



    public LiveData<List<Task>> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void insertTask(Task task) {
        executor.execute(() -> taskRepository.insertTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskRepository.deleteTask(task));
    }
}
