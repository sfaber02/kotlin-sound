package synthesizer

import sampleRate


class ToneGenerator() {
    private val synthesizer = Synthesizer(sampleRate)

    public fun generateTone (
        duration: Double,
        frequency: Double): ByteArray {
        val buffer = ByteArray((duration * sampleRate).toInt())

        for (i in buffer.indices step 2) {
            val rawSample: Double = synthesizer.getSample()
            println ("raw sample = $rawSample")
            val sample: Int = (rawSample * 1500).toInt()
            println("sample = $sample byte=${sample.toByte()}")
            buffer[i] = sample.toByte()
            buffer[i + 1] = (sample shr 8).toByte()
        }

        return buffer
    }
}

