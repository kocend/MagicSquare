package com.example.magicsquare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.*
import androidx.core.view.children
import kotlin.math.floor
import kotlin.math.roundToInt

class PlayGameActivity : AppCompatActivity() {

    private val inputs = mutableListOf<EditText>()
    private val results = mutableListOf<TextView>()
    private val nums = mutableListOf<Int>()

    private var nickname = ""
    private var level = 1
    private var helpCount = 0;

    private var start = 0L
    private var elapsed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        this.nickname = intent.getStringExtra("nickname").toString()
        this.level = intent.getIntExtra("level", 1)

        findViewById<TextView>(R.id.header).text = "Nickname: $nickname   Level: $level"

        val gridLayout = findViewById<GridLayout>(R.id.grid)

        gridLayout.children.forEach { if (it is EditText) {
            println(it.id)
        } }

        inputs.add(this.findViewById(R.id.et1))
        inputs.add(this.findViewById(R.id.et2))
        inputs.add(this.findViewById(R.id.et3))
        inputs.add(this.findViewById(R.id.et4))
        inputs.add(this.findViewById(R.id.et5))
        inputs.add(this.findViewById(R.id.et6))
        inputs.add(this.findViewById(R.id.et7))
        inputs.add(this.findViewById(R.id.et8))
        inputs.add(this.findViewById(R.id.et9))

        results.add(this.findViewById(R.id.res1))
        results.add(this.findViewById(R.id.res2))
        results.add(this.findViewById(R.id.res3))
        results.add(this.findViewById(R.id.res4))
        results.add(this.findViewById(R.id.res5))
        results.add(this.findViewById(R.id.res6))

        newGame()

        println(nums.toString())

        this.findViewById<Button>(R.id.submit).setOnClickListener {
            val result = this.check()
            val resultView = this.findViewById<TextView>(R.id.result)

            if(result){
                resultView.text = "Success! Your score: ${calculateScore()}"
            }
            else {
                resultView.text = "Try again."
            }
        }

        this.findViewById<Button>(R.id.continueButton).setOnClickListener {
            this.findViewById<TextView>(R.id.result).text = ""
        }

        this.findViewById<Button>(R.id.newGame).setOnClickListener { this.newGame() }

        this.findViewById<Button>(R.id.exit).setOnClickListener { this.finishAffinity() }
        this.findViewById<Button>(R.id.help).setOnClickListener { this.help() }
    }

    private fun newGame() {
        helpCount = 0;
        this.findViewById<TextView>(R.id.result).text = ""

        this.findViewById<Button>(R.id.newGame).isEnabled = false
        this.findViewById<Button>(R.id.continueButton).isEnabled = false

        inputs.forEach {
            it.isEnabled = true
            it.setText("")
        }

        nums.clear()

        for(i in 1..9){
            nums.add(floor(Math.random()*10).roundToInt())
        }

        results[0].text = nums.subList(0,3).sum().toString()
        results[1].text = nums.subList(3,6).sum().toString()
        results[2].text = nums.subList(6,9).sum().toString()
        results[3].text = (nums[0] + nums[3] + nums[6]).toString()
        results[4].text = (nums[1] + nums[4] + nums[7]).toString()
        results[5].text = (nums[2] + nums[5] + nums[8]).toString()

        val temp = mutableSetOf<EditText>()

        while(temp.size < 9-level){
            val input = inputs.random()
            val index = inputs.indexOf(input)
            input.setText(nums[index].toString())
            input.isEnabled = false
            temp.add(input)
        }

        start = SystemClock.elapsedRealtime()
    }

    private fun check(): Boolean {

        for(i in 0 until 9){
            if(inputs[i].text.toString() != nums[i].toString()) {
                this.findViewById<Button>(R.id.newGame).isEnabled = true
                this.findViewById<Button>(R.id.continueButton).isEnabled = true
                return false;
            }
        }
        this.findViewById<Button>(R.id.newGame).isEnabled = true
        elapsed = SystemClock.elapsedRealtime() - start
        return true
    }

    private fun help() {
        val input = inputs.find {it.isEnabled} ?: return
        val index = inputs.indexOf(input)
        input?.setText(nums[index].toString())
        input?.isEnabled = false
        helpCount++
    }

    private fun calculateScore(): Long {
        return level * 100L - helpCount * 10L - elapsed/1000
    }
}