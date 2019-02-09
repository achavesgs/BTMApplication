package br.com.btm.btmapplication

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.ImageView


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        carregar();
    }

    private fun carregar() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()

        //Pegando o nosso objeto criado no layout
        val iv = findViewById(R.id.splashImg) as ImageView
        if (iv != null) {
            iv!!.clearAnimation()
            iv!!.startAnimation(anim)
        }

        val sharedPreferences = getSharedPreferences("meuapp",
                Context.MODE_PRIVATE)

        Handler().postDelayed({
            // Após o tempo definido irá executar a próxima tela
            val userId = sharedPreferences.getString("USER_ID", "")
            if (userId.isNotBlank()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", userId)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }, 1000)
    }
}
