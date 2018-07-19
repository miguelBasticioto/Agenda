package br.com.miguel.agenda.agenda.auth.business

import android.util.Log
import br.com.miguel.agenda.agenda.auth.module.Usuario
import br.com.miguel.agenda.agenda.auth.network.AuthNetwork
import br.com.miguel.agenda.agenda.database.AuthDatabase

object AuthBusiness {
    fun criarUsuario(email: String, senha: String, onSuccess:(usuario: Usuario) -> Unit, onFailure:() -> Unit){
        val user = Usuario()
        user.email = email
        user.password = senha
        user.passwordConfirmation = senha
        AuthNetwork.criarUsuario(user , {
            //Gravar no banco
            AuthDatabase.salvarUsuario(it, {
                Log.d("tag", "sucesso")
            }, {

            })

            onSuccess(it)
        }, {
            onFailure()
        })
    }

    fun logarUsuario(email: String, senha: String, onSuccess:(usuario: Usuario) -> Unit, onFailure:() -> Unit) {

        val user = Usuario()
        user.email = email
        user.password = senha

        AuthNetwork.logarUsuario(user , {

            onSuccess(it)
        }, {
            onFailure()
        })
    }
}