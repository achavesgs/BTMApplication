package br.com.btm.btmapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.btm.btmapplication.extensions.value
import br.com.btm.btmapplication.CadastroUsuarioActivity
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btLogar.setOnClickListener {
            criarConta()
        }
    }

    private fun criarConta() {
        //está redirecionando para a classe CadastroUsuarioActivity
        val intent = Intent(this, CadastroUsuarioActivity::class.java)
        intent.putExtra("nome", inputNome.value())
        intent.putExtra("senha", inputPassword.value())
        startActivity(intent)
    }
}