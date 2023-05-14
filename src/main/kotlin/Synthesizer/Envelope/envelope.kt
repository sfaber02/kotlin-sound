package synthesizer

class EnvelopeGenerator(private val sampleRate: Int) {
    private var attackTime = 0.1
    private var decayTime = 0.1
    private var sustainLevel = 0.9
    private var releaseTime = 0.5
    private var state = State.IDLE
    private var phase = 0.0
    private var phaseIncrement = 0.0
    private var value = 0.0

    enum class State {
        IDLE,
        ATTACK,
        DECAY,
        SUSTAIN,
        RELEASE
    }

    fun setAttackTime(attackTime: Double) {
        this.attackTime = attackTime
    }

    fun setDecayTime(decayTime: Double) {
        this.decayTime = decayTime
    }

    fun setSustainLevel(sustainLevel: Double) {
        this.sustainLevel = sustainLevel
    }

    fun setReleaseTime(releaseTime: Double) {
        this.releaseTime = releaseTime
    }

    fun noteOn() {
        phaseIncrement = 1.0 / (attackTime * sampleRate)
        state = State.ATTACK
    }

    fun noteOff() {
        phaseIncrement = -value / (releaseTime * sampleRate)
        state = State.RELEASE
    }

    fun getSample(): Double {
        return when (state) {
            State.IDLE -> 0.0
            State.ATTACK -> {
                value += phaseIncrement
                if (value >= 1.0) {
                    phaseIncrement = -((1 - sustainLevel) * phaseIncrement) / (decayTime * sampleRate)
                    state = State.DECAY
                    value = 1.0
                }
                value
            }
            State.DECAY -> {
                value += phaseIncrement
                if (value <= sustainLevel) {
                    state = State.SUSTAIN
                    value = sustainLevel
                }
                value
            }
            State.SUSTAIN -> sustainLevel
            State.RELEASE -> {
                value += phaseIncrement
                if (value <= 0.0) {
                    state = State.IDLE
                    value = 0.0
                }
                value
            }
        }
    }
}