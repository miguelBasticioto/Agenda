package br.com.miguel.agenda.agenda.auth.module

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Usuario : RealmObject(){
    var email: String? = null
    var password: String? = null

    @SerializedName("password_confirmation")
    var passwordConfirmation: String? = null

    var uid: String? = null
    var client: String? = null
    var acessToken: String? = null
}