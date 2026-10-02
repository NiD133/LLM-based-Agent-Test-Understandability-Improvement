package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test17 extends PercentCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding then decoding a byte array via the Object-based
     * {@link PercentCodec#encode(Object)} / {@link PercentCodec#decode(Object)}
     * round-trips the data, and that encoding produces a new array rather than
     * returning the input unchanged.
     *
     * <p>The codec is configured so that the NUL byte (0) is always encoded.
     * Encoding the all-zero input therefore expands each byte into the escape
     * sequence "%00", so the encoded result is a distinct, larger array.</p>
     */
    @Test(timeout = 4000)
    public void encodeThenDecodeRoundTripsAndReturnsNewArray() throws Throwable {
        // NUL is registered as an "always encode" character, so zero bytes get escaped.
        byte[] allZeroBytes = new byte[5];
        PercentCodec percentCodec = new PercentCodec(allZeroBytes, false);

        Object encoded = percentCodec.encode((Object) allZeroBytes);
        Object decoded = percentCodec.decode(encoded);

        // Decoding the encoded form yields a (non-null) array again.
        assertNotNull(decoded);
        // Because the zero bytes were escaped, encoding returned a brand-new array.
        assertNotSame(allZeroBytes, encoded);
    }
}
