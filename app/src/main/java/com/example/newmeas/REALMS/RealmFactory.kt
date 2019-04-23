package com.example.newmeas.REALMS

import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import javax.inject.Inject

class RealmFactory @Inject constructor() {

    /*
    Repository. Работа с инстансами базы Realm
     */
    fun setRealmConfiguration (): RealmConfiguration {
        val realmConfiguration = RealmConfiguration.Builder()
            .name("MeasuresDatabase")
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)
        return realmConfiguration
    }

}