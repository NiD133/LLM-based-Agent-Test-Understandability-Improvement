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

    @Test(timeout = 4000)
    public void test_decodeWithPreloadedHalfByteContext_doesNotUseStrictDecoding() throws Throwable {
        // Lenient (non-strict) uppercase Base16 decoder
        Base16 decoder = new Base16(false);

        // Input buffer where only the last byte holds a valid Base16 character 'D'
        byte[] inputBuffer = new byte[6];
        inputBuffer[5] = (byte) 'D'; // ASCII 68, a valid uppercase hex digit

        // Simulate a decode context that already holds a partial (half-byte) state
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.ibitWorkArea = -26; // pre-loaded half-byte from a prior decode step

        // Decode starting at offset 5; only 1 byte is available despite the large length argument
        decoder.decode(inputBuffer, 5, 1767, context);

        // A decoder created with lowerCase=false and default policy must not enforce strict decoding
        assertFalse(decoder.isStrictDecoding());
    }
}
