package br.com.miguel.agenda.agenda.login.module

import io.realm.RealmObject

open class Usuario : RealmObject(){
    var email: String? = null
    var password: String? = null
    var password_confirmation: String? = null

    var uid: String? = null
    var client: String? = null
    var acessToken: String? = null
}