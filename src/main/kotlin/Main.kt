import synthesizer.ToneGenerator

import audio.AudioLine
import javax.sound.sampled.*

public const val sampleRate: Int = 44100
public const val bitDepth: Int  = 16
public val audioFormat: AudioFormat = AudioFormat(sampleRate.toFloat(), 16, 1, true, true)

fun main() {
    println ("Kotlin Synth v0.1")

    val audioOut = AudioLine().getLine()
    val toneGenerator: ToneGenerator = ToneGenerator()


    val duration = 1.0 // in seconds
    val frequency = 20.0 // in Hz

    audioOut.open(audioFormat)
    audioOut.start()

    val tone1: ByteArray = toneGenerator.generateTone(1.0, 200.0)

    audioOut.write(tone1, 0, tone1.size)

    audioOut.drain()
    audioOut.stop()

//    audioOut.write(buffer2, 0, buffer2.size)
//    audioOut.drain()
//    audioOut.stop()

    audioOut.close()
}