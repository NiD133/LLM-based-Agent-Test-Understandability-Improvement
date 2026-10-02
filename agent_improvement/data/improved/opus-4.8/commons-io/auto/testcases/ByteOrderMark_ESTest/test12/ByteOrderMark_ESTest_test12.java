package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test12 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * The constructor must reject an empty charset name by throwing an
     * IllegalArgumentException ("No charsetName specified"), even when valid
     * BOM bytes are supplied.
     */
    @Test(timeout = 4000)
    public void constructorWithEmptyCharsetNameThrowsIllegalArgumentException() throws Throwable {
        int[] bomBytes = new int[1];

        try {
            new ByteOrderMark("", bomBytes);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The empty charset name triggers the "No charsetName specified" guard.
            verifyException("org.apache.commons.io.ByteOrderMark", e);
        }
    }
}
