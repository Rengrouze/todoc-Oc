package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class ToDocDatabase extends RoomDatabase {

    private static volatile ToDocDatabase INSTANCE;

    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    public static ToDocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ToDocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ToDocDatabase.class, "todoc_database").addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static Callback prepopulateDatabase() {

        return new Callback() {

            @Override

            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);

                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().insertProject( new Project( "Projet Tartampion", 0xFFEADAD1)));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().insertProject( new Project( "Projet Lucidia", 0xFFB4CDBA)));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao().insertProject( new Project( "Projet Circus", 0xFFA3CED2)));


            }

        };

    }
}
