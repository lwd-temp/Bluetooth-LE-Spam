package de.simon.dankelmann.bluetoothlespam.Helpers

import java.util.Arrays

class ByteHelpers {

    companion object {
        fun trim(bytes: ByteArray): ByteArray {
            var i = bytes.size - 1
            while (i >= 0 && bytes[i].toInt() == 0) {
                --i
            }
            return Arrays.copyOf(bytes, i + 1)
        }
    }


}