package Synthesizer

class Filter(private val sampleRate: Int) {
    private var cutoffFrequency = 1000.0
    private var resonance = 0.5
    private var z1 = 0.0
    private var z2 = 0.0

    fun setCutoffFrequency(cutoffFrequency: Double) {
        this.cutoffFrequency = cutoffFrequency
    }

    fun setResonance(resonance: Double) {
        this.resonance = resonance
    }

    fun apply(input: Double): Double {
        val k = 2 * Math.PI * cutoffFrequency / sampleRate
        val r = 1.0 - k / (2 * resonance)

        val output = (1 - r*r) * input - 2*r*z1 + r*r*z2
        z2 = z1
        z1 = output

        return output
    }
}