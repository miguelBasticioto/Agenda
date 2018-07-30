package br.com.miguel.agenda.agenda.contato.network

import android.util.Log
import br.com.miguel.agenda.agenda.auth.module.Usuario
import br.com.miguel.agenda.agenda.contato.module.Contato
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ContatosNetwork {
    val contatosApi by lazy {
        ContatosNetwork.getRetrofit().create(ContatosAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    fun buscarContatos(uid: String, accessToken: String, client: String, onSuccess:(List<Contato>) -> Unit){
        contatosApi.buscarContatos(uid, accessToken, client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contatos ->
                    contatos?.let {
                        onSuccess(it)
                    }
                })
    }

    fun criarContato(user: Usuario, contato:Contato, onSuccess:() -> Unit){
        contatosApi.criarContato(user.uid!!, user.accessToken!!, user.client!!, "application/json", contato)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let {
                        onSuccess()
                    }
                })
    }

    fun editarContato(uid: String, accessToken: String, client: String, contato: Contato, id: String ,onSuccess:() -> Unit) {
        contatosApi.editarContato(uid, accessToken, client, "application/json", contato, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let {
                        Log.d("tag", "Editado com sucesso")
                        onSuccess()
                    }
                })
    }

    fun deletarContato(uid: String, accessToken: String, client: String, id: String ,onSuccess: () -> Unit){
        contatosApi.deletarContato(uid, accessToken, client, "application/json", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response?.let {
                        Log.d("tag", "Deletado com sucesso")
                        Log.d("tag", response.message())
                        onSuccess()
                    }

                } ,{

                })
    }
}