package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test19 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that available() returns 0 when the stream offset is positioned
     * at the very end of a 1-byte array (offset == data.length), leaving no
     * bytes left to read.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // A 1-byte array: valid indices are [0], so offset 1 is past the last byte
        byte[] singleByteArray = new byte[1];
        int offsetAtEnd = 1;
        int length = 1;

        // With offset == data.length, the effective read position is clamped to the
        // end of the array, so the stream has no bytes available to read
        UnsynchronizedByteArrayInputStream streamAtEnd =
                new UnsynchronizedByteArrayInputStream(singleByteArray, offsetAtEnd, length);

        int bytesAvailable = streamAtEnd.available();

        assertEquals(0, bytesAvailable);
    }
}
