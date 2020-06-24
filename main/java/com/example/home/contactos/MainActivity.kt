package com.example.home.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.*

class MainActivity : AppCompatActivity() {

    var lista: ListView? = null
    var grid: GridView? = null
    var viewSwitcher: ViewSwitcher? = null

    companion object{
        var contactos: ArrayList<Contacto>? = null
        var adaptador: AdaptadorCustom? = null
        var adaptadorGrid: AdaptadorCustomGrid? = null

        fun agregarContacto(contacto: Contacto){
            adaptador?.addItem(contacto)
            adaptadorGrid?.addItem(contacto)
        }

        fun obtenerContacto(index: Int): Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index: Int){
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index: Int, nuevoContacto: Contacto){
            adaptador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Marcos", "Perez",
            "Freelance", 25, 70.0F,
            "Calle Falsa 123", "7632-9873",
            "marcos@freelane.com", R.drawable.foto_01))
        contactos?.add(Contacto("Juan", "Gomez",
            "Globant", 35, 80.0F,
            "Calle Falsa 145", "74532-9873",
            "juan@gomez.com", R.drawable.foto_02))
        contactos?.add(Contacto("Mario", "Roteta",
            "Kiosco", 55, 90.0F,
            "Sarmiento 839", "7662-9873",
            "tata@hotmail.com", R.drawable.foto_03))
        contactos?.add(Contacto("Morena", "Rialseguroqueno",
            "HarryPoter", 25, 60.0F,
            "Fake st 123", "7632-9875",
            "morenita@hermione.com", R.drawable.foto_04))
        contactos?.add(Contacto("María", "Ladelbarrio",
            "Imitadora", 25, 60.0F,
            "Talia 123", "7632-9804",
            "talita@freelane.com", R.drawable.foto_05))
        contactos?.add(Contacto("Corona", "Virus",
            "Exterminio", 95, 90.0F,
            "Muerte 123", "7632-3973",
            "aguante@thanos.com", R.drawable.foto_06))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            // Preparar los datos
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar
                adaptador?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filtrar
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.itemNuevo -> {
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
    }
}
