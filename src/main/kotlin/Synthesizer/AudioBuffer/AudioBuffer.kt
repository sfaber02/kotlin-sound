package synthesizer


// creates a circular audio buffer
class AudioBuffer(private val bufferLength: Int) {
    private var readIndex: Int = 0
    private var writeIndex: Int = 0
    // create a byte array of bufferLength * 2 (hard coded to 16bit sound)
    private var bufferSize: Int = bufferLength * 2
    private var buffer = ByteArray(bufferSize) { 0 }

    fun writeToBuffer(sample: Int) {
        buffer[writeIndex] = (sample shr 8).toByte()
        if (writeIndex == buffer.size - 1) {
            writeIndex = 0
        } else {
            writeIndex++
        }
        buffer[writeIndex] = sample.toByte()
        if (writeIndex == buffer.size - 1) {
            writeIndex = 0
        } else {
            writeIndex++
        }
    }

    fun readFromBuffer(): Byte {
        return if (readIndex + 1 > buffer.size - 1) {
            readIndex = 0
            buffer[buffer.size - 1]
        } else {
            readIndex++
            buffer[readIndex - 1]
        }
    }

    public fun getBuffer(): ByteArray{
        return buffer
    }

}