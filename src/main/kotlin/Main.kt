import synthesizer.ToneGenerator

import audio.AudioFilePlayer

import audio.AudioLine
import javax.sound.sampled.*

public const val sampleRate: Int = 44100
public const val bitDepth: Int  = 16
public val audioFormat: AudioFormat = AudioFormat(
    sampleRate.toFloat(),
    16,
    1,
    true,
    true)

public enum class Waveform {
    SINE,
    SAWTOOTH,
    SQUARE,
    TRIANGLE
}
fun main() {
    println ("Kotlin Synth v0.1")

    val audioOut = AudioLine().getLine()
    val toneGenerator: ToneGenerator = ToneGenerator()

//    val player: AudioFilePlayer = AudioFilePlayer()
//    player.play()

    audioOut.open(audioFormat)
    audioOut.start()

    val g3: ByteArray = toneGenerator.generateTone(0.5, 49.0, 2000, Waveform.SINE)
    val b2: ByteArray = toneGenerator.generateTone(0.25, 123.47, 1000, Waveform.SINE)
    val d4: ByteArray = toneGenerator.generateTone(0.25, 293.66, 700, Waveform.SINE)


    val numBeats: Int = 4
    val measures: Int = 16
    var measure: Int = 1
    var beat:Int = 1

    while(measure <= measures) {
        println ("$beat/$measure")
        if (beat > numBeats) {
            beat = 1
            measure += 1
        }

        when (beat) {
            1, 2 -> audioOut.write(g3, 0, g3.size)
            3 -> audioOut.write(b2, 0, b2.size)
            else -> audioOut.write(d4, 0, d4.size)
        }


        audioOut.drain()
        beat += 1
    }

    audioOut.stop()
    audioOut.close()
}