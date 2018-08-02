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
import br.com.miguel.agenda.agenda.auth.view.activity.AuthActivity
import br.com.miguel.agenda.agenda.contato.adapter.ContatosAdapter
import br.com.miguel.agenda.agenda.contato.business.ContatosBusiness
import br.com.miguel.agenda.agenda.contato.module.Contato
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_contatos.*

class ContatosActivity : AppCompatActivity() {

    private var id = -1
    private var clicado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)
        setSupportActionBar(toolbar)

        id = intent.getIntExtra("id", 0)

        Realm.init(this)

        buscarContatos(id)
        setupAdicionarContatFab(id)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun buscarContatos(id: Int){

        recyclerViewProgress.visibility = View.VISIBLE

        //buscar do banco
        ContatosBusiness.buscarContatosDatabase {

            recyclerViewProgress.visibility = View.INVISIBLE
            setupRecyclerView(it.sortedBy { it.name }, id)
        }

        //Atualizar
        /*
        ContatosBusiness.buscarUsuario(id ,{

            recyclerViewProgress.visibility = View.INVISIBLE
            setupRecyclerView(it.sortedBy { it.name }, id)
        }, {
            ContatosBusiness.buscarContatosDatabase {

                setupRecyclerView(it.sortedBy { it.name }, id)
            }
            recyclerViewProgress.visibility = View.INVISIBLE
        })*/
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.logout -> {
                //Configurar botao de logout
                AuthBusiness.buscarUsuario(id) {
                    AuthBusiness.logout(it.uid!!, it.accessToken!!, it.client!! ,{
                        Log.d("Logout", "Deslogado")
                        //Voltar para tela de login
                        val intent = Intent(this, AuthActivity::class.java)
                        startActivity(intent)
                    }, {
                        Snackbar.make(toolbar, getString(R.string.semConexao), Snackbar.LENGTH_SHORT).show()
                    })
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(clicado) {
            finishAffinity()
            return
        }
        //Teste atualização(Deletar)
        ContatosBusiness.buscarUsuario(id ,{
            it.sortedBy { it.name }
            recyclerViewProgress.visibility = View.INVISIBLE
            setupRecyclerView(it, id)
        }, {
            Snackbar.make(recyclerViewContatos, R.string.semConexao, Snackbar.LENGTH_SHORT).show()
            ContatosBusiness.buscarContatosDatabase {
                it.sortedBy { it.name }
                setupRecyclerView(it, id)
            }
            recyclerViewProgress.visibility = View.INVISIBLE
        })

        clicado = true
        //Snackbar.make(recyclerViewContatos, getString(R.string.confirmarSair), 2000).show()
        Handler().postDelayed({ clicado = false}, 2000)
    }
}
