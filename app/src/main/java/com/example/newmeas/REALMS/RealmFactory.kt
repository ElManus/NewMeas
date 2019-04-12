package com.example.newmeas.REALMS

import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File

class RealmFactory {

    /*
    Repository. Работа с инстансами базы Realm
     */
    fun setRealmConfiguration (dbName: String): RealmConfiguration {
        val realmConfiguration = RealmConfiguration.Builder()
            .name(dbName)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(realmConfiguration)
        return realmConfiguration
    }

    fun isRealmFileExists(dbName: String): Boolean{
        val realmConfiguration = RealmConfiguration.Builder()
            .name(dbName)
            .deleteRealmIfMigrationNeeded()
            .build()

        val realmFile = File(realmConfiguration.path)
        return realmFile.exists()
    }
}