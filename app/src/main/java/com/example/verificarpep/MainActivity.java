package com.example.verificarpep;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton code_qr;
    private final int MIS_PERMISOS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        code_qr = findViewById(R.id.code_qr);
        code_qr.setOnClickListener(this);
        solicitaPermisosVersionesSuperiores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            //drawerLayout.closeDrawer(GravityCompat.START);
           *//* About_Dialog about_dialog = new About_Dialog();
            about_dialog.show(Intent, about_dialog.getTag());*//*
            Intent intent = new Intent(this, About_Dialog.class);
            startActivity(intent);
        }*/

        return super.onOptionsItemSelected(item);
    }


    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }
        //validamos si los permisos ya fueron aceptados
        if(MainActivity.this.checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)||(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)))){
            Toast.makeText(this,"Tiene que hacertar los permisos",Toast.LENGTH_LONG).show();
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{ Manifest.permission.CAMERA}, MIS_PERMISOS);
        }
        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
                    requestPermissions(new String[]{CAMERA,},100);
                }

            }
        });
        dialogo.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.code_qr:
                Intent intent = new Intent(this, ScannerSalidaProductoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
