package com.example.agenda.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class,Customer.class,CustomerProductCrossRef.class},version = 15,exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract CustomerDAO customerDAO();
    public abstract CustomerProductCrossRefDao customerProductCrossRefDao();
    private static volatile AgendaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService dataBaseWriteExecutor=
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AgendaDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (AgendaDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AgendaDatabase.class,"agenda_dp")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    dataBaseWriteExecutor.execute(() ->{

                    });
                }
            };
}
