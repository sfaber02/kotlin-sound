import Synthesizer.Synthesizer
import javax.sound.sampled.*


fun main() {
    println ("Hello World")

    val sampleRate = 44100
    val duration = 1.0 // in seconds
    val frequency = 20.0 // in Hz

    val synthesizer = Synthesizer(sampleRate)
    synthesizer.setCutoffFrequency(5000.0)
    synthesizer.setResonance(0.0)
    synthesizer.setAttackTime(0.1)
    synthesizer.setDecayTime(0.1)
    synthesizer.setSustainLevel(0.8)
    synthesizer.setReleaseTime(0.2)


    val audioFormat = AudioFormat(sampleRate.toFloat(), 16, 1, true, true)
    val dataLineInfo = DataLine.Info(SourceDataLine::class.java, audioFormat)
    val line = AudioSystem.getLine(dataLineInfo) as SourceDataLine

    println (AudioSystem.getLine(dataLineInfo))
    println (line)

    line.open(audioFormat)
    line.start()

    val buffer = ByteArray((duration * sampleRate).toInt())

    for (i in buffer.indices step 2) {
        val rawSample: Double = synthesizer.getSample()
        println ("raw sample = $rawSample")
        val sample: Int = (rawSample * 1500).toInt()
        println("sample = $sample byte=${sample.toByte()}")
        buffer[i] = sample.toByte()
        buffer[i + 1] = (sample shr 8).toByte()
    }

    val buffer2: Array<Int> = Array<Int>((duration * sampleRate).toInt()) { 0 }

    for (i in buffer.indices) {
        val sample: Int = (synthesizer.getSample() * 1500).toInt()
        buffer2[i] = sample
    }

    line.write(buffer, 0, buffer.size)

    line.drain()
    line.stop()

//    line.write(buffer2, 0, buffer2.size)
//    line.drain()
//    line.stop()

    line.close()
}