package br.com.miguel.agenda.agenda.contato.network

import br.com.miguel.agenda.agenda.auth.model.Usuario
import br.com.miguel.agenda.agenda.contato.model.Contato
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ContatosNetwork {

    private val contatosApi by lazy {
        ContatosNetwork.getRetrofit().create(ContatosAPI::class.java)
    }

    const val contacts = "contacts"
    const val contactsId = "contacts/{id}"

    const val uid = "uid"
    const val accessToken = "access-token"
    const val client = "client"
    const val contentType = "Content-Type"

    const val id = "id"

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

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
                .subscribe({contato ->
                    contato.let {
                        onSuccess()
                    }
                }, {
                    onFailure()
                })

    }

    fun deletarContato(uid: String, accessToken: String, client: String, id: Int, onSuccess: () -> Unit, onFailure: () -> Unit) {

        contatosApi.deletarContato(uid, accessToken, client, "application/json", id.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.let {
                        onSuccess()
                    }

                }, {
                    onFailure()
                })

    }

}