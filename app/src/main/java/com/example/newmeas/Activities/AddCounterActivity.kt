package com.example.newmeas.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newmeas.Data.MeasuresVM
import com.example.newmeas.R
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

                valuesListFloat.clear()
                valuesListFloat.addAll(meas?.valuesList!!)

                adapter.notifyDataSetChanged()

                //todo сохранилось в локальный список. В RealmList должно сохраниться потом.
                //todo продумать интерфейс. Что еще должно быть?
            }

        }
        //endregion

        /*
        todo сделать сохранение в базе изменений (если имя такое уже есть, то сохранить,
        спросив разрешение на замену. Если новое, тогда посмотреть, есть ли такое имя в базе?
        Если нет, то сохраняем с новым именем.
        Закрыть в данной активности рилм.

         */



        /*
        ADD
         */
        addValue.setOnClickListener {
            if(!EditTextMeasNewValue.text!!.isEmpty())
            {
                valuesListFloat.add(0, EditTextMeasNewValue.text.toString().toFloatOrNull()!!)
                adapter.notifyDataSetChanged()
            }
        }
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

