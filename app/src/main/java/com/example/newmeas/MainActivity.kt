package com.example.newmeas

import android.os.Bundle
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
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){


        private lateinit var viewModel: MeasuresVM
        // private lateinit var recyclerView: RecyclerView
        private var list: MutableList<Measures> = mutableListOf()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            /*
        test
         */
            var testlist: ArrayList<Measures> = arrayListOf()
            var mes1 = Measures()
            mes1.name = "1011"
            var mes2 = Measures()
            mes2.name = "222"

            testlist.add(mes1)
            testlist.add(mes2)

            Toast.makeText(this, mes2.name, Toast.LENGTH_SHORT).show()

            //Todo разобраться, как тут работает лямбда-функия, почитать учебник.
            val objAdapter = CustomAdapter(testlist) { mess: Measures -> clickedRecyclerItem(mess)}
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recyclerView.adapter = objAdapter


            initRealm("testBase")
            initVM()

        }

    private fun clickedRecyclerItem(mess: Measures) {
        Toast.makeText(applicationContext, mess.name, Toast.LENGTH_SHORT).show()

    }

    private fun initRealm(dbName: String) {
            Realm.init(this)
            val realmFactory: RealmFactory = RealmFactory()
            realmFactory.setRealmConfiguration(dbName)

        }


        private fun initVM() {
            viewModel = ViewModelProviders.of(this).get(MeasuresVM::class.java)

            viewModel.data.observe(this, Observer { mutableList ->
                //здесь идет работа с полученным списком данных

                // message.text = "size = ${mutableList.size.toString()}"
                // bigOut.text = ""

                /*
            очистка списка и повторное заполнение
             */


                //  adapter = CountersRecyclerAdapter(mutableList, this)
                //  recyclerView.adapter = adapter


                /*for (tt in mutableList) {
                bigOut.append("${tt.name}\n")
            }*/
            })



    }
}
