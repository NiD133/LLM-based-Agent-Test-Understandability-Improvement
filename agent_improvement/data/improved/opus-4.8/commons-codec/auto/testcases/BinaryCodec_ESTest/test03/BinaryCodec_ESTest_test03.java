package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test03 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a {@code null} object does not return {@code null}.
     * Per {@link BinaryCodec#decode(Object)}, a {@code null} argument yields an
     * empty byte array rather than {@code null} or an exception.
     */
    @Test(timeout = 4000)
    public void decodeNullObjectReturnsNonNullResult() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        Object decoded = binaryCodec.decode((Object) null);

        assertNotNull(decoded);
    }
}
