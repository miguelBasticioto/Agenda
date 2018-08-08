package br.com.miguel.agenda.agenda.module.contato.network

import br.com.miguel.agenda.agenda.core.BaseNetwork
import br.com.miguel.agenda.agenda.module.auth.model.Usuario
import br.com.miguel.agenda.agenda.module.contato.model.Contato
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ContatosNetwork: BaseNetwork(){

    const val contacts = "contacts"
    const val contactsId = "contacts/{id}"

    const val uid = "uid"
    const val accessToken = "access-token"
    const val client = "client"
    const val contentType = "Content-Type"

    const val id = "id"

    val contatosApi by lazy {
        getRetrofit().create(ContatosAPI::class.java)
    }

    fun buscarContatos(uid: String, accessToken: String, client: String, onSuccess: (List<Contato>) -> Unit, onFailure: () -> Unit) {

        contatosApi.buscarContatos(uid, accessToken, client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contatos ->
                    contatos.let {contatos ->
                        onSuccess(contatos)
                    }
                }, {
                    onFailure()
                })

    }

    fun criarContato(user: Usuario, contato: Contato, onSuccess: (contato: Contato) -> Unit, onFailure: () -> Unit) {

        contatosApi.criarContato(user.uid!!, user.accessToken!!, user.client!!, "application/json", contato)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({contato ->
                    onSuccess(contato)
                }, {
                    onFailure()
                })

    }

    fun editarContato(uid: String, accessToken: String, client: String, contato: Contato, id: String, onSuccess: () -> Unit, onFailure: () -> Unit) {

        contatosApi.editarContato(uid, accessToken, client, "application/json", contato, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess()
                }, {
                    onFailure()
                })

    }

    fun deletarContato(uid: String, accessToken: String, client: String, id: Int, onSuccess: () -> Unit, onFailure: () -> Unit) {

        contatosApi.deletarContato(uid, accessToken, client, "application/json", id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess()

                }, {
                    onFailure()
                })

    }

}