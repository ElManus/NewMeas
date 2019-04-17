package com.example.newmeas.REALMS

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newmeas.Models.Measures
import io.realm.Realm

class MeasureDao(val realm: Realm) {

    //Переменная содержит livedata - изменяемый список объектов Measures
    private var livedataList: MutableLiveData<MutableList<Measures>> = MutableLiveData()

    /*
    Описываем  все методы работы с базой данных
     */
    fun insert (name: String): Boolean{
        //Проверка. Если такого имени нет, тогда асинхронно вносим новые данные.
        return if(realm.where(Measures::class.java).equalTo("name",name).findFirst() == null) {

            realm.executeTransactionAsync {
                val item = Measures()
                item.name = name
                it.insert(item)
            }
            true
        } else{
            false
        }
    }

    fun delete (name: String): Boolean{
        return if(realm.where(Measures::class.java).equalTo("name", name).findFirst() != null){
            realm.executeTransaction{
                val result = it.where(Measures::class.java).equalTo("name", name).findAll()
                result?.deleteAllFromRealm()
            }
            true
        } else {
            false
        }

        /*
        todo сделать тут слушателя на onSuccess, дабы не отказываться от асинхронности!
         */

    }

    fun deleteAll () {
        realm.executeTransactionAsync {
            val result = it.where(Measures::class.java).findAll()
            result.deleteAllFromRealm()
        }
    }

    fun findByName(nameFind: String): Measures? {
        val res = realm
            .where(Measures::class.java)
            .equalTo("name", nameFind)
            .findFirstAsync()

        realm.beginTransaction()
        val res2 = realm.copyFromRealm(res)
        realm.commitTransaction()

        return res2


    }

    fun getAllMeasuresByLiveData(): LiveData<MutableList<Measures>> {
        //Получаем результат асинхронного поиска всех объектов в базе.
        val result = realm
            .where(Measures::class.java)
            .findAllAsync()
        //в livedata-данные помещаем копию полученных результатов поиска
        livedataList.postValue(realm.copyFromRealm(result))
        //возвращаем значение типа LiveData<MutableList<Measures>>
        return livedataList
    }


    fun getAll(): MutableList<Measures>{
        return realm
            .where(Measures::class.java)
            .findAllAsync()
    }

}