package com.example.nelther.recycleviewapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nelther on 05/11/2015.
 */
public class TareappBD  extends SQLiteOpenHelper{

    String TB_Profesor = "Create table profesor (idProfesor integer primary key, nombProfesor text)";

    String TB_Materia = "Create table materia (idMateria integer primary key, nomMateria text, idProfesor int, foreign key (idProfesor) references profesor (idProfesor)) ";

    String  TB_Tareas = "Create table Tarea (idTarea integer primary key, nomTarea text, fechaEntrega datetime, recordar int, descTarea text,Realizada int, idmateria int, Foreign key (idMateria) references materia (idMateria))";

    public TareappBD (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TB_Profesor);
        db.execSQL(TB_Materia);
        db.execSQL(TB_Tareas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
