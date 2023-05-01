public class FloatingPointRegisters {

    public short FR0;
    public short FR1;

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

    public static short floatToShort(float value) {
        int floatBits = Float.floatToIntBits(value);
        int sign = (floatBits >>> 16) & 0x8000;
        int exponent = ((floatBits >>> 23) & 0xFF) - 127;
        int mantissa = floatBits & 0x007FFFFF;
        if (exponent == 128) {
            if (mantissa == 0) {
                // Infinity
                return (short) (sign | 0x7F00);
            } else {
                // NaN
                return (short) (sign | 0x7F00 | (mantissa >>> 15));
            }
        }
        if (exponent < -63) {
            if (exponent == -127 && mantissa == 0) {
                // Zero
                return (short) sign;
            } else {
                // Subnormal or zero
                return (short) (sign | (((mantissa | 0x00800000) >>> (1 - exponent)) >>> 15));
            }
        }
        if (exponent > 64) {
            // Overflow
            return (short) (sign | 0x7F00);
        }
        // Normalized
        return (short) (sign | ((exponent + 63) << 8) | (mantissa >>> 15));
    }

    public static float shortToFloat(short value) {
        int sign = (value >>> 15) & 0x1;
        int exponent = (value >>> 8) & 0x7F;
        int mantissa = value & 0xFF;

        if (exponent == 0) {
            if (mantissa == 0) {
                // Zero
                return Float.intBitsToFloat(sign << 31);
            } else {
                // Subnormal
                while ((mantissa & 0x00000100) == 0) {
                    mantissa <<= 1;
                    exponent--;
                }
                exponent++;
                mantissa &= 0x000000FF;
            }
        } else if (exponent == 127) {
            if (mantissa == 0) {
                // Infinity
                return Float.intBitsToFloat((sign << 31) | 0x7F800000);
            } else {
                // NaN
                return Float.intBitsToFloat((sign << 31) | 0x7F800000 | (mantissa << 15));
            }
        }

        exponent = exponent + (127 - 63);
        mantissa = mantissa << 15;

        int floatBits = (sign << 31) | (exponent << 23) | mantissa;
        return Float.intBitsToFloat(floatBits);
    }
}