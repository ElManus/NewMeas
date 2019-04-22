package com.example.newmeas.REALMS

import io.realm.Realm

/*
Вот что должно быть в модели:
val level = realmDB.measureDao().insert("sssf")
Таким образом, мы в Realm+Dao делаем прослойку между данными, которыми оперируют Dao и VM.
(Extention function) Функция расширения. В данном случае означает, что мы Realm (как объект
библиотеки) расширяем дополнительной функцией. Эта ф-ия типа Repository, и тело её = Repository.
Тем самым, мы можем теперь из VM исполнять методы Dao.
 */
fun Realm.measureModel(): Repository = Repository(this)