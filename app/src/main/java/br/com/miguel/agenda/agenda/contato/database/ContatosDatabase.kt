package br.com.miguel.agenda.agenda.contato.database

import android.util.Log
import br.com.miguel.agenda.agenda.auth.module.Usuario
import io.realm.Realm

object ContatosDatabase {
    fun buscarUsuario(id: Int){
        Realm.getDefaultInstance().use { realm ->
            val usuario = realm.where(Usuario::class.java).equalTo("id", id).findFirst()
            Log.d("tag", usuario!!.acessToken.toString())
            Log.d("tag", usuario.client.toString())
            Log.d("tag", usuario.uid.toString())

        }
    }
}