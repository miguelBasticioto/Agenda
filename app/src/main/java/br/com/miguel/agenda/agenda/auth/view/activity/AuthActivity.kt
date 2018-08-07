package br.com.miguel.agenda.agenda.auth.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.miguel.agenda.R
import br.com.miguel.agenda.agenda.auth.business.AuthBusiness
import br.com.miguel.agenda.agenda.auth.database.AuthDatabase
import br.com.miguel.agenda.agenda.contato.view.activity.ContatosActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_login.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Realm.init(this)

        if (AuthBusiness.isLogado()) {
            //proxima tela

            val intent = Intent(this, ContatosActivity::class.java)
            startActivity(intent)

            finish()
        }

        setupCriarButton()
        setupEntrarButton()
    }

    private fun setupCriarButton() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        criarButton.setOnClickListener { view ->

            if (AuthBusiness.validarEmail(email) && AuthBusiness.validarSenha(password)) {

                mostrarFeedback()

                AuthBusiness.criarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {

                    esconderFeedback(R.string.usuarioCriadoSucesso)

                }, {

                    esconderFeedback(R.string.usuarioCriadoFracasso)

                }, {

                    esconderFeedback(R.string.semConexao)

                })
            } else {

                Snackbar.make(view, getString(R.string.camposObrigatorios), Snackbar.LENGTH_SHORT).show()

            }
        }
    }

    private fun setupEntrarButton() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        entrarButton.setOnClickListener { view ->

            mostrarFeedback()

            if (AuthBusiness.validarEmail(email) && AuthBusiness.validarSenha(password)) {

                mostrarFeedback()

                AuthBusiness.logarUsuario(emailEditText.text.toString(), passwordEditText.text.toString(), {

                    Snackbar.make(view, getString(R.string.logadoSucesso), Snackbar.LENGTH_SHORT).show()


                    val intent = Intent(this, ContatosActivity::class.java)
                    startActivity(intent)

                    finish()

                    entrarProgress.visibility = View.INVISIBLE
                }, {
                    esconderFeedback(R.string.logadoFracasso)
                }, {
                    esconderFeedback(R.string.semConexao)
                })
            } else {
                Snackbar.make(view, R.string.camposObrigatorios, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun mostrarFeedback (){
        entrarProgress.visibility = View.VISIBLE
        entrarButton.isEnabled = false
        criarButton.isEnabled = false
    }

    private fun esconderFeedback (@StringRes mensagem: Int){
        entrarProgress.visibility = View.INVISIBLE
        Snackbar.make(emailEditText, mensagem, Snackbar.LENGTH_SHORT).show()
        entrarButton.isEnabled = true
        criarButton.isEnabled = true
    }
}
