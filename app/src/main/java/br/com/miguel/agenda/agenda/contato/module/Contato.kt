package br.com.miguel.agenda.agenda.contato.module

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Contato: RealmObject() {
    @PrimaryKey
    var id: Int = 0

    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var picture: String? = null
    var birth: Long? = 0

}