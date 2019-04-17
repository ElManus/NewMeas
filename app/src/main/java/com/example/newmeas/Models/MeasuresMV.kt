package com.example.newmeas.Models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.newmeas.REALMS.measureModel
import io.realm.Realm

class MeasuresVM(application: Application): AndroidViewModel(application) {

    //переменная базы
    private val realmDB: Realm = Realm.getDefaultInstance()

    //переменная, которая получает livedata-данные
    val data: LiveData<MutableList<Measures>>

    init {
        //при инициализации VM (в том числе и после смены конфигурации), получаем "живые данные" от Dao
        data = realmDB.measureModel().getAllMeasuresByLiveData()
    }

    fun findByName(name: String): Measures? {
        return realmDB.measureModel().findByName(name)
    }

    fun insert(name: String): Boolean {
        return (realmDB.measureModel().insert(name))
    }

    fun delete(name: String): Boolean
    {
        return realmDB.measureModel().delete(name)
    }

    fun getAll(): MutableList<Measures>? {
        var tt =  realmDB.measureModel().getAll()

        Log.i("LOG", "----------- getAll() -----------")
        for (tf in tt){
            Log.i("LOG", "                ${tf.name}")
        }
            return tt
    }


}