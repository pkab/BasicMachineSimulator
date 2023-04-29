public class FloatingPointRegisters {
    private static final int EXPONENT_BIAS = 63;
    private static final int MANTISSA_MASK = 0xFF;
    private static final int EXPONENT_MASK = 0x7F80;
    private static final int SIGN_MASK = 0x8000;

    private short FR0;
    private short FR1;

    public void setFR0(float value) {
        FR0 = floatToShort(value);
    }

    public void setFR1(float value) {
        FR1 = floatToShort(value);
    }

    public float getFR0() {
        return shortToFloat(FR0);
    }

    public float getFR1() {
        return shortToFloat(FR1);
    }

    private static short floatToShort(float value) {
        int bits = Float.floatToIntBits(value);
        boolean sign = (bits & SIGN_MASK) != 0;
        int exponent = ((bits & EXPONENT_MASK) >> 7) - EXPONENT_BIAS;
        int mantissa = bits & MANTISSA_MASK;
        short result = (short) ((sign ? 0x8000 : 0) | ((exponent & 0x7F) << 8) | mantissa);
        return result;
    }

    private static float shortToFloat(short value) {
        boolean sign = (value & SIGN_MASK) != 0;
        int exponent = ((value >> 8) & 0x7F) - EXPONENT_BIAS;
        int mantissa = value & MANTISSA_MASK;
        int bits = (sign ? 0x80000000 : 0) | ((exponent & 0xFF) << 23) | (mantissa << 15);
        float result = Float.intBitsToFloat(bits);
        return result;
    }
}


/*

public class VectorProcessor {
    private Float16[] FR0;
    private Float16[] FR1;

    public VectorProcessor() {
        FR0 = new Float16[256];
        FR1 = new Float16[256];
    }

    public void vadd(int fr, int address, int length) {
        Float16[] v1 = readVector(address, length);
        Float16[] v2 = readVector(address + 1, length);
        Float16[] result = new Float16[length];
        for (int i = 0; i < length; i++) {
            result[i] = Float16.add(v1[i], v2[i]);
        }
        writeVector(fr, result);
    }

    public void vsub(int fr, int address, int length) {
        Float16[] v1 = readVector(address, length);
        Float16[] v2 = readVector(address + 1, length);
        Float16[] result = new Float16[length];
        for (int i = 0; i < length; i++) {
            result[i] = Float16.subtract(v1[i], v2[i]);
        }
        writeVector(fr, result);
    }

    public Float16[] readVector(int address, int length) {
        Float16[] vector = new Float16[length];
        for (int i = 0; i < length; i++) {
            // read two memory locations and combine them into a single 16-bit floating point number
            int word1 = readMemory(address + i);
            int word2 = readMemory(address + i + 1);
            int bits = (word1 << 8) | word2;
            boolean sign = (bits & 0x8000) != 0;
            int exponent = ((bits >> 8) & 0x7f) - 63;
            int mantissa = bits & 0xff;
            vector[i] = new Float16(sign, exponent, mantissa);
        }
        return vector;
    }

    public void writeVector(int fr, Float16[] vector) {
        for (int i = 0; i < vector.length; i++) {
            // split each floating point number into two 8-bit words and write them to memory
            int bits = ((vector[i].sign ? 1 : 0) << 15)
                    | ((vector[i].exponent + 63) << 8)
                    | vector[i].mantissa;
            int word1 =         (bits >> 8) & 0xff;
        int word2 = bits & 0xff;
        writeMemory(fr * vector.length + i, word1);
        writeMemory(fr * vector.length + i + 1, word2);
    }
}

public int readMemory(int address) {
    // TODO: implement memory read operation
}

public void writeMemory(int address, int data) {
    // TODO: implement memory write operation
}



*/