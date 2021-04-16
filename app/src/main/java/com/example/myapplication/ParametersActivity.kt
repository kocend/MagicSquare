package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ParametersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)

        this.findViewById<Button>(R.id.save).setOnClickListener {
            val nickname = this.findViewById<EditText>(R.id.nickname).text.toString()
            val level = this.findViewById<EditText>(R.id.level).text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("nickname", nickname)
            resultIntent.putExtra("level", level)
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

    }
}