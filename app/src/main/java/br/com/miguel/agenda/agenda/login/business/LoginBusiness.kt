package br.com.miguel.agenda.agenda.login.business

import android.util.Log
import br.com.miguel.agenda.agenda.login.module.Usuario
import br.com.miguel.agenda.agenda.login.network.LoginNetwork

object LoginBusiness {
    fun criarUsuario(email: String, senha: String){
        val user = Usuario()
        user.email = email
        user.password = senha
        user.password_confirmation = senha
        LoginNetwork.criarUsuario(user , {
            Log.d("tag", it.email)
        }, {
            //falha
        })
    }
}