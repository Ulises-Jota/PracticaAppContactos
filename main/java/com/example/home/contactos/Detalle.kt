package com.example.home.contactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class Detalle : AppCompatActivity() {

    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
        // Log.d("INDEX", index.toString())

        mapearDatos()
    }

    fun mapearDatos(){
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<TextView>(R.id.tv_Nombre)
        val tvEmpresa = findViewById<TextView>(R.id.tv_Empresa)
        val tvEdad = findViewById<TextView>(R.id.textView6)
        val tvPeso = findViewById<TextView>(R.id.textView7)
        val tvTelefono = findViewById<TextView>(R.id.textView8)
        val tvEmail = findViewById<TextView>(R.id.textView9)
        val tvDireccion = findViewById<TextView>(R.id.textView12)
        val ivFoto = findViewById<ImageView>(R.id.iv_foto)

        tvNombre.text = contacto.nombre + " " + contacto.apellido
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString()
        tvPeso.text = contacto.peso.toString()
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        ivFoto.setImageResource(contacto.foto)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iEditar -> {
                val intent = Intent(this, Nuevo::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }
            R.id.iEliminar -> {
                MainActivity.eliminarContacto(index)
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }
}
