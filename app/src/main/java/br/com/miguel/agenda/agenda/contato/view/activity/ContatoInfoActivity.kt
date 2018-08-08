package br.com.miguel.agenda.agenda.contato.view.activity

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.model.Contato
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_info_contato.*

class ContatoInfoActivity : AppCompatActivity() {
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var phone: String
    private lateinit var picture:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_contato)

        Realm.init(this)

        val contatoId = intent.getIntExtra("contatoId", -1)

        if (contatoId != -1) {
            deletarContatoButton.visibility = View.VISIBLE

            criarContatoButton.text = getString(R.string.salvarButton)

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

                name = nomeContatoEditText.text.toString()
                email = emailContatoEditText.text.toString()
                phone = telefoneContatoEditText.text.toString()
                picture = imagemUrlContatoEditText.text.toString()

                if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && picture.isNotEmpty()) {

                    mostrarFeedback()

                    val contato = Contato()
                    contato.name = name
                    contato.email = email
                    contato.phone = phone
                    contato.picture = picture

                    ContatosBusiness.criarContato(contato, {

                        finish()
                    }, {
                        esconderFeedback(R.string.semConexao)
                    })
                } else {
                    esconderFeedback(R.string.camposObrigatorios)
                }

            }
        } else {
            //Editar contato
            criarContatoButton.setOnClickListener {
                name = nomeContatoEditText.text.toString()
                email = emailContatoEditText.text.toString()
                phone = telefoneContatoEditText.text.toString()
                picture = imagemUrlContatoEditText.text.toString()

                if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && picture.isNotEmpty()) {

                    mostrarFeedback()

                    val contato = Contato()

                    contato.id = contatoId
                    contato.name = name
                    contato.email = email
                    contato.phone = phone
                    contato.picture = picture
                    contato.birth = 0

                    ContatosBusiness.editarContato(contato, contatoId.toString(), {
                        //chamar proxima tela


                        finish()

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

                finish()
            }, {

                esconderFeedback(R.string.semConexao)

            })
        }
    }

    private fun mostrarFeedback (){
        deletarContatoButton.isEnabled = false
        criarContatoButton.isEnabled = false
        criarContatoProgressBar.visibility = View.VISIBLE
    }

    private fun esconderFeedback(@StringRes mensagem:Int){
        deletarContatoButton.isEnabled = true
        criarContatoButton.isEnabled = true
        criarContatoProgressBar.visibility = View.INVISIBLE
        Snackbar.make(deletarContatoButton, mensagem, Snackbar.LENGTH_SHORT).show()
    }

}
