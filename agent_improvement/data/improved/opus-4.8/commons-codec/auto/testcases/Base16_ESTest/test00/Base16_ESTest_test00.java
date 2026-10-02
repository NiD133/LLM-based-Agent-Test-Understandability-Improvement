package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test00 extends Base16_ESTest_scaffolding {

    /**
     * Decodes a single hex character that completes a leftover half-byte carried
     * over from a previous {@code decode} call.
     *
     * <p>The decoding context starts with a non-zero {@code ibitWorkArea}, which
     * tells {@code decode} that half of a byte is already pending. Feeding one more
     * valid Base16 character ('D') supplies the second nibble, so the pair is
     * decoded into a full byte without error.</p>
     *
     * <p>The codec is built in lenient (non-strict) decoding mode, so the call
     * completes normally; the assertion confirms that mode.</p>
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Lenient, upper-case Base16 codec (lowerCase = false).
        Base16 base16 = new Base16(false);

        // Input buffer whose only meaningful element is the trailing 'D' (0x44 = 68)
        // located at index 5; the call below starts decoding from that index.
        byte[] input = new byte[6];
        input[5] = (byte) 'D';

        // A decoding context that already holds a pending half-byte: a non-zero
        // ibitWorkArea makes decode() treat the next character as the second nibble.
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.ibitWorkArea = (byte) (-26);

        // Decode starting at the trailing 'D'. The length (1767) intentionally
        // exceeds the available data; decode() clamps it to the remaining byte.
        int offsetOfTrailingChar = 5;
        int requestedLength = 1767;
        base16.decode(input, offsetOfTrailingChar, requestedLength, context);

        // The codec was created with the default lenient policy.
        assertFalse(base16.isStrictDecoding());
    }
}
