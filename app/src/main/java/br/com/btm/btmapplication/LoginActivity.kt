package br.com.btm.btmapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.btm.btmapplication.extensions.value
import br.com.btm.btmapplication.CadastroUsuarioActivity
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://signatures-api.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val service = retrofit.create(SignaturesApiInterface::class.java)

            service.signIn(inputNome.text.toString(), inputPassword.text.toString())
                    .enqueue(object : Callback<Usuario> {
                        override fun onFailure(call: Call<Usuario>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Deu Ruim", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                            Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()
                            proximaTela()
                        }
                    })
            editor.apply()

        }
    }

    private fun proximaTela() {
        val intent = Intent(this, CadastroAssinaturaActivity::class.java)
//         intent.putExtra("nome", inputNome.value())
//        intent.putExtra("senha", inputPassword.value())
        startActivity(intent)
    }
}