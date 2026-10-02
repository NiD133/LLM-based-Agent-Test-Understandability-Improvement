package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test05 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding an unsupported object type (BitSet) throws an EncoderException,
     * because URLCodec only supports String and byte[] inputs.
     */
    @Test(timeout = 4000)
    public void test05_encodeUnsupportedObjectType_throwsEncoderException() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        // BitSet is not a supported type for URL encoding (only String and byte[] are supported)
        long[] zeroLongs = new long[2];
        BitSet bitSetInput = BitSet.valueOf(zeroLongs);

        try {
            urlCodec.encode((Object) bitSetInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Objects of type java.util.BitSet cannot be URL encoded
            //
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
