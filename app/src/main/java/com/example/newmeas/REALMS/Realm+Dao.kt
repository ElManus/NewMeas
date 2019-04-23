package com.example.newmeas.REALMS

import io.realm.Realm
import javax.inject.Inject


/*
Дополняем тип Realm новой функцией, которая возвращает объект Repository.
Тем самым мы делаем проброс в viewModel: обращаясь напрямую к базе Realm, мы можем вызвать этот метод
и через него обратиться к репозиторию, а из него - выполнить методы работы с базой.
 */
fun Realm.measureModel(): Repository = Repository(this)