import audio.AudioLine
import frontend.Frontend
import javax.sound.sampled.*
import kotlinx.coroutines.*
import kotlinx.coroutines.coroutineScope
import synthesizer.IntervalGenerator
import synthesizer.ToneGenerator

public const val sampleRate: Int = 44100
public const val bitDepth: Int = 16
public val audioFormat: AudioFormat = AudioFormat(sampleRate.toFloat(), 16, 1, true, true)

public enum class Waveform {
    SINE,
    SAWTOOTH,
    SQUARE,
    TRIANGLE
}

suspend fun main() = coroutineScope {
    println("Kotlin Synth v0.1")

    launch {
        val frontend: Frontend = Frontend()
        frontend.buildUI()
    }

    launch { play() }
}

suspend fun play() = coroutineScope {
    val audioOut = AudioLine().getLine()

    val toneGenerator: ToneGenerator = ToneGenerator()

    audioOut.open(audioFormat)
    audioOut.start()

    val g3: ByteArray = toneGenerator.generateTone(0.4, 49.0, 5000, Waveform.SINE)
    val b2: ByteArray = toneGenerator.generateTone(0.25, 123.47, 1000, Waveform.SINE)
    val d4: ByteArray = toneGenerator.generateTone(0.25, 293.66, 700, Waveform.SINE)
    val high: ByteArray = toneGenerator.generateTone(0.05, 10000.00, 200, Waveform.SINE)

    // AD HOC SEQUENCER

    val numBeats: Int = 5
    val measures: Int = 20
    var measure: Int = 1
    var beat: Int = 1

    while (measure <= measures) {
        if (beat > numBeats) {
            beat = 1
            measure += 1
        }

        when (beat) {
            1, 2 -> audioOut.write(g3, 0, g3.size)
            3 -> audioOut.write(b2, 0, b2.size)
            4 -> audioOut.write(high, 0, high.size)
            else -> audioOut.write(d4, 0, d4.size)
        }

        audioOut.drain()
        beat += 1
    }

    val intervalGenerator: IntervalGenerator = IntervalGenerator()

    // a minor triad
    val someInterval: ByteArray =
            intervalGenerator.generateInterval(30.0, 110.0, 261.63, 629.25, 1000, Waveform.SINE)

    audioOut.write(someInterval, 0, someInterval.size)

    //        val testTone: ByteArray = toneGenerator.generateToneWithOffset(5.0, 49.0, 5000,
    // Waveform.SINE)

    //    audioOut.write(testTone, 0, testTone.size)

    audioOut.flush()

    audioOut.stop()
    audioOut.close()
}

// Play an audio file
//    val player: AudioFilePlayer = AudioFilePlayer()
//    player.play()
