package br.com.miguel.agenda.agenda.auth.business

import android.util.Log
import br.com.miguel.agenda.agenda.auth.model.Usuario
import br.com.miguel.agenda.agenda.auth.network.AuthNetwork
import br.com.miguel.agenda.agenda.auth.database.AuthDatabase

object AuthBusiness {

    fun criarUsuario(email: String, senha: String, onSuccess: (usuario: Usuario) -> Unit, onFailure: (mensagem:Int) -> Unit) {

        val user = Usuario()
        user.email = email
        user.password = senha
        user.passwordConfirmation = senha

        AuthNetwork.criarUsuario(user, { usuario ->

            onSuccess(usuario)

        }, onFailure)

    }

    fun logarUsuario(email: String, senha: String, onSuccess: (usuario: Usuario) -> Unit, onFailure: (mensagem: Int) -> Unit) {

        val user = Usuario()
        user.email = email
        user.password = senha

        AuthNetwork.logarUsuario(user, { usuario ->

            AuthDatabase.salvarUsuario(usuario, {


            }, {

            })

            onSuccess(usuario)

        }, onFailure)

    }

    fun buscarUsuario(onSuccess: (usuario: Usuario) -> Unit) {
        AuthDatabase.buscarUsuario { usuario ->

            onSuccess(usuario)

        }
    }

    fun logout( onSuccess: () -> Unit, onFailure: () -> Unit) {

        AuthBusiness.buscarUsuario { usuario ->
            AuthNetwork.logout(usuario.uid!!, usuario.accessToken!!, usuario.client!!, {

                //Apagar do banco
                AuthDatabase.limparBanco {

                    onSuccess()

                }
            }, {

                onFailure()

            })
        }

    }


    fun isLogado(): Boolean = AuthDatabase.contagemUsuario() > 0

}