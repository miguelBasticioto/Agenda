package br.com.miguel.agenda.agenda.contato.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.model.Contato
import br.com.miguel.agenda.agenda.contato.view.holder.ContatoViewHolder

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
class ContatosAdapter : RecyclerView.Adapter<ContatoViewHolder>() {

    private lateinit var contatos: List<Contato>

    fun refresh(onSuccess: () -> Unit = {}, onFailure:() -> Unit = {}){

        contatos = ContatosBusiness.listarContatosDatabase()
        notifyDataSetChanged()

        AuthBusiness.buscarUsuario {  usuario ->

            ContatosBusiness.listarContatosNetwork({ contatosNetwork ->

                contatos = contatosNetwork
                notifyDataSetChanged()
                onSuccess()

            }, {
                onFailure()
            }, usuario)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_contato, parent, false)

        return ContatoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contatos.size
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.bind(contatos[position])
    }

}