package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.List;

@Entity(
        tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "projectId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Task {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "projectId")
    private long projectId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "creationTimestamp")
    private long creationTimestamp;


    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public Project getProject(List<Project> allProjects) {
        for (Project p:allProjects) {
            if(p.getId() == projectId){
                return p;
            }

        }
        return null;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
    // Comparator classes for sorting tasks
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return Long.compare(right.creationTimestamp, left.creationTimestamp);
        }
    }

    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return Long.compare(left.creationTimestamp, right.creationTimestamp);
        }
    }
}