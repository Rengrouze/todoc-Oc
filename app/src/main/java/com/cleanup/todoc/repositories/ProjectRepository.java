package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }

    public void insertProject(Project project) {
        projectDao.insertProject(project);
    }

    public void deleteProject(Project project) {
        projectDao.deleteProject(project);
    }
}
