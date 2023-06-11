package synthesizer

import sampleRate

// Generates a ByteArray of audio samples
class ToneGenerator {

    public fun generateTone(
        duration: Double,
        frequency: Double,
        amplitude: Int,
        waveform: Waveform
    ): ByteArray {
        val oscillator: Oscillator = Oscillator(sampleRate, waveform, frequency)
        val envelope: Envelope = Envelope(44100)

        envelope.noteOn()

        // 16 bit sound, bytes are only 8 bits so each sample will need to be serialized
        // to 2 indices.  Create an array twice the size of the duration * sampleRate
        val buffer = ByteArray((duration * sampleRate).toInt() * 2)

        for (i in buffer.indices step 2) {
            val rawSample: Double = oscillator.getSample()
            val envelopeSample: Double = envelope.getSample()

            val finalSample: Int = (rawSample * envelopeSample * amplitude).toInt()

            // shr 8 shifts the sample 8 bits to the right and records the
            // more significant 8 bits first (Big Endian)
            buffer[i] = (finalSample shr 8).toByte()
            buffer[i + 1] = finalSample.toByte()
        }

        return buffer
    }

    public fun generateToneWithOffset(
        duration: Double,
        frequency: Double,
        amplitude: Int,
        waveform: Waveform
    ): ByteArray {
        val oscillator: ExperimentalOscillator = ExperimentalOscillator(sampleRate, waveform, frequency, 0.0000002)
//        val envelopeGenerator: EnvelopeGenerator = EnvelopeGenerator(44100)

//        envelopeGenerator.noteOn()

        // 16 bit sound, bytes are only 8 bits so each sample will need to be serialized
        // to 2 indices.  Create an array twice the size of the duration * sampleRate
        val buffer = ByteArray((duration * sampleRate).toInt() * 2)

        return getSamples(oscillator, amplitude, buffer)
    }

    private fun getSamples(oscillator: ExperimentalOscillator, amplitude: Int, buffer: ByteArray): ByteArray {
        for (i in buffer.indices step 2) {
            val rawSample: Double = oscillator.getSample()
            //            val envelopeSample: Double = envelopeGenerator.getSample()

            val finalSample: Int = (rawSample * amplitude).toInt()

            // shr 8 shifts the sample 8 bits to the right and records the
            // more significant 8 bits first (Big Endian)
            buffer[i] = (finalSample shr 8).toByte()
            buffer[i + 1] = finalSample.toByte()
        }

        return buffer
    }
}
