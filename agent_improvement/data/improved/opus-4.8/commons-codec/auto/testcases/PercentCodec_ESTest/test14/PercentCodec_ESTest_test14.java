package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test14 extends PercentCodec_ESTest_scaffolding {

    /**
     * When none of the input bytes need encoding, {@link PercentCodec#encode(byte[])}
     * skips the copy and returns the very same array instance it was given.
     *
     * Here the carriage-return byte (13) is a plain US-ASCII character that is not in
     * the "always encode" set, so there is nothing to escape and the original array
     * is returned unchanged.
     */
    @Test(timeout = 4000)
    public void encodeReturnsSameArrayWhenNothingNeedsEncoding() throws Throwable {
        // "Always encode" set of five NUL bytes; encode spaces as '+'.
        byte[] alwaysEncodeChars = new byte[5];
        PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, true);

        // A single carriage-return byte: ASCII and not flagged for encoding.
        byte[] input = new byte[1];
        input[0] = (byte) 13;

        byte[] encoded = percentCodec.encode(input);

        assertNotNull(encoded);
        assertSame(input, encoded);
    }
}
