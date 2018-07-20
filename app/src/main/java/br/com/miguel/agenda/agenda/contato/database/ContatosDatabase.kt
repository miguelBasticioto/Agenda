package br.com.miguel.agenda.agenda.contato.database

import android.util.Log
import br.com.miguel.agenda.agenda.contato.module.Contato
import io.realm.Realm

object ContatosDatabase {
    fun salvarContatos (contatos: List<Contato>){
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contatos)
            Log.d("tag", "contatos salvos no banco")
            realm.commitTransaction()
        }
    }
}