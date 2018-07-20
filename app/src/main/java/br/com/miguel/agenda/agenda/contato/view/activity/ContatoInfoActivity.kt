package br.com.miguel.agenda.agenda.contato.view.activity

import android.content.Intent
import android.os.Bundle
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
        testText.text = id.toString()

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
        } else {
            //Editar contato
            criarContatoButton.setOnClickListener{
                AuthBusiness.buscarUsuario(usuarioId) {
                    val uid = it.uid
                    val accessToken = it.accessToken
                    val cliente = it.client

                    val contato = Contato()
                    contato.id = contatoId
                    contato.name = nomeContatoEditText.text.toString()
                    contato.email = emailContatoEditText.text.toString()
                    contato.phone = telefoneContatoEditText.text.toString()
                    contato.birth = 0

                    ContatosBusiness.editarContato(uid!!,cliente!!,accessToken!!,contato,contatoId.toString()) {
                        //chamar proxima tela
                        val extraBundle = Bundle()
                        extraBundle.putInt("id", usuarioId)

                        val intent = Intent(this, ContatosActivity::class.java)
                        intent.putExtras(extraBundle)
                        startActivity(intent)

                    }
                }
            }

        }
    }

    private fun setupDeletarContatoButton(usuarioId: Int, contatoId: Int){
        deletarContatoButton.setOnClickListener {
            AuthBusiness.buscarUsuario(usuarioId, {
                ContatosBusiness.deletarContato(it.uid!!, it.client!!, it.accessToken!!, contatoId.toString()) {
                    val extraBundle = Bundle()
                    extraBundle.putInt("id", usuarioId)

                    val intent = Intent(this, ContatosActivity::class.java)
                    intent.putExtras(extraBundle)
                    startActivity(intent)
                }
            })
        }
    }
}
