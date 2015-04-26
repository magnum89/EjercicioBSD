package com.eling.bsdlog;

import java.util.Calendar;
//

import com.eling.bsdlog.db.SQLite;
//
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadPrincipal extends Activity implements OnClickListener{



    private EditText txtUser;
    private EditText txtEmail;

    private Button btnRegistrar;

    private Button btnRegistros;


    //
    private SQLite sqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //

        txtUser = (EditText) findViewById( R.id.txtUser );
        txtEmail = (EditText) findViewById( R.id.txtEmail );
        btnRegistrar = (Button) findViewById(R.id.btnGuardar );
        btnRegistrar.setOnClickListener( this );

        btnRegistros = (Button) findViewById(R.id.btnUsuarios );
        btnRegistros.setOnClickListener( this );

        //base de datos
        sqlite = new SQLite( this );
        sqlite.abrir();
        //
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() )
        {
            case R.id.btnGuardar:


                //Registra en la base de datos
                if ( sqlite.addRegistro( txtUser.getText().toString(),
                        txtEmail.getText().toString()))
                {
                    //recupera ID de ultimo registro y pasa como parametro
                    int id = sqlite.getUltimoID();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    Intent intent = new Intent( ActividadPrincipal.this, RegistroActivity.class );/**agregar*/
                    intent.putExtras( bundle );
                    startActivity( intent );
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Error: Compruebe que los datos sean correctos"  ,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnUsuarios:
                Intent iRegs = new Intent( ActividadPrincipal.this, RegistrosActivity.class );
                startActivity( iRegs );
                break;

        }
    }



}
