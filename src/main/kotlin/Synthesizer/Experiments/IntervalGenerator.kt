package synthesizer

import sampleRate

class IntervalGenerator {
    public fun generateInterval(
        duration: Double,
        frequency1: Double,
        frequency2: Double,
        frequency3: Double,
        amplitude: Int,
        waveform: Waveform
    ): ByteArray {
        val oscillator = Oscillator(sampleRate, waveform, frequency1)
        val oscillator2 = Oscillator(sampleRate, waveform, frequency2)
        val oscillator3 = Oscillator(sampleRate, waveform, frequency3)

        val envelope = Envelope(44100)
        envelope.noteOn()

        val filter = Filter(sampleRate)

        val buffer = ByteArray((duration * sampleRate).toInt() * 2)

        for (i in buffer.indices step 2) {
            val rawSample1: Double = oscillator.getSample()
            val rawSample2: Double = oscillator2.getSample()
            val rawSample3: Double = oscillator3.getSample()
            val envelopeSample: Double = envelope.getSample()

            val finalSample: Int = ((rawSample1 + rawSample2 + rawSample3) * amplitude * envelopeSample).toInt()

            // shr 8 shifts the sample 8 bits to the right and records the
            // more significant 8 bits first (Big Endian)
            buffer[i] = (finalSample shr 8).toByte()
            buffer[i + 1] = finalSample.toByte()
        }

        return buffer
    }
}
