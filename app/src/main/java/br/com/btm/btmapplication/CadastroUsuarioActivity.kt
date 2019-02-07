package br.com.btm.btmapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CadastroUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        btCreateAccount.setOnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://signatures-api.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val service = retrofit.create(SignaturesApiInterface::class.java)

            service.signUp(etUserName.text.toString(), etPassword.text.toString())
                .enqueue(object : Callback<Usuario> {
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(
                                this@CadastroUsuarioActivity,
                                R.string.genericError,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.code() in 200..299) {
                            goToLogin()
                        } else {
                            Toast.makeText(
                                    this@CadastroUsuarioActivity,
                                    R.string.signupError,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
