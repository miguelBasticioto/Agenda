package br.com.miguel.agenda.agenda.module.auth.network

import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.core.CoreNetwork
import br.com.miguel.agenda.agenda.module.auth.model.Usuario
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object AuthNetwork: CoreNetwork() {

    const val auth = "auth"
    const val signIn = "auth/sign_in"
    const val signOut = "auth/sign_out"

    const val uid = "uid"
    const val accessToken = "access-token"
    const val client = "client"


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
                .subscribe({
                    onSuccess()
                }, {
                    onFailure()
                })

    }

}