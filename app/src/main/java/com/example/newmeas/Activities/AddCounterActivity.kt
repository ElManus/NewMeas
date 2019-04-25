package com.example.newmeas.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmeas.Adapters.CustomAdapter
import com.example.newmeas.Data.Measures
import com.example.newmeas.Data.MeasuresVM
import com.example.newmeas.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_counter_activity.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddCounterActivity : AppCompatActivity() {

    private lateinit var viewModel: MeasuresVM
    private var valuesListFloat: ArrayList<Float> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_counter_activity)

        initVM()

        /*
        ListView
         */

        val listView = findViewById<ListView>(R.id.measValues)
        val adapter = ArrayAdapter<Float> (this, android.R.layout.simple_list_item_1, valuesListFloat)
        listView.adapter = adapter

        //region =>Intent from MainActivity
        val arg: Bundle? = intent.extras
        val name = arg.let {
            it?.get("name").toString()
        }
        //endregion

        //region =>Opening existing counter
        if (name != "null"){

            /*
            Ниже - подключение к базе, поиск
             */
            val meas = viewModel.findByName(name)
            meas.let {
                measName.setText(it?.name)

               /* if (!meas?.valuesList?.isEmpty()!!){
                    Toast.makeText(applicationContext, "Not empty", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(applicationContext, "SO EmpTY!", Toast.LENGTH_SHORT).show()
                }
*/
                valuesListFloat.clear()
                valuesListFloat.addAll(meas?.valuesList!!)


               adapter.setNotifyOnChange(true)

            }

        }
        //endregion

        /*
        todo сделать сохранение в базе изменений (если имя такое уже есть, то сохранить,
        спросив разрешение на замену. Если новое, тогда посмотреть, есть ли такое имя в базе?
        Если нет, то сохраняем с новым именем.
        Закрыть в данной активности рилм.

         */
        }


    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initVM() {
        viewModel = ViewModelProviders.of(this).get(MeasuresVM::class.java)
        viewModel.data.observe(this, Observer { mutableList ->


        })
    }
}

