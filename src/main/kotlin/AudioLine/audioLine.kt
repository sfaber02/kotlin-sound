package audio

import audioFormat


import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

class AudioLine () {
    private val dataLineInfo = DataLine.Info(SourceDataLine::class.java, audioFormat)
    private val line = AudioSystem.getLine(dataLineInfo) as SourceDataLine

    public fun getLine(): SourceDataLine {
        return line
    }
}