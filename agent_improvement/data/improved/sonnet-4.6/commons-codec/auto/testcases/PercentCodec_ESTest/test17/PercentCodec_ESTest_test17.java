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

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // Configure a codec that always percent-encodes null bytes (byte value 0), without '+' for spaces
        byte[] nullBytes = new byte[5]; // five null bytes (0x00)
        PercentCodec percentCodec = new PercentCodec(nullBytes, false);

        // Encode: each null byte becomes "%00", so a new byte array is allocated
        Object encodedResult = percentCodec.encode((Object) nullBytes);

        // Decode: percent-encoded bytes should decode back to a valid byte array
        Object decodedResult = percentCodec.decode(encodedResult);

        // The decoded result must exist
        assertNotNull(decodedResult);
        // Encoding produced a new array, not the original reference
        assertNotSame(nullBytes, encodedResult);
    }
}
