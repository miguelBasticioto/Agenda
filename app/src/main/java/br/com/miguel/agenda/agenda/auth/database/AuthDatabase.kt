package br.com.miguel.agenda.agenda.auth.database

import br.com.miguel.agenda.agenda.auth.module.Usuario
import io.realm.Realm

object AuthDatabase {

    fun salvarUsuario(usuario: Usuario, onSuccess: () -> Unit, onFailure:() -> Unit) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(usuario)
            realm.commitTransaction()
            onSuccess()
        }
    }

    fun buscarUsuario(id: Int, onSuccess: (usuario: Usuario) -> Unit){
        Realm.getDefaultInstance().use { realm ->
            val usuario = realm.where(Usuario::class.java).equalTo("id", id).findFirst()
            onSuccess(usuario!!)

        }
    }
}