package br.com.miguel.agenda.agenda.core

import br.com.miguel.agenda.agenda.module.auth.network.AuthAPI
import br.com.miguel.agenda.agenda.module.contato.network.ContatosAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


open class CoreNetwork {

    val authApi by lazy {
        getRetrofit().create(AuthAPI::class.java)
    }

    val contatosApi by lazy {
        getRetrofit().create(ContatosAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

    }

}