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
        AuthNetwork.criarUsuario(user, {

            onSuccess(it)

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

        AuthNetwork.logarUsuario(user, {

            AuthDatabase.salvarUsuario(it, {

                Log.d("tag", "Usuario salvo com sucesso")

            }, {

            })

            onSuccess(it)

        }, {

            onFailure()

        }, {

            semConexao()

        })
    }

    fun buscarUsuario(id: Int, onSuccess: (usuario: Usuario) -> Unit) {
        AuthDatabase.buscarUsuario(id) {

            onSuccess(it)

        }
    }

    fun logout(uid: String, accessToken: String, client: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        AuthNetwork.logout(uid, accessToken, client, {

            //Apagar do banco
            AuthDatabase.limparBanco {

                onSuccess()

            }
        }, {

            onFailure()

        })
    }

    fun checarUsuario(): Usuario? {

        return AuthDatabase.checarUsuario()

    }

    fun limparContatos () {
        AuthDatabase.limparContatos {

        }
    }
}