package br.com.miguel.agenda.agenda.contato.view.holder

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.miguel.agenda.agenda.contato.model.Contato
import br.com.miguel.agenda.agenda.contato.view.activity.ContatoInfoActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.viewholder_contato.view.*

/**
 * Created by Miguel Basticioto on 19/07/2018.
 */
class ContatoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private var contatoId: Int = -1

    fun bind(contato: Contato, usuarioId: Int) {
        with(view) {
            textViewNome.text = contato.name
            textViewTelefone.text = contato.phone
            textViewEmail.text = contato.email
            contatoId = contato.id
            Picasso.get().load(contato.picture).into(imageViewContato)

            setOnClickListener {
                val extraBundle = Bundle()
                extraBundle.putInt("contatoId", contatoId)
                extraBundle.putInt("usuarioId", usuarioId)

                val intent = Intent(textViewEmail.context, ContatoInfoActivity::class.java)
                intent.putExtras(extraBundle)

                startActivity(textViewEmail.context, intent, extraBundle)
            }

        }
    }

}