package br.com.miguel.agenda.agenda.login.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.login.business.LoginBusiness
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupCriarButton()
        setupEntrarButton()
    }

    private fun setupCriarButton(){
       criarButton.setOnClickListener{
           LoginBusiness.criarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {

           }, {
               Snackbar.make(it, "Usuário já cadastrado", Snackbar.LENGTH_SHORT).show()
           })
       }
    }

    private fun setupEntrarButton(){
        entrarButton.setOnClickListener{
            LoginBusiness.logarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {

            }, {
                Snackbar.make(it, "Login ou senha inválidos", Snackbar.LENGTH_SHORT).show()
            })
        }
    }
}
