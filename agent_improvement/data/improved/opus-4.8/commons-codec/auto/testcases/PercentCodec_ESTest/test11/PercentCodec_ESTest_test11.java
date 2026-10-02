package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test11 extends PercentCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding then decoding a byte that is configured as
     * "always encode" round-trips back to the original value.
     *
     * <p>The codec is configured to always encode the NUL byte (0). Encoding the
     * single NUL byte therefore produces its Percent-Encoding "%00" (bytes
     * {@code '%', '0', '0'} == {@code 37, 48, 48}), and decoding that sequence
     * restores the original NUL byte.</p>
     */
    @Test(timeout = 4000)
    public void encodeThenDecodeNulByte_roundTripsToOriginal() throws Throwable {
        // The NUL byte (0) is both the input to encode and the character marked
        // as "always encode" in the codec configuration.
        byte[] inputBytes = new byte[] { (byte) 0 };
        PercentCodec percentCodec = new PercentCodec(inputBytes, false);

        byte[] encodedBytes = percentCodec.encode(inputBytes);
        byte[] decodedBytes = percentCodec.decode(encodedBytes);

        // NUL is Percent-Encoded as the ASCII sequence "%00".
        byte[] expectedEncoded = new byte[] { (byte) '%', (byte) '0', (byte) '0' };
        assertArrayEquals(expectedEncoded, encodedBytes);

        // Decoding "%00" must restore the original NUL byte.
        assertArrayEquals(new byte[] { (byte) 0 }, decodedBytes);
    }
}
