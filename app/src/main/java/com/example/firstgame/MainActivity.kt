package com.example.firstgame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    //0 - O
    //1 - X
    //2 - Empty

    private val xImage = R.drawable.x
    private val oImage = R.drawable.o

    private  lateinit var texts:TextView
    private var gameSlate = intArrayOf(2,2,2,2,2,2,2,2,2)
    private var activePlayer = 0
    private val winningPositions = arrayOf(intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
        intArrayOf(0,3,6), intArrayOf(1,4,7),intArrayOf(2,5,8), intArrayOf(0,4,8),intArrayOf(2,4,6)
    )
    private var gameRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun resetGame(view: View){
        gameRunning = true
        gameSlate = intArrayOf(2,2,2,2,2,2,2,2,2)
        setContentView(R.layout.activity_main)
    }

    fun playerTap(view: View) {
        val img:ImageView = view as ImageView
        val imgNumber = img.tag.toString().toInt()

        texts = findViewById(R.id.textView2)

        if(gameSlate[imgNumber]==2 && gameRunning){

            gameSlate[imgNumber] = activePlayer

            if(activePlayer==0){
                img.setImageResource(oImage)
                activePlayer = 1
                texts.text = getString(R.string.x_plays_msg)
            }
            else{
                img.setImageResource(xImage)
                activePlayer = 0
                texts.text = getString(R.string.o_plays_msg)
            }

            if(checkGameStatus()){
                texts.text = if(activePlayer==0) "X won" else "O won"
                Toast.makeText(this,texts.text,Toast.LENGTH_SHORT).show()
                gameRunning = false

            }
        }
    }

    private fun checkGameStatus():Boolean{
        for(i in winningPositions){
            val (a,b,c) = Triple(gameSlate[i[0]],gameSlate[i[1]],gameSlate[i[2]])
            if((a==b && b==c)&& a!=2) return true
        }
        return false
    }
}