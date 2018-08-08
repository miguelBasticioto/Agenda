package br.com.miguel.agenda.agenda.module.contato.database

import br.com.miguel.agenda.agenda.module.contato.model.Contato
import io.realm.Realm

object ContatosDatabase {

    fun salvarContatos(contatos: List<Contato>) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contatos)
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

    fun buscarContatos(): List<Contato> = Realm.getDefaultInstance().where(Contato::class.java).findAll()

    fun editarContato(contato: Contato, onSuccess: () -> Unit) {

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(contato)
            onSuccess()
            realm.commitTransaction()
        }

    }

    fun limparContatos(){

        Realm.getDefaultInstance().use { realm ->
            realm.beginTransaction()
            realm.delete(Contato::class.java)
            realm.commitTransaction()
        }
    }

}