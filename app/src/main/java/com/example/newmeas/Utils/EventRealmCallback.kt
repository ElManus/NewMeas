package com.example.newmeas.Utils

/*
исп-ся для того, чтобы можно было взять данные с реалма только после того, как асинхронно отработается
 */
interface EventRealmCallback {
    fun onComplete()
}
