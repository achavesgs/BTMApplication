package br.com.btm.btmapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        btSubscribe.setOnClickListener{
            goToCadastro()
        }

        btLogar.setOnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://signatures-api.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val service = retrofit.create(SignaturesApiInterface::class.java)

            service.signIn(inputNome.text.toString(), inputPassword.text.toString())
                .enqueue(object : Callback<UsuarioResponse> {
                    override fun onFailure(call: Call<UsuarioResponse>?, t: Throwable) {
                        Toast.makeText(
                                this@LoginActivity,
                                R.string.genericError,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<UsuarioResponse>?, response: Response<UsuarioResponse>?) {
                        if (response?.code() in 200..299) {
                            val usuarioResponse = response?.body()
//                            if (usuario!!.emailVerified) {
                              goToMain(usuarioResponse!!.usuario.userId)

                                if (cbManterConectado.isChecked) {
                                    val editor = sharedPreferences.edit()
                                    editor.putBoolean("MANTER_CONECTADO", cbManterConectado.isChecked)
                                    editor.putString("USUARIO", inputNome.text.toString())
                                    editor.putString("PASSWORD", inputPassword.text.toString())
                                    editor.apply()
                                }
//                            } else {
//                                Toast.makeText(
//                                        this@LoginActivity,
//                                        R.string.emailNotVerifiedError,
//                                        Toast.LENGTH_SHORT
//                                ).show()
//                            }
                        } else {
                            Toast.makeText(
                                    this@LoginActivity,
                                    R.string.loginError,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        }
    }

    private fun goToCadastro() {
        val intent = Intent(this, CadastroAssinaturaActivity::class.java)
        startActivity(intent)
    }

    private fun goToMain(usuario: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }
}