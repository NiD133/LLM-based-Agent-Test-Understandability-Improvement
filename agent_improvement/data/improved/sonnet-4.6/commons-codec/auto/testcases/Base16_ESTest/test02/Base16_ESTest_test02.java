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
     * Verifies that strict decoding rejects an incomplete hex pair at end-of-stream.
     *
     * Base16 encodes every byte as exactly two hex characters. When strict decoding
     * is active, encountering a single unpaired hex character at EOF (meaning one
     * nibble is buffered with no second nibble following) must raise an exception.
     *
     * The decode() method uses ibitWorkArea as a one-nibble buffer:
     *   - 0 means "no nibble buffered"
     *   - non-zero means "one nibble is waiting for its pair"
     * A negative length argument signals end-of-stream to decode().
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Build a Base16 decoder configured to enforce strict decoding rules.
        Base16 strictBase16 = Base16.builder()
                .setDecodingPolicy(CodecPolicy.STRICT)
                .get();

        // Prepare a decode context that already holds one buffered nibble.
        // Any non-zero ibitWorkArea value represents a pending half-byte that
        // needs a second hex character to form a complete decoded byte.
        BaseNCodec.Context contextWithPendingNibble = new BaseNCodec.Context();
        contextWithPendingNibble.ibitWorkArea = (-3481);

        // The input buffer and offset are irrelevant here: a negative length (-1214)
        // acts as an EOF signal, so decode() skips the buffer and goes straight to
        // the end-of-stream check. Finding a pending nibble in strict mode is an error.
        byte[] inputBuffer = new byte[5];
        int anyOffset = 31;
        int eofSignal = (-1214);

        // Strict decoding must reject a trailing unpaired hex character.
        try {
            strictBase16.decode(inputBuffer, anyOffset, eofSignal, contextWithPendingNibble);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Strict decoding: Last encoded character is a valid Base 16 alphabet character but not a possible encoding. Decoding requires at least two characters to create one byte.
            //
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
