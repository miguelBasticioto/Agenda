package br.com.miguel.agenda.agenda.contato.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.contato.model.Contato
import br.com.miguel.agenda.agenda.contato.view.holder.ContatoViewHolder

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
class ContatosAdapter(contatos: List<Contato>) : RecyclerView.Adapter<ContatoViewHolder>() {
    var listaContatos = contatos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_contato, parent, false)

        return ContatoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaContatos.size
    }

    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.bind(listaContatos[position])
    }
}