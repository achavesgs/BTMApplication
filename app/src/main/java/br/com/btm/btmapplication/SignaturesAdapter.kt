package br.com.btm.btmapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SignatureAdapter(var signatures: List<Signature>) :
        RecyclerView.Adapter<SignatureAdapter.SignatureViewHolder>() {
    override fun getItemCount(): Int {
        return signatures.size
    }
    fun setList(signatures: List<Signature>) {
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
    }
    class SignatureViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvService: TextView = v.findViewById(R.id.tvService)
        var tvDate: TextView = v.findViewById(R.id.tvDate)
        var tvTime: TextView = v.findViewById(R.id.tvTime)
    }
}