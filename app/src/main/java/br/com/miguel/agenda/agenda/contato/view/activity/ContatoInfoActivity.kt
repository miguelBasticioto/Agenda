package br.com.miguel.agenda.agenda.contato.view.activity

import android.content.Intent
import android.os.Bundle
import android.renderscript.Script
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.module.Contato
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_info_contato.*

class ContatoInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_contato)

        Realm.init(this)

        val id = intent.getIntExtra("usuarioId", 0)


        val contatoId = intent.getIntExtra("contatoId", -1)

        if(contatoId != -1) {
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

        setupCriarContatoButton(id, contatoId)
        setupDeletarContatoButton(id, contatoId)
    }

    private fun setupCriarContatoButton(usuarioId: Int, contatoId: Int){
        if(contatoId == -1) {
            criarContatoButton.setOnClickListener {
                criarContatoButton.isEnabled = false
                criarContatoProgressBar.visibility = View.VISIBLE
                var contato = Contato()
                contato.name = nomeContatoEditText.text.toString()
                contato.email = emailContatoEditText.text.toString()
                contato.phone = telefoneContatoEditText.text.toString()
                contato.picture = imagemUrlContatoEditText.text.toString()

                ContatosBusiness.criarContato(usuarioId, contato ,{
                    val extraBundle = Bundle()
                    extraBundle.putInt("id", usuarioId)

                    val intent = Intent(this, ContatosActivity::class.java)
                    intent.putExtras(extraBundle)
                    startActivity(intent)
                } ,{
                    criarContatoButton.isEnabled = true
                    criarContatoProgressBar.visibility = View.INVISIBLE
                    Snackbar.make(criarContatoButton, "Sem conexão", Snackbar.LENGTH_SHORT).show()
                })

            }
        } else {
            //Editar contato
            criarContatoButton.setOnClickListener{
                AuthBusiness.buscarUsuario(usuarioId) {
                    criarContatoButton.isEnabled = false
                    criarContatoProgressBar.visibility = View.VISIBLE
                    val uid = it.uid
                    val accessToken = it.accessToken
                    val cliente = it.client

                    val contato = Contato()
                    contato.id = contatoId
                    contato.name = nomeContatoEditText.text.toString()
                    contato.email = emailContatoEditText.text.toString()
                    contato.phone = telefoneContatoEditText.text.toString()
                    contato.picture = imagemUrlContatoEditText.text.toString()
                    contato.birth = 0

                    ContatosBusiness.editarContato(uid!!,cliente!!,accessToken!!,contato,contatoId.toString(), {
                        //chamar proxima tela
                        val extraBundle = Bundle()
                        extraBundle.putInt("id", usuarioId)

                        val intent = Intent(this, ContatosActivity::class.java)
                        intent.putExtras(extraBundle)
                        startActivity(intent)

                    }, {
                        criarContatoButton.isEnabled = true
                        criarContatoProgressBar.visibility = View.INVISIBLE
                        Snackbar.make(criarContatoButton, "Sem conexão", Snackbar.LENGTH_SHORT).show()
                    })
                }
            }

        }
    }

    private fun setupDeletarContatoButton(usuarioId: Int, contatoId: Int){
        deletarContatoButton.setOnClickListener {
            AuthBusiness.buscarUsuario(usuarioId, {
                deletarContatoButton.isEnabled = false
                deletarContatoProgressBar.visibility = View.VISIBLE
                val uid = it.uid
                val accessToken = it.accessToken
                val cliente = it.client

                ContatosBusiness.deletarContato(uid!!, cliente!!, accessToken!!, contatoId.toString(), {
                    val extraBundle = Bundle()
                    extraBundle.putInt("id", usuarioId)

                    val intent = Intent(this, ContatosActivity::class.java)
                    intent.putExtras(extraBundle)
                    startActivity(intent)
                }, {
                    deletarContatoButton.isEnabled = true
                    deletarContatoProgressBar.visibility = View.INVISIBLE
                    Snackbar.make(deletarContatoButton, "Sem conexão", Snackbar.LENGTH_SHORT).show()
                })
            })
        }
    }
}
