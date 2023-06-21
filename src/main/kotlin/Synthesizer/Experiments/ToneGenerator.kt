package synthesizer

import audioFormat
import sampleRate
import javax.sound.sampled.SourceDataLine
import java.time.Instant


// Generates a ByteArray of audio samples
class ToneGenerator {


    // generates a tone of a set length
    fun generateTone(
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

    // this doesn't work yet, the intention here is to generate a rising / falling tone
    fun generateToneWithOffset(
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

    fun generateBufferedTone(frequency: Double, amplitude: Int, waveform: Waveform, audioOut: SourceDataLine) {
        print ("generate buffer tone")
        val bufferLength: Int = 2000
        val playTimeInSeconds: Int = 60
        val buffer: AudioBuffer = AudioBuffer(bufferLength)
        val oscillator: Oscillator = Oscillator(sampleRate, waveform, frequency)
        var init: Int = 0

        audioOut.open(audioFormat)
        audioOut.start()

        print ("$buffer, $oscillator, $audioOut")


        // out put for 5 seconds
        val start = Instant.now()
        while (start.epochSecond + playTimeInSeconds > Instant.now().epochSecond) {
            val rawSample: Double = oscillator.getSample()
            buffer.writeToBuffer((rawSample * amplitude).toInt())
            if (init < bufferLength) {
                // fill the buffer before we play it
                init++
                audioOut.write(buffer.getBuffer(), 0, bufferLength)
            } else {
//                print("fkfdkf")
                audioOut.write(buffer.getBuffer(), 0, bufferLength)
                audioOut.flush()
//                print(buffer.getBuffer())
            }
        }
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
