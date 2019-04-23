package com.example.newmeas.REALMS

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newmeas.Utils.EventRealmCallback
import com.example.newmeas.Data.Measures
import io.realm.Realm
import javax.inject.Inject

class Repository (val realm: Realm): Dao {


    //Переменная содержит livedata - изменяемый список объектов Measures
    private var livedataList: MutableLiveData<MutableList<Measures>> = MutableLiveData()

    /*
    Описываем  все методы работы с базой данных
     */
    override fun insert (name: String): Boolean{
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

    /*
    сначала должно произойти удаление, а только после него - выгрузка данных для обновления recyclerview
     */
    override fun delete (name: String, callback: EventRealmCallback) {
        if (realm.where(Measures::class.java).equalTo("name", name).findFirst() != null) {
            realm.executeTransactionAsync(Realm.Transaction {
                val result = it.where(Measures::class.java).equalTo("name", name).findAll()
                result?.deleteAllFromRealm()
                }, Realm.Transaction.OnSuccess {
                callback.onComplete()
            }
            )}
        }



    override fun deleteAll () {
        realm.executeTransactionAsync {
            val result = it.where(Measures::class.java).findAll()
            result.deleteAllFromRealm()
        }
    }

    override fun findByName(nameFind: String): Measures? {
        val res = realm
            .where(Measures::class.java)
            .equalTo("name", nameFind)
            .findFirstAsync()

        realm.beginTransaction()
        val res2 = realm.copyFromRealm(res)
        realm.commitTransaction()

        return res2


    }

    override fun getAllMeasuresByLiveData(): LiveData<MutableList<Measures>> {
        //Получаем результат асинхронного поиска всех объектов в базе.
        val result = realm
            .where(Measures::class.java)
            .findAllAsync()
        //в livedata-данные помещаем копию полученных результатов поиска
        livedataList.postValue(realm.copyFromRealm(result))
        //возвращаем значение типа LiveData<MutableList<Measures>>
        return livedataList
    }


    override fun getAll(): MutableList<Measures>{
        return realm
            .where(Measures::class.java)
            .findAllAsync()
    }

}

