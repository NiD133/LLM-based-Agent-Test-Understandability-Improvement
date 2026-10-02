package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test02 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding an empty String, encoding the result, then decoding again
     * returns the same empty byte array instance — demonstrating that BinaryCodec reuses
     * its internal EMPTY_BYTE_ARRAY singleton for all empty inputs.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        // Decoding an empty String yields the shared empty byte[] singleton
        Object emptyByteArray = codec.decode((Object) "");

        // Encoding an empty byte[] yields the shared empty char[] singleton
        Object emptyCharArray = codec.encode(emptyByteArray);

        // Decoding an empty char[] yields the same empty byte[] singleton as before
        Object redecodedByteArray = codec.decode(emptyCharArray);

        assertSame(redecodedByteArray, emptyByteArray);
    }
}
