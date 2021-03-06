package br.com.btm.btmapplication

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SignatureListViewModel : ViewModel() {
    private var usuarioId: String = "";
    private val signatures: MutableLiveData<MutableList<Signature>> by lazy {
        MutableLiveData<MutableList<Signature>>().also {
            loadSignatures(usuarioId)
        }
    }

    fun getSignatures(usuario: String): LiveData<MutableList<Signature>> {
        usuarioId = usuario
        return signatures
    }

    private fun loadSignatures(usuario: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://signatures-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SignaturesApiInterface::class.java)

        service.mySignatures(usuario)
                .enqueue(object : Callback<MutableList<Signature>> {
                    override fun onResponse(call: Call<MutableList<Signature>>, response: Response<MutableList<Signature>>) {
                        if (response.isSuccessful) {
                            signatures.setValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<MutableList<Signature>>, t: Throwable) {
                        signatures.setValue(null)
                    }
                })
    }
}