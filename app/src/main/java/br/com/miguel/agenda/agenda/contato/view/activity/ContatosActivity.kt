package br.com.miguel.agenda.agenda.contato.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_contatos.*

class ContatosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)

        val id = intent.getIntExtra("id", 0)
        userIdText.text = id.toString()

        Realm.init(this)

        buscarUsuario(id)
    }

    private fun buscarUsuario(id: Int){
        ContatosBusiness.buscarUsuario(id)
    }
}
