package com.example.newmeas.Models


import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class Measures: RealmModel{

    @PrimaryKey
    @Required
    var name: String = ""
    var currentValue: Float = 0.0f

}




/*open class Measures(@PrimaryKey open var name: String,
                          var currentValue: Float): RealmObject() {
    constructor() : this("", 0.0f)
 }*/