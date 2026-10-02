package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test15 extends PercentCodec_ESTest_scaffolding {

    /**
     * When none of the input bytes need to be percent-encoded, {@link PercentCodec#encode(Object)}
     * returns the very same byte[] instance it was given (no copy is made).
     *
     * Here the codec is configured with no "always encode" characters and the input is all zero
     * bytes, which are plain US-ASCII and therefore left untouched.
     */
    @Test(timeout = 4000)
    public void encodeReturnsSameArrayWhenNothingNeedsEncoding() throws Throwable {
        byte[] noAlwaysEncodeChars = new byte[0];
        PercentCodec percentCodec = new PercentCodec(noAlwaysEncodeChars, false);

        byte[] inputBytes = new byte[5];
        Object encoded = percentCodec.encode((Object) inputBytes);

        assertNotNull(encoded);
        assertSame(inputBytes, encoded);
    }
}
