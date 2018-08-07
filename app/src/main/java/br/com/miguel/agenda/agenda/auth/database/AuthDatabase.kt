package br.com.miguel.agenda.agenda.auth.database

import br.com.miguel.agenda.agenda.auth.model.Usuario
import br.com.miguel.agenda.agenda.contato.model.Contato
import io.realm.Realm

object AuthDatabase {

    fun salvarUsuario(usuario: Usuario, onSuccess: () -> Unit, onFailure: () -> Unit) {

        Realm.getDefaultInstance().use { realm ->

            realm.beginTransaction()
            realm.copyToRealmOrUpdate(usuario)
            realm.commitTransaction()
            onSuccess()

        }
    }

    fun buscarUsuario(onSuccess: (usuario: Usuario) -> Unit) {

        Realm.getDefaultInstance().use { realm ->

            val usuario = realm.where(Usuario::class.java).findFirst()
            onSuccess(usuario!!)

        }

    }

    fun limparBanco(onSuccess: () -> Unit) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.deleteAll()
            onSuccess()
            realm.commitTransaction()
        }

    }

    fun contagemUsuario(): Long {

        Realm.getDefaultInstance().use { realm ->
            return realm.where(Usuario::class.java).count()
        }
    }

}