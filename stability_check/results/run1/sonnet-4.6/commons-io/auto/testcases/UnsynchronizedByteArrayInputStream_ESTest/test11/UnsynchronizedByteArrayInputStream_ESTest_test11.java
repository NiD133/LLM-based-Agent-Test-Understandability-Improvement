package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test11 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * When an empty byte array is given with an offset beyond its bounds,
     * available() should return 0 because there are no bytes to read.
     */
    @Test(timeout = 4000)
    public void test_availableReturnsZero_whenOffsetExceedsEmptyArrayLength() throws Throwable {
        byte[] emptyData = new byte[0];
        int offsetBeyondEnd = 1;
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(emptyData, offsetBeyondEnd);

        int bytesAvailable = stream.available();

        assertEquals(0, bytesAvailable);
    }
}
