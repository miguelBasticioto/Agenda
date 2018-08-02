package br.com.miguel.agenda.agenda.auth.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.contato.view.activity.ContatosActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_login.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Realm.init(this)

        val usuario = AuthBusiness.checarUsuario()

        if(usuario != null){
            //proxima tela
            val extraBundle = Bundle()
            extraBundle.putInt("id", usuario.id)

            val intent = Intent(this, ContatosActivity::class.java)
            intent.putExtras(extraBundle)
            startActivity(intent)
        }

        setupCriarButton()
        setupEntrarButton()
    }

    private fun setupCriarButton(){
       criarButton.setOnClickListener{ view ->
           if(!emailEditText.text.isEmpty() && !passwordEditText.text.isEmpty()) {
               criarProgress.visibility = View.VISIBLE
               criarButton.isEnabled = false
               entrarButton.isEnabled = false
               AuthBusiness.criarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {
                   criarProgress.visibility = View.INVISIBLE
                   criarButton.isEnabled = true
                   entrarButton.isEnabled = true
                   Snackbar.make(view, getString(R.string.usuarioCriadoSucesso), Snackbar.LENGTH_SHORT).show()
               }, {
                   criarProgress.visibility = View.INVISIBLE
                   criarButton.isEnabled = true
                   entrarButton.isEnabled = true
                   Snackbar.make(view, getString(R.string.usuarioCriadoFracasso), Snackbar.LENGTH_SHORT).show()
               }, {
                   criarProgress.visibility = View.INVISIBLE
                   criarButton.isEnabled = true
                   entrarButton.isEnabled = true
                   Snackbar.make(view, getString(R.string.semConexao), Snackbar.LENGTH_SHORT).show()
               })
           } else {
               Snackbar.make(view, getString(R.string.camposObrigatorios), Snackbar.LENGTH_SHORT).show()
           }
       }
    }

    private fun setupEntrarButton(){
        entrarButton.setOnClickListener{ view->
            if(!emailEditText.text.isEmpty() && !passwordEditText.text.isEmpty()) {
                entrarProgress.visibility = View.VISIBLE
                entrarButton.isEnabled = false
                criarButton.isEnabled = false
                AuthBusiness.logarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {

                    Snackbar.make(view, getString(R.string.logadoSucesso), Snackbar.LENGTH_SHORT).show()

                    val extraBundle = Bundle()
                    extraBundle.putInt("id", it.id)

                    val intent = Intent(this, ContatosActivity::class.java)
                    intent.putExtras(extraBundle)
                    startActivity(intent)
                    finish()
                    entrarProgress.visibility = View.INVISIBLE
                }, {
                    entrarProgress.visibility = View.INVISIBLE
                    Snackbar.make(view, getString(R.string.logadoFracasso), Snackbar.LENGTH_SHORT).show()
                    entrarButton.isEnabled = true
                    criarButton.isEnabled = true
                }, {
                    entrarProgress.visibility = View.INVISIBLE
                    Snackbar.make(view, getString(R.string.semConexao), Snackbar.LENGTH_SHORT).show()
                    entrarButton.isEnabled = true
                    criarButton.isEnabled = true
                })
            } else {
                Snackbar.make(view, R.string.camposObrigatorios, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
