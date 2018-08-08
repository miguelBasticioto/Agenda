package br.com.miguel.agenda.agenda.module.contato.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.module.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.module.contato.adapter.ContatosAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_contatos.*

class ContatosActivity : AppCompatActivity() {

    private var clicado = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)
        setSupportActionBar(toolbar)

        Realm.init(this)

        setupRecyclerView()
        setupSwipeLayout()
        setupAdicionarContatFab()

    }

    override fun onResume() {
        super.onResume()
        refreshRecyclerView()
    }

    private fun setupRecyclerView (){
        recyclerViewContatos.adapter = ContatosAdapter()
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {

        recyclerViewSwipeLayout.isRefreshing = true

        (recyclerViewContatos.adapter as ContatosAdapter).refresh({

            recyclerViewSwipeLayout.isRefreshing = false
        }, {
            Snackbar.make(recyclerViewContatos, getString(R.string.semConexao), Snackbar.LENGTH_SHORT).show()
            recyclerViewSwipeLayout.isRefreshing = false
        })
    }

    private fun setupSwipeLayout() {

        recyclerViewSwipeLayout.setOnRefreshListener {
            refreshRecyclerView()
        }

    }

    private fun setupAdicionarContatFab() {
        adicionarContatoFab.setOnClickListener {
            intentContatoInfoActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            R.id.logout -> {

                //Configurar botao de logout
                AuthBusiness.logout({
                    //Voltar para tela de login
                    finish()
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

    private fun intentContatoInfoActivity() {
        val intent = Intent(this, ContatoInfoActivity::class.java)

        startActivity(intent)
    }
}
