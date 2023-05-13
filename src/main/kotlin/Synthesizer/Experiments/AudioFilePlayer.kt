package audio



import javax.sound.sampled.*


import javax.sound.sampled.Clip
import java.io.File

class AudioFilePlayer () {

    val audioFile = File("/Users/shawn/dev/synth-test/src/main/kotlin/Synthesizer/Experiments/22022.wav")

    public fun play() {
        if (!audioFile.exists()) {
            println("Audio file does not exist")
            return
        }

        val audioInputStream = AudioSystem.getAudioInputStream(audioFile)
        val clip = AudioSystem.getClip()
        clip.open(audioInputStream)

        clip.start()

        // Wait for clip to finish playing
        while (!clip.isRunning) {
            Thread.sleep(10)
        }

        while (clip.isRunning) {
            Thread.sleep(10)
        }

        clip.close()
    }




}