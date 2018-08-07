package br.com.miguel.agenda.agenda.contato.business

import android.util.Log
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.auth.database.AuthDatabase
import br.com.miguel.agenda.agenda.auth.model.Usuario
import br.com.miguel.agenda.agenda.contato.database.ContatosDatabase
import br.com.miguel.agenda.agenda.contato.model.Contato
import br.com.miguel.agenda.agenda.contato.network.ContatosNetwork

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
object ContatosBusiness {

    fun criarContato(contato: Contato, onSuccess: () -> Unit, onFailure: () -> Unit) {

        AuthDatabase.buscarUsuario { usuario ->

            ContatosNetwork.criarContato(usuario, contato, { contato ->

                ContatosDatabase.criarContato(contato) {
                    onSuccess()
                }
            }, {
                onFailure()
            })
        }

    }

    fun buscarContato(contatoId: Int, onSuccess: (contato: Contato) -> Unit) {
        ContatosDatabase.buscarContato(contatoId) { contato ->
            onSuccess(contato)
        }
    }

    fun listarContatosDatabase(): List<Contato> = ContatosDatabase.buscarContatos().sortedBy { it.name.toString().toLowerCase() }

    fun listarContatosNetwork(onSuccess: (List<Contato>) -> Unit, onFailure: () -> Unit, usuario: Usuario) {

        ContatosNetwork.buscarContatos(usuario.uid!!, usuario.accessToken!!, usuario.client!!, { contatos ->

            //Atualizar banco

            ContatosDatabase.limparContatos {  }
            ContatosDatabase.salvarContatos(contatos.sortedBy { it.name.toString().toLowerCase()})
            onSuccess(contatos.sortedBy { it.name.toString().toLowerCase() })
        }, {
            onFailure()
        })

    }

    fun editarContato(contato: Contato, id: String, onSuccess: () -> Unit, onFailure: () -> Unit) {

        AuthBusiness.buscarUsuario { usuario ->

            ContatosNetwork.editarContato(usuario.uid!!, usuario.accessToken!!, usuario.client!!, contato, id, {
                ContatosDatabase.editarContato(contato) {
                    onSuccess()
                }
            }, {
                onFailure()
            })

        }

    }

    fun deletarContato(id:Int, onSuccess: () -> Unit, onFailure: () -> Unit) {

        AuthBusiness.buscarUsuario { usuario ->
            ContatosNetwork.deletarContato(usuario.uid!!, usuario.accessToken!!, usuario.client!!, id, {
                ContatosDatabase.deletarContato(id) {
                    onSuccess()
                }
            }, {
                onFailure()
            })
        }

    }

}