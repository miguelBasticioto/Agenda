package br.com.miguel.agenda.agenda.contato.view.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.miguel.agenda.agenda.contato.module.Contato
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_contato.view.*

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
class ContatoViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun bind(contato: Contato) {
        with(view) {
            textViewNome.text = contato.name
            textViewTelefone.text = contato.phone
            textViewEmail.text = contato.email
            Picasso.get().load(contato.picture).into(imageViewContato)
        }
    }
}