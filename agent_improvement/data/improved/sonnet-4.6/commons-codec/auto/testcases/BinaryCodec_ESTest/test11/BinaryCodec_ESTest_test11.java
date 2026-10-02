package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test11 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding an empty String and then decoding the resulting empty byte array
     * both return the same shared EMPTY_BYTE_ARRAY instance, confirming that the codec
     * consistently returns a single shared constant for empty inputs.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        // Decoding an empty String produces an empty byte array (the shared EMPTY_BYTE_ARRAY constant)
        Object emptyByteArrayFromString = codec.decode((Object) "");

        // Decoding that empty byte array again returns the same shared EMPTY_BYTE_ARRAY constant
        Object emptyByteArrayFromByteArray = codec.decode(emptyByteArrayFromString);

        // Both decode calls return the exact same object reference (the shared empty byte array)
        assertSame(emptyByteArrayFromByteArray, emptyByteArrayFromString);
    }
}
