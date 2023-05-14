package synthesizer

import sampleRate
import Waveform

class IntervalGenerator() {
    public fun generateInterval(
        duration: Double,
        frequency1: Double,
        frequency2: Double,
        amplitude: Int,
        waveform: Waveform): ByteArray {

        val oscillator: Oscillator = Oscillator(sampleRate, waveform, frequency1)
        val oscillator2: Oscillator = Oscillator(sampleRate, waveform, frequency2)


        val buffer = ByteArray((duration * sampleRate).toInt() * 2)
//        val buffer2 = ByteArray((duration * sampleRate).toInt() * 2)
//        val mergedBuffer = ByteArray((duration * sampleRate).toInt() * 2)


        for (i in buffer.indices step 2) {
            val rawSample1: Double = oscillator.getSample()
            val rawSample2: Double = oscillator2.getSample()

            if (i % 4000 == 0) {
                println("sample1 = $rawSample1 sample2 = $rawSample2")
            }


            val finalSample: Int = (rawSample1 + rawSample2 * amplitude).toInt()


            // shr 8 shifts the sample 8 bits to the right and records the
            // more significant 8 bits first (Big Endian)
            buffer[i] = (finalSample shr 8).toByte()
            buffer[i + 1] = finalSample.toByte()
        }

        return buffer

    }
}