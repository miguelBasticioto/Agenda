package br.com.miguel.agenda.agenda.contato.business

import android.util.Log
import br.com.miguel.agenda.agenda.auth.database.AuthDatabase
import br.com.miguel.agenda.agenda.contato.database.ContatosDatabase
import br.com.miguel.agenda.agenda.contato.model.Contato
import br.com.miguel.agenda.agenda.contato.network.ContatosNetwork

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
object ContatosBusiness {
    fun buscarUsuario(id: Int, onSuccess:(contatos: List<Contato>) -> Unit, onFailure: () -> Unit){
        AuthDatabase.buscarUsuario(id){
            Log.d("tag", it.client.toString())
            Log.d("tag", it.uid.toString())
            Log.d("tag", it.accessToken.toString())
            Log.d("tag", "\n")

            ContatosNetwork.buscarContatos(it.uid.toString(), it.accessToken.toString(), it.client.toString(), {
                //Gravar no banco
                ContatosDatabase.salvarContatos(it)

                onSuccess(it)
            }, {
                onFailure()
            })
        }
    }

    fun criarContato(idUsuario: Int, contato: Contato, onSuccess:() -> Unit, onFailure:() -> Unit){
        AuthDatabase.buscarUsuario(idUsuario) {
            ContatosNetwork.criarContato(it, contato, {
                Log.d("erro", it.id.toString())
                ContatosDatabase.criarContato(it){
                    onSuccess()
                }
            }, {
                onFailure()
            })
        }
    }

    fun buscarContato(contatoId: Int, onSuccess: (contato: Contato) -> Unit) {
        ContatosDatabase.buscarContato(contatoId) {
            onSuccess(it)
        }
    }

    fun buscarContatosDatabase(onSuccess: (List<Contato>) -> Unit) {
        ContatosDatabase.buscarContatos { listaContatos ->
            onSuccess(listaContatos)
        }
    }

    fun editarContato(uid: String, cliente: String, accessToken : String, contato: Contato, id: String, onSuccess:() -> Unit, onFailure: () -> Unit){

        Log.d("info", uid)
        Log.d("info", cliente)
        Log.d("info", accessToken)
        Log.d("info", id)

        ContatosNetwork.editarContato(uid, accessToken, cliente, contato, id ,{
            ContatosDatabase.editarContato(contato) {
                onSuccess()
            }
        }, {
            onFailure()
        })
    }

    fun deletarContato(uid: String, cliente: String, accessToken: String, id: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        Log.d("info", uid)
        Log.d("info", cliente)
        Log.d("info", accessToken)
        Log.d("info", id)

        ContatosNetwork.deletarContato(uid, accessToken, cliente, id, {
            ContatosDatabase.deletarContato(id.toInt()) {
                Log.d("tag", "deletando contato de id: ${id}")
                onSuccess()
            }
        }, {
            onFailure()
        })
    }
}