package br.com.miguel.agenda.agenda.contato.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.module.Contato
import kotlinx.android.synthetic.main.activity_criar_contato.*

class CriarContatoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_contato)

        val id = intent.getIntExtra("id", 0)
        testText.text = id.toString()

        setupAdicionarContatoButton(id)
    }

    private fun setupAdicionarContatoButton(usuarioId: Int){
        adicionarContatoButton.setOnClickListener {
            var contato = Contato()
            contato.name = nomeContatoEditText.text.toString()
            contato.email = emailContatoEditText.text.toString()
            contato.phone = telefoneContatoEditText.text.toString()
            contato.picture = imagemUrlContatoEditText.text.toString()

            ContatosBusiness.criarContato(usuarioId, contato) {
                val extraBundle = Bundle()
                extraBundle.putInt("id", usuarioId)

                val intent = Intent(this, ContatosActivity::class.java)
                intent.putExtras(extraBundle)
                startActivity(intent)
            }
        }
    }
}
