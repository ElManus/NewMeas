package com.example.newmeas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmeas.Adapters.CustomAdapter
import com.example.newmeas.Models.Measures
import com.example.newmeas.Models.MeasuresVM
import com.example.newmeas.REALMS.RealmFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MeasuresVM
    private lateinit var objAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        initRealm("testBase")
        initVM()

        val fab = findViewById<FloatingActionButton>(R.id.add_fab)
        fab.setOnClickListener {
            viewModel.insert("test"+ Random.nextInt(0,100))
            val intent = Intent(this, AddCounterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun clickedRecyclerItem(mess: Measures) {

        viewModel.delete(mess.name)
        objAdapter.updateList(viewModel.getAll()!!)
        recyclerView.adapter = objAdapter
    }

    private fun initRealm(dbName: String) {
        Realm.init(this)
        val realmFactory = RealmFactory()
        realmFactory.setRealmConfiguration(dbName)

    }


    private fun initVM() {
        viewModel = ViewModelProviders.of(this).get(MeasuresVM::class.java)

        viewModel.data.observe(this, Observer { mutableList ->

            objAdapter = CustomAdapter(mutableList) { mess: Measures -> clickedRecyclerItem(mess) }
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recyclerView.adapter = objAdapter

        })


    }
}
