package br.com.miguel.agenda.agenda.auth.network

import br.com.miguel.agenda.agenda.auth.module.Usuario
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AuthNetwork {
    val loginAPI by lazy {
        getRetrofit().create(AuthAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    fun criarUsuario(user: Usuario, onSuccess: (usuario: Usuario) -> Unit, onFailure: () -> Unit) {
        loginAPI.criarUsuario(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ usuario ->

                    usuario?.data?.let {

                        onSuccess(it)

                    }

                }, {

                    onFailure()

                })
    }

    fun logarUsuario(user: Usuario, onSuccess: (usuario: Usuario) -> Unit, onFailure: () -> Unit) {

        loginAPI.logarUsuario(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    response.headers().get("uid")

                    if(response.code() !== 200) {
                        onFailure()
                    }

                    response.body()?.data?.let {usuario ->
                        usuario.uid = response.headers().get("uid")
                        usuario.accessToken = response.headers().get("access-token")
                        usuario.client = response.headers().get("client")

                        onSuccess(usuario)
                    }

                }, {
                    onFailure()
                })

    }
}