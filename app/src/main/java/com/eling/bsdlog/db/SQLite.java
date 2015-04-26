package com.eling.bsdlog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SQLite {

    private SQLiteHelper sqliteHelper;
    private SQLiteDatabase db;

    /** Constructor de clase */
    public SQLite(Context context)
    {
        sqliteHelper = new SQLiteHelper( context );
    }

    /** Abre conexion a base de datos */
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteHelper.getDatabaseName() );
        db = sqliteHelper.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteHelper.getDatabaseName() );
        sqliteHelper.close();
    }

    /**
     * Metodo para agregar un nuevo registro
     /* @param String usuario
     /* @param String email
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean addRegistro( String usuario, String email)
    {
        if( usuario.length()> 0 )
        {
            ContentValues contentValues = new ContentValues();

            contentValues.put( sqliteHelper.__campo_usuario , usuario);
            contentValues.put( sqliteHelper.__campo_email , email  );

            Log.i("SQLite", "Nuevo registro " );
            return ( db.insert( sqliteHelper.__tabla__ , null, contentValues ) != -1 )?true:false;
        }
        else
            return false;
    }


    /**
     * Metodo que retorna el ID del ultimo usuario registrado
     * @return integer ID -1 si no existen registros
     * */
    public int getUltimoID()
    {
        int id = -1;
        //query(String table,
        //String[] columns,
        //String selection, String[] selectionArgs, String groupBy, String having,
        //String orderBy, String limit)
        Cursor cursor = db.query( sqliteHelper.__tabla__ ,
                new String[]{ sqliteHelper.__campo_id },
                null, null, null,null,
                sqliteHelper.__campo_id + " DESC ", "1");
        if( cursor.moveToFirst() )
        {
            do
            {
                id = cursor.getInt(0);
            } while ( cursor.moveToNext() );
        }
        return id;
    }

    /**
    /* @param INT ID del registro a eliminar
     * @return BOOLEAN
     * */
    public boolean borrar_registro( int id )
    {
        //table , whereClause, whereArgs
        return  (db.delete( sqliteHelper.__tabla__ , sqliteHelper.__campo_id + " = " + id ,  null) > 0) ? true:false;

    }
    /**
     * Obtiene todos los registros de la unica tabla de la base de datos
     * @return Cursor
     * */
    public Cursor getRegistros()
    {
        return db.query( sqliteHelper.__tabla__ ,
                new String[]{
                        sqliteHelper.__campo_id ,
                        sqliteHelper.__campo_usuario,
                        sqliteHelper.__campo_email,

                },
                null, null, null, null, null);
    }


    /**
     * Obtiene un registro
     * */
    public Cursor getRegistro( int id )
    {
        return db.query( sqliteHelper.__tabla__ ,
                new String[]{
                        sqliteHelper.__campo_id ,
                        sqliteHelper.__campo_usuario,
                        sqliteHelper.__campo_email,

                },
                sqliteHelper.__campo_id + " = " + id ,
                null, null, null, null);
    }

    /**
     * Dado un Cursor con los registros de la base de datos, da formato y retorna resultado
     * @return ArrayList<String>
     * */
    public ArrayList<String> getFormatListUsr( Cursor cursor )
    {
        ArrayList<String> listData = new ArrayList<String>();
        String item = "";
        if( cursor.moveToFirst() )
        {
            do
            {
                item += "ID: [" + cursor.getInt(0) + "]\r\n";
                item += "Usuario: " + cursor.getString(1) + "\r\n";
                item += "email: " + cursor.getString(2) + "";

                listData.add( item );
                item="";

            } while ( cursor.moveToNext() );
        }
        return listData;
    }

}