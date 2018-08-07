package br.com.miguel.agenda.agenda.auth.business

import android.util.Log
import br.com.miguel.agenda.agenda.auth.model.Usuario
import br.com.miguel.agenda.agenda.auth.network.AuthNetwork
import br.com.miguel.agenda.agenda.auth.database.AuthDatabase

object AuthBusiness {
    fun criarUsuario(email: String, senha: String, onSuccess: (usuario: Usuario) -> Unit, onFailure: () -> Unit, semConexao: () -> Unit) {
        val user = Usuario()
        user.email = email
        user.password = senha
        user.passwordConfirmation = senha
        AuthNetwork.criarUsuario(user, { usuario ->

            onSuccess(usuario)

        }, {

            onFailure()

        }, {

            semConexao()

        })
    }

    fun logarUsuario(email: String, senha: String, onSuccess: (usuario: Usuario) -> Unit, onFailure: () -> Unit, semConexao: () -> Unit) {

        val user = Usuario()
        user.email = email
        user.password = senha

        AuthNetwork.logarUsuario(user, { usuario ->

            AuthDatabase.salvarUsuario(usuario, {

                Log.d("tag", "Usuario salvo com sucesso")

            }, {

            })

            onSuccess(usuario)

        }, {

            onFailure()

        }, {

            semConexao()

        })
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

    fun buscarUsuarioDatabase():Usuario  = AuthDatabase.recuperarUsuario()

    fun checarUsuario(): Usuario? {

        return AuthDatabase.checarUsuario()

    }

    fun validarEmail(email: String): Boolean = email.isNotEmpty()

    fun validarSenha(password: String): Boolean = password.isNotEmpty()

    fun isLogado(): Boolean = AuthDatabase.contagemUsuario() > 0

}