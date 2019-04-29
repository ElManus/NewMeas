package com.example.newmeas.REALMS

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newmeas.Utils.EventRealmCallback
import com.example.newmeas.Data.Measures
import io.realm.Realm
import io.realm.RealmList

class Repository(val realm: Realm) : Dao {


    //Переменная содержит livedata - изменяемый список объектов Measures
    private var livedataList: MutableLiveData<MutableList<Measures>> = MutableLiveData()

    /*
    Описываем  все методы работы с базой данных
     */
    override fun insert(name: String, valuesListAdd: RealmList<Float>): Boolean {
        //Проверка. Если такого имени нет, тогда асинхронно вносим новые данные.
        return if (realm.where(Measures::class.java).equalTo("name", name).findFirst() == null) {

            realm.executeTransactionAsync {
                val item = Measures()
                item.name = name

                item.run {
                    valuesList.clear()
                    valuesList.addAll(valuesListAdd)
                }

                it.insert(item)
            }
            true
        } else {
            false
        }
    }

    /*
    сначала должно произойти удаление, а только после него - выгрузка данных для обновления recyclerview
     */
    override fun delete(name: String, callback: EventRealmCallback) {
        if (realm.where(Measures::class.java).equalTo("name", name).findFirst() != null) {
            realm.executeTransactionAsync(Realm.Transaction {
                val result = it.where(Measures::class.java).equalTo("name", name).findAll()
                result?.deleteAllFromRealm()
            }, Realm.Transaction.OnSuccess {
                callback.onComplete()
            }
            )
        }
    }


    override fun replace(newName: String, newValuesList: RealmList<Float>) {
        Log.i("LOG", "=========== in replace method START===========")

        val res = realm.where(Measures::class.java).equalTo("name", newName).findFirst()

        if (res != null) {
            //обновляем найденный объект
            val item = Measures()
            item.name = newName

            item.run {
                this.valuesList.clear()
                this.valuesList.addAll(newValuesList)
            }
            realm.insertOrUpdate(item)

            Log.i("LOG", "=========== in replace method ===========")
            Log.i("LOG", "Found name is ${item.name} for Update")

        } else {
            //создаем новый объект

            realm.executeTransactionAsync { it1 ->
                val item = Measures()
                item.name = newName

                item.run {
                    this.valuesList.clear()
                    this.valuesList.addAll(newValuesList)
                }
                it1.insert(item)

                Log.i("LOG", "=========== in replace method ===========")
                Log.i("LOG", "New name is ${item.name} for Insert")

            }
        }


    }

    override fun deleteAll() {
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

    override fun getAll(): MutableList<Measures> {
        return realm
            .where(Measures::class.java)
            .findAllAsync()
    }
}

