package com.example.newmeas.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_counter_activity)

        initVM()

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
                measCurrValue.setText(it?.currentValue.toString())
                Toast.makeText(applicationContext,"name = ${it?.name},value =  ${it?.currentValue}", Toast.LENGTH_SHORT).show()
            }

        }
        //endregion

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

