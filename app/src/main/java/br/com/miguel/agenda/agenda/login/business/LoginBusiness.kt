package br.com.miguel.agenda.agenda.login.business

import br.com.miguel.agenda.agenda.login.module.Usuario
import br.com.miguel.agenda.agenda.login.network.LoginNetwork

object LoginBusiness {
    fun criarUsuario(email: String, senha: String, onSuccess:() -> Unit, onFailure:() -> Unit){
        val user = Usuario()
        user.email = email
        user.password = senha
        user.passwordConfirmation = senha
        LoginNetwork.criarUsuario(user , {

        }, {
            onFailure()
        })
    }
}