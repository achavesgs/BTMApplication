package br.com.btm.btmapplication
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val usuario = intent.getStringExtra("usuario")

        val retrofit = Retrofit.Builder()
                .baseUrl("https://signatures-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(SignaturesApiInterface::class.java)

        service.mySignatures(usuario)
                .enqueue(object : Callback<List<Signature>> {
                    override fun onFailure(call: Call<List<Signature>>, t: Throwable) {
                        Toast.makeText(
                                this@MainActivity,
                                R.string.genericError,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<List<Signature>>, response: Response<List<Signature>>) {
                        Toast.makeText(
                                this@MainActivity,
                                "FOOOOOOI",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                })

    }

}
