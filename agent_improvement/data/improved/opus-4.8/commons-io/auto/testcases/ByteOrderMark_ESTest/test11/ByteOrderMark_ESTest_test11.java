package org.apache.commons.io;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test11 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * The constructor must reject an empty bytes array, since a BOM requires at
     * least one byte. It should fail with an IllegalArgumentException ("No bytes
     * specified") even when a valid charset name is supplied.
     */
    @Test(timeout = 4000)
    public void constructorWithEmptyBytesThrowsIllegalArgumentException() throws Throwable {
        String charsetName = "(\"09%p_HU|M5jy";
        int[] emptyBytes = new int[0];

        try {
            new ByteOrderMark(charsetName, emptyBytes);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown by ByteOrderMark because no BOM bytes were provided.
            verifyException("org.apache.commons.io.ByteOrderMark", e);
        }
    }
}
