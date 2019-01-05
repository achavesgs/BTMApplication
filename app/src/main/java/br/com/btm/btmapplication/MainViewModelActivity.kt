package br.com.btm.btmapplication

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainViewModelActivity : AppCompatActivity() {

    class MainViewModel: ViewModel(){

        var nomeCliente = ""
        var telefoneCliente = ""
        var atumSelecionado = false
        var baconSelecionado = false
        var calabresaSelecionada = false
        var portuguesaSelecionada = false
    }
}
