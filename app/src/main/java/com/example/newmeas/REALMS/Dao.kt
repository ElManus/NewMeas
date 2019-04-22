package com.example.newmeas.REALMS

import androidx.lifecycle.LiveData
import com.example.newmeas.Models.Measures
import com.example.newmeas.Utils.EventRealmCallback

interface Dao {

    fun insert(name: String):Boolean

    fun delete(name: String, callback: EventRealmCallback)

    fun deleteAll()

    fun findByName(nameFind: String): Measures?

    fun getAllMeasuresByLiveData(): LiveData<MutableList<Measures>>

    fun getAll(): MutableList<Measures>

}