package synthesizer

import Waveform

import synthesizer.Oscillator
import synthesizer.EnvelopeGenerator
import synthesizer.Filter

class Synthesizer(private val sampleRate: Int) {
//    private val oscillator = Oscillator(sampleRate, Waveform.SINE)
//    private val filter = Filter(sampleRate)
//    private val envelopeGenerator = EnvelopeGenerator(sampleRate)
//
//    fun getSample(): Double {
////        return filter.apply(oscillator.getSample() * envelopeGenerator.getSample())
//        return oscillator.getSample()
//    }
}