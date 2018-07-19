package br.com.miguel.agenda.agenda.auth.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
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

        setupCriarButton()
        setupEntrarButton()
    }

    private fun setupCriarButton(){
       criarButton.setOnClickListener{ view ->
           AuthBusiness.criarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {
               Snackbar.make(view, "Usuário criado com sucesso", Snackbar.LENGTH_SHORT).show()
           }, {
               Snackbar.make(view, "Usuário já cadastrado", Snackbar.LENGTH_SHORT).show()
           })
       }
    }

    private fun setupEntrarButton(){
        entrarButton.setOnClickListener{ view->
            AuthBusiness.logarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {
                Snackbar.make(view, "Logado com sucesso", Snackbar.LENGTH_SHORT).show()

                val extraBundle = Bundle()
                extraBundle.putInt("id", it.id)

                val intent = Intent(this, ContatosActivity::class.java)
                intent.putExtras(extraBundle)
                startActivity(intent)
            }, {
                Snackbar.make(view, "Login ou senha inválidos", Snackbar.LENGTH_SHORT).show()
            })
        }
    }
}
