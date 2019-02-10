package br.com.btm.btmapplication
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_view_model.*
import br.com.btm.btmapplication.SignatureAdapter
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import br.com.btm.btmapplication.Signature
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var adapter: SignatureAdapter? = null
    private var signatures: MutableList<Signature> = mutableListOf()
    private var usuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        usuario = this.intent.getStringExtra("usuario")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_model)
        mostrarDados()
        val usuario = this.intent.getStringExtra("usuario")
        rvSignatures.layoutManager = LinearLayoutManager(this)
        adapter = SignatureAdapter(signatures!!, usuario, this)
        rvSignatures.adapter = adapter


        btnAddSignature.setOnClickListener {
            val intent = Intent(this, CadastroAssinaturaActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }
    }

    public fun mostrarDados() {
        var model = ViewModelProviders.of(this)
            .get(SignatureListViewModel::class.java)
            .getSignatures(usuario)
            .observe(this, Observer<MutableList<Signature>> { signatures ->
            adapter?.setList(signatures!!)
            rvSignatures.adapter.notifyDataSetChanged()
        })
    }
}
