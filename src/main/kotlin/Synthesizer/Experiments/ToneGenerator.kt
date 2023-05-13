package synthesizer

import sampleRate
import Waveform


class ToneGenerator() {


    public fun generateTone (
        duration: Double,
        frequency: Double): ByteArray {

        val oscillator: Oscillator = Oscillator(sampleRate, Waveform.SINE, frequency)
        val buffer = ByteArray((duration * sampleRate).toInt() * 2)

        for (i in buffer.indices step 2) {
            val rawSample: Double = oscillator.getSample()
            val sample: Int = (rawSample * 1000).toInt()
            buffer[i] = (sample shr 8).toByte()
            buffer[i + 1] = sample.toByte()
        }

        return buffer
    }
}

