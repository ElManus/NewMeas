package com.example.newmeas.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newmeas.Data.MeasuresVM
import com.example.newmeas.R

import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.add_counter_activity.*


//@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddCounterActivity : AppCompatActivity() {

    private lateinit var viewModel: MeasuresVM
    private var valuesListFloat: ArrayList<Float> = arrayListOf()

    private var nameMes: String = ""

    private var newMeasFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_counter_activity)

        initVM()

        /*
        ListView
         */

        val listView = findViewById<ListView>(R.id.measValues)
        val adapter = ArrayAdapter<Float>(this, android.R.layout.simple_list_item_1, valuesListFloat)
        listView.adapter = adapter

        //region =>Intent from MainActivity
        val arg: Bundle? = intent.extras
        val name = arg.let {
            it?.get("name").toString()
        }
        //endregion
        Log.i("LOG", "first name is = $name")

        if (name != "null") {

            Log.i("LOG", "name in field = $name")

            nameMes.plus(name)

            Log.i("LOG", "nameMes in field = $nameMes")

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

        } else
        /*
        Пустое поле?
         */ {
            //проверка, есть ли такое имя в базе
            //поле с именем не пустое?
            if (!measName.text!!.isEmpty()) {

                val meas = viewModel.findByName(measName.text.toString())
                if (meas == null) {
                    //нет объекта? готовы сохранить по нажатию кнопки "save"
                    newMeasFlag = true
                }
            }
            else{
                Toast.makeText(applicationContext, "measName is empty!", Toast.LENGTH_LONG).show()
            }
        }

        /*
        ADD
         */
        addValue.setOnClickListener {
            if (!EditTextMeasNewValue.text!!.isEmpty()) {
                valuesListFloat.add(0, EditTextMeasNewValue.text.toString().toFloatOrNull()!!)
                adapter.notifyDataSetChanged()
            }
        }

        //SAVE
        /*
        todo добавить. Если имя есть? Спрашиваем замену! тогда replace.
        Если совпадений в базе нет - тогда insert
         */
        saveMeas.setOnClickListener {

            hideSoftKeyboard()

            if (newMeasFlag) {
                newMeasFlag = false
                viewModel.insert(measName.text.toString(), RealmList(0.0f, 0.1f, 0.2f))
                Toast.makeText(applicationContext, "SAVED", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        /* if (nameMes != "") {
             replaceData(nameMes, valuesListFloat)
         }
         */

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        Realm.getDefaultInstance().close()
    }

    private fun initVM() {
        viewModel = ViewModelProviders.of(this).get(MeasuresVM::class.java)
        viewModel.data.observe(this, Observer { mutableList ->


        })
    }

    private fun replaceData(name: String, valueListArray: ArrayList<Float>) {

        Log.i("LOG", "=========== in replaceData START===========")

        val listRealm: RealmList<Float> = RealmList()

        for (tt in valueListArray) {
            listRealm.add(tt)
        }

        viewModel.replace(name, listRealm)
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(measName.windowToken, 0)
    }


}

