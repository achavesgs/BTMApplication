package br.com.btm.btmapplication

import android.content.Intent
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cadastro_assinatura.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CadastroAssinaturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_assinatura)

        val usuario = this.intent.getStringExtra("usuario")
        val key = this.intent?.getStringExtra("key")

        if (!key.isNullOrEmpty()) {
            val service = this.intent.getStringExtra("servico")
            val date = this.intent.getStringExtra("data")
            val time = this.intent.getStringExtra("tempo")

            var tvService: TextView = findViewById(R.id.inputService)
            tvService.text = service

            var tvDate: TextView = findViewById(R.id.inputDate)
            tvDate.text = date

            var tvTime: TextView = findViewById(R.id.inputTime)
            tvTime.text = time
        }

        btSaveSignature.setOnClickListener {
            doRequest()
        }
    }

    private fun doRequest() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://signatures-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SignaturesApiInterface::class.java)

        val usuario = this.intent.getStringExtra("usuario")
        val key = this.intent?.getStringExtra("key")
        var request : Call<String>;

        if (!key.isNullOrEmpty()) {
            val signature = Signature(inputDate.text.toString(), inputService.text.toString(), inputTime.text.toString(), key!!)
            request = service.updateSignature(usuario, key!!, signature)
        } else {
            val signature = Signature(inputDate.text.toString(), inputService.text.toString(), inputTime.text.toString(), "")
            request = service.createSignature(usuario, signature)
        }

        request.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable) {
                Toast.makeText(
                        this@CadastroAssinaturaActivity,
                        R.string.genericError,
                        Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response?.code() in 200..299) {
                    val intent = Intent(this@CadastroAssinaturaActivity, MainActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                            this@CadastroAssinaturaActivity,
                            R.string.createError,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
