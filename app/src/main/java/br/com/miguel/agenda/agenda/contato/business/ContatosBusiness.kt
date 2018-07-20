package br.com.miguel.agenda.agenda.contato.business

import android.util.Log
import br.com.miguel.agenda.agenda.auth.database.AuthDatabase
import br.com.miguel.agenda.agenda.contato.database.ContatosDatabase
import br.com.miguel.agenda.agenda.contato.module.Contato
import br.com.miguel.agenda.agenda.contato.network.ContatosNetwork

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
object ContatosBusiness {
    fun buscarUsuario(id: Int, onSuccess:(contatos: List<Contato>) -> Unit){
        AuthDatabase.buscarUsuario(id){
            Log.d("tag", it.client.toString())
            Log.d("tag", it.uid.toString())
            Log.d("tag", it.acessToken.toString())
            Log.d("tag", "\n")

            ContatosNetwork.buscarContatos(it.uid.toString(), it.acessToken.toString(), it.client.toString()) {
                //Gravar no banco
                ContatosDatabase.salvarContatos(it)

                onSuccess(it)
            }
        }
    }

    fun criarContato(idUsuario: Int, contato: Contato, onSuccess:() -> Unit){
        AuthDatabase.buscarUsuario(idUsuario) {
            ContatosNetwork.criarContato(it, contato){
                onSuccess()
            }
        }
    }
}