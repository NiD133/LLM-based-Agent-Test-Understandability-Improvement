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
public class Base16_ESTest_test02 extends Base16_ESTest_scaffolding {

    /**
     * Under {@link CodecPolicy#STRICT}, asking Base16 to finish decoding while a
     * half-decoded character is still pending must fail.
     *
     * <p>The decode call below is told the stream has ended (a negative length
     * signals end-of-input), but the context still holds a non-zero
     * {@code ibitWorkArea}, meaning a single trailing Base16 character was left
     * over with no partner to form a complete byte. Strict decoding rejects this
     * with an {@link IllegalArgumentException}.</p>
     */
    @Test(timeout = 4000)
    public void decodeWithStrictPolicyRejectsDanglingTrailingCharacter() throws Throwable {
        // Build a strict-decoding Base16 codec.
        Base16 base16 = Base16.builder()
                .setDecodingPolicy(CodecPolicy.STRICT)
                .get();

        // Simulate a context that already holds an unfinished (half) byte.
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.ibitWorkArea = -3481; // any non-zero value marks a pending trailing character

        // A negative length tells decode() the input has ended.
        byte[] data = new byte[5];
        int offset = 31;
        int negativeLengthSignallingEof = -1214;

        try {
            base16.decode(data, offset, negativeLengthSignallingEof, context);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Strict decoding: Last encoded character is a valid Base 16 alphabet
            // character but not a possible encoding. Decoding requires at least two
            // characters to create one byte.
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
