package br.com.miguel.agenda.agenda.contato.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.auth.view.activity.AuthActivity
import br.com.miguel.agenda.agenda.contato.adapter.ContatosAdapter
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.module.Contato
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_contatos.*

class ContatosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)

        val id = intent.getIntExtra("id", 0)

        Realm.init(this)

        buscarContatos(id)
        setupAdicionarContatFab(id)
        setupLogoutBotao(id)
    }

    private fun buscarContatos(id: Int){
        ContatosBusiness.buscarUsuario(id){
            setupRecyclerView(it, id)
        }
    }

    private fun setupRecyclerView(contatos: List<Contato>, usuarioId: Int){
        recyclerViewContatos.adapter = ContatosAdapter(contatos, usuarioId)
    }

    private fun setupAdicionarContatFab (id: Int){
        adicionarContatoFab.setOnClickListener {
            val extraBundle = Bundle()
            extraBundle.putInt("usuarioId", id)

            val intent = Intent(this, ContatoInfoActivity::class.java)
            intent.putExtras(extraBundle)

            startActivity(intent)
        }
    }

    private fun setupLogoutBotao(id: Int){
        logoutBotao.setOnClickListener{view ->
            //Configurar botao de logout
            AuthBusiness.buscarUsuario(id) {
                AuthBusiness.logout(it.uid!!, it.accessToken!!, it.client!!) {
                    Log.d("Logout", "Deslogado")
                    //Voltar para tela de login
                    val intent = Intent(view.context, AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {

    }
}
