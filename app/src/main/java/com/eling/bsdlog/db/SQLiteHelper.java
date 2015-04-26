package com.eling.bsdlog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper
{

    //nombre de la base de datos
    private static final String __DATABASE = "dbBSD";
    //versión de la base de datos
    private static final int __VERSION = 1;
    //nombre tabla y campos de tabla
    public final String __tabla__ = "Usuarios";
    public final String __campo_id = "id";
    public final String __campo_usuario = "Usurio";
    public final String __campo_email = "FechaNac";

    //Instrucción SQL para crear las tablas


    private final String sql = "CREATE TABLE " + __tabla__ + " ( " +
            __campo_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            __campo_usuario + " TEXT NULL, " + __campo_email + " TEXT " + " )";

    /**
     * Constructor de clase
     * */
    public SQLiteHelper(Context context) {
        super( context, __DATABASE, null, __VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion, int newVersion ) {
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS " + __tabla__ );
            //y luego creamos la nueva tabla
            db.execSQL( sql );
        }
    }

}