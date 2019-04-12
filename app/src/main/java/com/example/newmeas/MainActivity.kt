package com.example.newmeas

import android.content.Intent
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newmeas.Models.Measures
import com.example.newmeas.Models.MeasuresVM
import com.example.newmeas.REALMS.RealmFactory
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MeasuresVM
   // private lateinit var adapterLV: BaseAdapter
    private lateinit var listview: ListView
    private var list: MutableList<Measures> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRealm("testBase")
        initVM()
        test()

    }

    private fun initRealm(dbName: String) {
        Realm.init(this)
        val realmFactory: RealmFactory = RealmFactory()
        realmFactory.setRealmConfiguration(dbName)

    }

    private fun initVM() {
        viewModel = ViewModelProviders.of(this).get(MeasuresVM::class.java)

        //initLV()

        viewModel.data.observe(this, Observer { mutableList ->
            //здесь идет работа с полученным списком данных

            message.text = "size = ${mutableList.size.toString()}"
            bigOut.text = ""

            /*
            очистка списка и повторное заполнение
             */

            listview = findViewById(R.id.listview)
            //adapterLV = MeasureListAdapter(this, mutableList)
          //  listview.adapter = adapterLV
          //  adapterLV.notifyDataSetChanged()


            for (tt in mutableList) {
                bigOut.append("${tt.name}\n")
            }
        })


    }

    private fun test() {

        var nameInBase: String

        addbutton.setOnClickListener {

            nameInBase = enterName.text.toString()
            viewModel.insert(nameInBase)
            bigOut.append("$nameInBase\n")



        }

        deletebutton.setOnClickListener {

            nameInBase = enterName.text.toString()


            Toast.makeText(this,"${viewModel.findByName(nameInBase)?.name ?:"no name"} was deleted", Toast.LENGTH_SHORT).show()
            viewModel.delete(nameInBase)

            //todo 2/ Сделать обновление адаптера. Если не получится, попробовать recyclerView.



        }

        showbutton.setOnClickListener {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
        }

        nextbutton.setOnClickListener {
            val intent = Intent(this, Main2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
