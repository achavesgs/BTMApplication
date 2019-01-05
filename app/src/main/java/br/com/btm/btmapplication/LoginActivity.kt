package br.com.btm.btmapplication

import android.app.Activity
import android.content.Context
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

        val sharedPreferences = getSharedPreferences("meuapp",
                Context.MODE_PRIVATE)

        if(sharedPreferences.getBoolean("MANTER_CONECTADO", false)){
            proximaTela()
        }
        btSubscribe.setOnClickListener{
            startActivity(Intent(this, CadastroUsuarioActivity::class.java))
        }

        btLogar.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("MANTER_CONECTADO", cbManterConectado.isChecked)
            editor.putString("USUARIO", inputNome.text.toString())
            editor.putString("PASSWORD", inputPassword.text.toString())
            editor.apply()
            proximaTela()
        }
    }

    private fun proximaTela() {
        val intent = Intent(this, CadastroAssinaturaActivity::class.java)
//        intent.putExtra("nome", inputNome.value())
//        intent.putExtra("senha", inputPassword.value())
        startActivity(intent)
    }
}