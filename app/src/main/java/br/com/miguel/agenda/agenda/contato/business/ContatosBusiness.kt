package br.com.miguel.agenda.agenda.contato.business

import br.com.miguel.agenda.agenda.contato.database.ContatosDatabase

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
object ContatosBusiness {
    fun buscarUsuario(id: Int){
        ContatosDatabase.buscarUsuario(id)
    }
}