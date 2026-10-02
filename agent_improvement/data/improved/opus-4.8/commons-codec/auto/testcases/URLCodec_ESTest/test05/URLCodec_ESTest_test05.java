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
     * Verifies that {@link URLCodec#encode(Object)} rejects an unsupported argument type.
     * The object overload only accepts {@code byte[]} or {@code String}; passing any other
     * type (here a {@link BitSet}) must raise an exception originating from URLCodec.
     */
    @Test(timeout = 4000)
    public void encodeUnsupportedObjectTypeThrowsException() throws Throwable {
        URLCodec urlCodec = new URLCodec();
        BitSet unsupportedInput = BitSet.valueOf(new long[2]);

        try {
            urlCodec.encode((Object) unsupportedInput);
            fail("Expected an exception: a BitSet cannot be URL encoded");
        } catch (Exception e) {
            // Message: "Objects of type java.util.BitSet cannot be URL encoded"
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
