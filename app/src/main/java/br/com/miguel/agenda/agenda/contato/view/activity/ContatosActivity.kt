package br.com.miguel.agenda.agenda.contato.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.auth.model.Usuario
import br.com.miguel.agenda.agenda.auth.view.activity.AuthActivity
import br.com.miguel.agenda.agenda.contato.adapter.ContatosAdapter
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.model.Contato
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_contatos.*

class ContatosActivity : AppCompatActivity() {

    private var clicado = false
    private var usuario: Usuario = AuthBusiness.buscarUsuarioDatabase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)
        setSupportActionBar(toolbar)

        Realm.init(this)

        setupSwipeLayout()
        setupAdicionarContatFab()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun setupRecyclerView(contatos: List<Contato>) {
        recyclerViewContatos.adapter = ContatosAdapter(contatos)
    }

    private fun setupAdicionarContatFab() {
        adicionarContatoFab.setOnClickListener {

            val intent = Intent(this, ContatoInfoActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.logout -> {
                //Configurar botao de logout
                AuthBusiness.logout({
                    Log.d("Logout", "Deslogado")
                    //Voltar para tela de login
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                }, {
                    Snackbar.make(toolbar, getString(R.string.semConexao), Snackbar.LENGTH_SHORT).show()
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (clicado) {
            finishAffinity()
            return
        }

        clicado = true
        Snackbar.make(recyclerViewContatos, getString(R.string.confirmarSair), 2000).show()
        Handler().postDelayed({ clicado = false }, 2000)
    }

    private fun setupSwipeLayout() {
        refreshRecyclerView()

        recyclerViewSwipeLayout.setOnRefreshListener {
            refreshRecyclerView()
        }
    }

    private fun refreshRecyclerView() {

        recyclerViewSwipeLayout.isRefreshing = true
        var contatos = ContatosBusiness.listarContatosDatabase()
        setupRecyclerView(contatos.sortedBy { it.name })

        recyclerViewProgress.visibility = View.INVISIBLE

        recyclerViewSwipeLayout.isRefreshing = false

        ContatosBusiness.listarContatosNetwork({ contatos ->

            setupRecyclerView(contatos.sortedBy { it.name })
            recyclerViewSwipeLayout.isRefreshing = false

        }, {

            recyclerViewSwipeLayout.isRefreshing = false
            Snackbar.make(recyclerViewContatos, R.string.semConexao, Snackbar.LENGTH_SHORT).show()

        }, usuario)


    }

}
