package br.com.btm.btmapplication
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_view_model.*
import br.com.btm.btmapplication.SignatureAdapter
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import br.com.btm.btmapplication.Signature

class MainActivity : AppCompatActivity() {
    private var adapter: SignatureAdapter? = null
    private var signatures: List<Signature> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_model)
        mostrarDados()
        val usuario = this.intent.getStringExtra("usuario")
        rvSignatures.layoutManager = LinearLayoutManager(this)
        adapter = SignatureAdapter(signatures!!, usuario, this)
        rvSignatures.adapter = adapter
    }

    private fun mostrarDados() {
        val usuario = this.intent.getStringExtra("usuario")
        var model = ViewModelProviders.of(this)
            .get(SignatureListViewModel::class.java)
            .getSignatures(usuario)
            .observe(this, Observer<List<Signature>> { signatures ->
            adapter?.setList(signatures!!)
            rvSignatures.adapter.notifyDataSetChanged()
        })
    }
}
