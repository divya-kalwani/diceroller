package com.example.diceroller

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var diceText: TextToSpeech
    private lateinit var diceImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rollButton: Button = findViewById(R.id.roll_button)
        val speakMuteImageButton: ImageButton = findViewById(R.id.speak_mute_image_button)
        diceImage = findViewById(R.id.dice_image)
        diceText = TextToSpeech(this, this)
        speakMuteImageButton.setTag(R.id.speakMuteTag,"speak")
        speakMuteImageButton.setOnClickListener {
            speakMute(speakMuteImageButton)
        }
        rollButton.setOnClickListener {
            rollDice(speakMuteImageButton)
        }
    }

    private fun speakMute(speakMuteImageButton: ImageButton) {
        val tag: String = speakMuteImageButton.getTag(R.id.speakMuteTag).toString()
        if(tag == "speak") {
            speakMuteImageButton.setTag(R.id.speakMuteTag,"mute")
            speakMuteImageButton.setImageResource(R.drawable.ic_mute)
        } else{
            speakMuteImageButton.setTag(R.id.speakMuteTag,"speak")
            speakMuteImageButton.setImageResource(R.drawable.ic_speak)
        }

    }

    private fun rollDice(speakMuteImageButton: ImageButton) {
        val randomInt: Int = Random.nextInt(6) + 1
        val tag: String = speakMuteImageButton.getTag(R.id.speakMuteTag).toString()
        if(tag == "speak") {
            diceText.speak(randomInt.toString(), TextToSpeech.QUEUE_FLUSH, null,"")
        }
        val newDiceImage = when(randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.empty_dice
        }
        diceImage.setImageResource(newDiceImage)
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = diceText!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }

    public override fun onDestroy() {
        // Shutdown TTS
        diceText.stop()
        diceText.shutdown()
        super.onDestroy()
    }
}
