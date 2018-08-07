package br.com.miguel.agenda.agenda.contato.database

import android.util.Log
import br.com.miguel.agenda.agenda.contato.model.Contato
import io.realm.Realm

object ContatosDatabase {
    fun salvarContatos(contatos: List<Contato>) {
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contatos)
            Log.d("tag", "contatos salvos no banco")
            realm.commitTransaction()
        }
    }

    fun buscarContato(contatoId: Int, onSuccess: (contato: Contato) -> Unit) {
        Realm.getDefaultInstance().use { realm ->
            val contato = realm.where(Contato::class.java).equalTo("id", contatoId).findFirst()
            onSuccess(contato!!)
        }

    }

    fun criarContato(contato: Contato, onSuccess: () -> Unit) {
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contato)
            onSuccess()
            realm.commitTransaction()
        }
    }

    fun deletarContato(contatoId: Int, onSuccess: () -> Unit) {
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            val contato = realm.where(Contato::class.java).equalTo("id", contatoId).findFirst()
            contato!!.deleteFromRealm()
            onSuccess()
            realm.commitTransaction()
        }
    }

    fun buscarContatos(): List<Contato> {
        Realm.getDefaultInstance().use { realm ->
            return  realm.where(Contato::class.java).findAll()
        }
    }

    fun editarContato(contato: Contato, onSuccess: () -> Unit) {
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contato)
            onSuccess()
            realm.commitTransaction()
        }
    }

    fun limparContatos(onSuccess: () -> Unit){
        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.delete(Contato::class.java)
            onSuccess()
            realm.commitTransaction()
        }
    }
}