package br.com.btm.btmapplication

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_view_model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sign

class SignatureAdapter(var signatures: MutableList<Signature>, var usuario: String, private val context: MainActivity) :
        RecyclerView.Adapter<SignatureAdapter.SignatureViewHolder>() {
    override fun getItemCount(): Int {
        return signatures.size
    }
    fun setList(signatures: MutableList<Signature>) {
        this.signatures = signatures
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): SignatureViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_signature, parent, false)
        return SignatureViewHolder(v)
    }
    override fun onBindViewHolder(holder: SignatureViewHolder, i: Int)
    {
        val signature = signatures[i]
        holder.tvService.text = signature.servico
        holder.tvDate.text = signature.data
        holder.tvTime.text = signature.tempo
        holder.btnDelete.setOnClickListener {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://signatures-api.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val service = retrofit.create(SignaturesApiInterface::class.java)

            service.deleteSignature(usuario, signature.key).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                            context,
                            R.string.genericError,
                            Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onResponse(call: Call<String>, response: Response<String>?) {
                    if (response?.code() in 200..299) {
                        Toast.makeText(
                                context,
                                R.string.deleteSuccess,
                                Toast.LENGTH_SHORT
                        ).show()
                        signatures.removeAt(i)
                        notifyItemRemoved(i)
                    } else {
                        Toast.makeText(
                                context,
                                R.string.deleteError,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        };

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, CadastroAssinaturaActivity::class.java)
            intent.putExtra("key", signature.key)
            intent.putExtra("servico", signature.servico)
            intent.putExtra("data", signature.data)
            intent.putExtra("tempo", signature.tempo)
            intent.putExtra("usuario", usuario)
            context.startActivity(intent)
        };
    }
    class SignatureViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvService: TextView = v.findViewById(R.id.tvService)
        var tvDate: TextView = v.findViewById(R.id.tvDate)
        var tvTime: TextView = v.findViewById(R.id.tvTime)
        var btnEdit: Button = v.findViewById(R.id.btnEdit)
        var btnDelete: Button = v.findViewById(R.id.btnDelete)
    }
}