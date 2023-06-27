package com.cleanup.todoc;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    private ToDocDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, ToDocDatabase.class).build();
    }

    @After
    public void tearDown() {
        database.close();
    }

    @Test
    public void insertAndGetAllProjects() throws InterruptedException {
        Project project = new Project("Projet Test", 0xFF000000);
        database.projectDao().insertProject(project);

        LiveData<List<Project>> liveData = database.projectDao().getAllProjects();
        List<Project> projects = getValue(liveData);

        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Projet Test", projects.get(0).getName());
        assertEquals(0xFF000000, projects.get(0).getColor());
    }

    @Test
    public void insertAndGetAllTasks() throws InterruptedException {
        Project project = new Project("Projet Test", 0xFF000000);
        database.projectDao().insertProject(project);

        LiveData<List<Project>> liveDataProjects = database.projectDao().getAllProjects();
        List<Project> projects = getValue(liveDataProjects);
        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Projet Test", projects.get(0).getName());
        assertEquals(0xFF000000, projects.get(0).getColor());

        Task task = new Task(projects.get(0).getId(), "Task Test", System.currentTimeMillis());
        database.taskDao().insertTask(task);

        LiveData<List<Task>> liveDataTasks = database.taskDao().getAllTasks();
        List<Task> tasks = getValue(liveDataTasks);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Task Test", tasks.get(0).getName());
        assertEquals(projects.get(0).getId(), tasks.get(0).getProjectId());
    }

    private <T> T getValue(LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);

        //noinspection unchecked
        return (T) data[0];
    }
}
