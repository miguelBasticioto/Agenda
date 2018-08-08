package br.com.miguel.agenda.agenda.auth.network

import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.model.Usuario
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AuthNetwork {

    const val auth = "auth"
    const val signIn = "auth/sign_in"
    const val signOut = "auth/sign_out"

    const val uid = "uid"
    const val accessToken = "access-token"
    const val client = "client"

    val loginAPI by lazy {
        getRetrofit().create(AuthAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://api-agenda-unifor.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

    }

    fun criarUsuario(user: Usuario, onSuccess: (usuario: Usuario) -> Unit, onFailure: (mensagem: Int) -> Unit) {

        loginAPI.criarUsuario(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ usuario ->

                    usuario.data.let { usuario ->

                        usuario?.run { onSuccess(usuario) }

                    }

                }, { erro ->
                    if(erro.message.toString().contains("422")){
                        onFailure(R.string.usuarioCriadoFracasso)
                        return@subscribe
                    }

                    onFailure(R.string.semConexao)

                })

    }

    fun logarUsuario(user: Usuario, onSuccess: (usuario: Usuario) -> Unit, onFailure: (mensagem: Int) -> Unit) {

        loginAPI.logarUsuario(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    response.headers().get("uid")

                    if(!response.isSuccessful){
                        onFailure(R.string.logadoFracasso)
                        return@subscribe
                    }

                    response.body()?.data.let { usuario ->
                        usuario?.uid = response.headers().get("uid")
                        usuario?.accessToken = response.headers().get("access-token")
                        usuario?.client = response.headers().get("client")

                        onSuccess(usuario!!)
                    }

                }, {
                    onFailure(R.string.semConexao)
                })

    }

    fun logout(uid: String, accessToken: String, client: String, onSuccess: () -> Unit, onFailure: () -> Unit) {

        loginAPI.logout(uid, accessToken, client)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ resposta ->
                    onSuccess()
                }, {
                    onFailure()
                })

    }

}