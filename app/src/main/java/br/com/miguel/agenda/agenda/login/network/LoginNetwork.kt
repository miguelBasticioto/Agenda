package br.com.miguel.agenda.agenda.login.network

import android.util.Log
import br.com.miguel.agenda.agenda.login.module.Usuario
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object LoginNetwork {
    val loginAPI by lazy {
        getRetrofit().create(LoginAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    fun criarUsuario(user: Usuario, onSuccess: (usuario: Usuario) -> Unit, onFailure: () -> Unit){
        loginAPI.criarUsuario(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({usuario ->

                    usuario?.let{

                        Log.d("tag", "Email: ${usuario.email}")
                        onSuccess(it)

                        }

                    } ,{

                    onFailure()

                })
    }

    fun logarUsuario(user: Usuario, onSuccess:() -> Unit, onFailure:() -> Unit){
        loginAPI.logarUsuario(user).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({usuario ->
                    usuario?.let{
                    Log.d("tag", "Email: ${it.email}")
                 }
                }, {
                    onFailure()
                })
    }
}