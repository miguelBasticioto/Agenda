package br.com.miguel.agenda.agenda.contato.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import br.com.miguel.agenda.R
import io.realm.Realm

class ContatosActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contatos)

    }

}