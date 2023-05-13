package Synthesizer

import kotlin.math.sin

class Oscillator(private val sampleRate: Int, private val waveform: Waveform) {
    private var phase: Double = 0.0
    private val phaseIncrement: Double = frequencyToPhaseIncrement(440.0)

    enum class Waveform {
        SINE,
        SAWTOOTH,
        SQUARE,
        TRIANGLE
    }

    fun getSample(): Double {
        val value: Double = when (waveform) {
            Waveform.SINE -> sin(phase)
            Waveform.SAWTOOTH -> if (phase < Math.PI) {
                -1.0 + 2.0 * phase / Math.PI
            } else {
                1.0 - 2.0 * (phase - Math.PI) / Math.PI
            }
            Waveform.SQUARE -> if (phase < Math.PI) {
                -1.0
            } else {
                1.0
            }
            Waveform.TRIANGLE -> if (phase < Math.PI) {
                -1.0 + 2.0 * phase / Math.PI
            } else {
                1.0 - 2.0 * (phase - Math.PI) / Math.PI
            }
        }

        phase += phaseIncrement
        if (phase >= 2 * Math.PI) {
            phase -= 2 * Math.PI
        }
//        println(value)
        return value
    }

    private fun frequencyToPhaseIncrement(frequency: Double): Double {
        return 2.0 * Math.PI * frequency / sampleRate
    }
}