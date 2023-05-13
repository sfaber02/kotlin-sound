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

    val tone1: ByteArray = toneGenerator.generateTone(0.25, 200.0)
    val tone2: ByteArray = toneGenerator.generateTone(0.5, 440.0)

    for (i in 0..100) {
        if (i % 2 == 0) {
            println(i)
            audioOut.write(tone1, 0, tone1.size)
        } else  {
            audioOut.write(tone2, 0, tone2.size)
        }
        audioOut.drain()
    }

    audioOut.stop()
    audioOut.close()
}