package com.example.magicsquare

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var nickname = ""
    private var level = 1

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.findViewById<Button>(R.id.playGame).setOnClickListener {
            val intent = Intent(this, PlayGameActivity::class.java).apply {
                putExtra("nickname", nickname)
                putExtra("level", level)
            }
            startActivity(intent)
        }

        this.findViewById<Button>(R.id.parameters).setOnClickListener {
            val intent = Intent(this, ParametersActivity::class.java)
            startActivityForResult(intent, 1)
        }

        this.findViewById<Button>(R.id.about).setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Magic_square"))
            startActivity(intent)
        }

        this.findViewById<Button>(R.id.quit).setOnClickListener {
            this.finishAffinity()
        }
    }

    override fun onResume() {
        println("resume")
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode === Activity.RESULT_OK) {
                    this.nickname = data?.getStringExtra("nickname").toString()
                    this.level = data?.getStringExtra("level")?.toInt() ?: 1
                }
            }
        }
    }


}
