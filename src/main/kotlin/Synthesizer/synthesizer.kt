package Synthesizer

import Synthesizer.Oscillator
import Synthesizer.EnvelopeGenerator
import Synthesizer.Filter

class Synthesizer(private val sampleRate: Int) {
    private val oscillator = Oscillator(sampleRate, Oscillator.Waveform.SINE)
    private val filter = Filter(sampleRate)
    private val envelopeGenerator = EnvelopeGenerator(sampleRate)

    fun setCutoffFrequency(cutoffFrequency: Double) {
        filter.setCutoffFrequency(cutoffFrequency)
    }

    fun setResonance(resonance: Double) {
        filter.setResonance(resonance)
    }

    fun setAttackTime(attackTime: Double) {
        envelopeGenerator.setAttackTime(attackTime)
    }

    fun setDecayTime(decayTime: Double) {
        envelopeGenerator.setDecayTime(decayTime)
    }

    fun setSustainLevel(sustainLevel: Double) {
        envelopeGenerator.setSustainLevel(sustainLevel)
    }

    fun setReleaseTime(releaseTime: Double) {
        envelopeGenerator.setReleaseTime(releaseTime)
    }

    fun noteOn() {
        envelopeGenerator.noteOn()
    }

    fun noteOff() {
        envelopeGenerator.noteOff()
    }

    fun getSample(): Double {
//        return filter.apply(oscillator.getSample() * envelopeGenerator.getSample())
        return oscillator.getSample()
    }
}