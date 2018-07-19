package br.com.miguel.agenda.agenda.auth.business

import br.com.miguel.agenda.agenda.auth.module.Usuario
import br.com.miguel.agenda.agenda.auth.network.AuthNetwork

object AuthBusiness {
    fun criarUsuario(email: String, senha: String, onSuccess:() -> Unit, onFailure:() -> Unit){
        val user = Usuario()
        user.email = email
        user.password = senha
        user.passwordConfirmation = senha
        AuthNetwork.criarUsuario(user , {

        }, {
            onFailure()
        })
    }

    fun logarUsuario(email: String, senha: String, onSuccess:() -> Unit, onFailure:() -> Unit) {
        val user = Usuario()
        user.email = email
        user.password = senha
        AuthNetwork.logarUsuario(user , {

        }, {
            onFailure()
        })
    }
}