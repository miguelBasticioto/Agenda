package br.com.miguel.agenda.agenda.contato.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.model.Contato
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_contatos.*
import kotlinx.android.synthetic.main.activity_info_contato.*
import kotlinx.android.synthetic.main.activity_login.*

class ContatoInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_contato)

        Realm.init(this)

        val contatoId = intent.getIntExtra("contatoId", -1)

        if (contatoId != -1) {
            deletarContatoButton.visibility = View.VISIBLE

            criarContatoButton.text = "Salvar"

            //Buscar contato no banco
            ContatosBusiness.buscarContato(contatoId) { contato ->
                nomeContatoEditText.setText(contato.name)
                emailContatoEditText.setText(contato.email)
                telefoneContatoEditText.setText(contato.phone)
                imagemUrlContatoEditText.setText(contato.picture)

            }
        }

        setupCriarContatoButton(contatoId)
        setupDeletarContatoButton(contatoId)
    }

    private fun setupCriarContatoButton(contatoId: Int) {
        if (contatoId == -1) {
            criarContatoButton.setOnClickListener {

                val name = nomeContatoEditText.text.toString()
                val email = emailContatoEditText.text.toString()
                val phone = telefoneContatoEditText.text.toString()
                val picture = imagemUrlContatoEditText.text.toString()

                if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && picture.isNotEmpty()) {

                    criarContatoButton.isEnabled = false
                    criarContatoProgressBar.visibility = View.VISIBLE

                    var contato = Contato()
                    contato.name = name
                    contato.email = email
                    contato.phone = phone
                    contato.picture = picture

                    ContatosBusiness.criarContato(contato, {

                        val intent = Intent(this, ContatosActivity::class.java)
                        startActivity(intent)
                    }, {
                        criarContatoButton.isEnabled = true
                        criarContatoProgressBar.visibility = View.INVISIBLE
                        Snackbar.make(criarContatoButton, getString(R.string.semConexao), Snackbar.LENGTH_SHORT).show()
                    })
                } else {
                    Snackbar.make(criarContatoButton, R.string.camposObrigatorios, Snackbar.LENGTH_SHORT).show()
                }

            }
        } else {
            //Editar contato
            criarContatoButton.setOnClickListener {

                if (!nomeContatoEditText.text.isEmpty() && !emailContatoEditText.text.isEmpty() &&
                        !telefoneContatoEditText.text.isEmpty() && !imagemUrlContatoEditText.text.isEmpty()) {

                    mostrarFeedback()

                    val contato = Contato()
                    contato.id = contatoId
                    contato.name = nomeContatoEditText.text.toString()
                    contato.email = emailContatoEditText.text.toString()
                    contato.phone = telefoneContatoEditText.text.toString()
                    contato.picture = imagemUrlContatoEditText.text.toString()
                    contato.birth = 0

                    ContatosBusiness.editarContato(contato, contatoId.toString(), {
                        //chamar proxima tela

                        val intent = Intent(this, ContatosActivity::class.java)
                        startActivity(intent)

                    }, {
                        esconderFeedback(R.string.semConexao)
                    })
                } else {
                    esconderFeedback(R.string.camposObrigatorios)
                }
            }

        }
    }

    private fun setupDeletarContatoButton(contatoId: Int) {

        deletarContatoButton.setOnClickListener {

            mostrarFeedback()

            ContatosBusiness.deletarContato( contatoId, {

                val intent = Intent(this, ContatosActivity::class.java)
                startActivity(intent)
            }, {

                esconderFeedback(R.string.semConexao)

            })
        }
    }

    fun mostrarFeedback (){
        deletarContatoButton.isEnabled = false
        criarContatoButton.isEnabled = false
        criarContatoProgressBar.visibility = View.VISIBLE
    }

    fun esconderFeedback(@StringRes mensagem:Int){
        deletarContatoButton.isEnabled = true
        criarContatoButton.isEnabled = true
        criarContatoProgressBar.visibility = View.INVISIBLE
        Snackbar.make(deletarContatoButton, mensagem, Snackbar.LENGTH_SHORT).show()
    }
}
