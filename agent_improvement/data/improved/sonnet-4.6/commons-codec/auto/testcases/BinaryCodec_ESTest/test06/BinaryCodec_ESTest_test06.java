package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test06 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that both toByteArray(null) and toAsciiBytes(emptyArray) return
     * the same shared EMPTY_BYTE_ARRAY singleton, confirming that encoding an
     * empty byte array is a no-op that preserves the empty-array identity.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        BinaryCodec codec = new BinaryCodec();

        // toByteArray(null) returns the shared EMPTY_BYTE_ARRAY constant
        byte[] emptyBytesFromNull = codec.toByteArray((String) null);

        // toAsciiBytes on an empty input also returns the same EMPTY_BYTE_ARRAY constant
        byte[] asciiEncodedEmpty = BinaryCodec.toAsciiBytes(emptyBytesFromNull);

        // Both calls return the exact same object reference
        assertSame(asciiEncodedEmpty, emptyBytesFromNull);
    }
}
