package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    @Test
    public void test_projects() {
        final Project project1 = new Project("Projet Tartampion", 0xFFEADAD1);
        project1.setId(1);
        final Project project2 = new Project("Projet Lucidia", 0xFFB4CDBA);
        project2.setId(2);
        final Project project3 = new Project("Projet Circus", 0xFFA3CED2);
        project3.setId(3);

        final Task task1 = new Task(project1.getId(), "task 1", new Date().getTime());
        final Task task2 = new Task(project2.getId(), "task 2", new Date().getTime());
        final Task task3 = new Task(project3.getId(), "task 3", new Date().getTime());
        final Task task4 = new Task(0, "task 4", new Date().getTime());

        List<Project> allProjects = new ArrayList<>();
        allProjects.add(project1);
        allProjects.add(project2);
        allProjects.add(project3);

        assertEquals(project1, task1.getProject(allProjects));
        assertEquals(project2, task2.getProject(allProjects));
        assertEquals(project3, task3.getProject(allProjects));
        assertNull(task4.getProject(allProjects));
    }




    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1,  "aaa", 123);
        final Task task2 = new Task(2,  "zzz", 124);
        final Task task3 = new Task(3,  "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1,  "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3,  "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1,  "aaa", 123);
        final Task task2 = new Task(2,  "zzz", 124);
        final Task task3 = new Task(3,  "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1,  "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3,  "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}