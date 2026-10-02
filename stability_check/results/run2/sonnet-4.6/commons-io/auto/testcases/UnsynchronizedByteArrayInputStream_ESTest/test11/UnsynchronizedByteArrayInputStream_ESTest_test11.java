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
     * Verifies that available() returns 0 when the stream is constructed with an empty
     * byte array and an offset that exceeds the array length. Since there is no data and
     * the offset is clamped to the array boundary, no bytes should be available to read.
     */
    @Test(timeout = 4000)
    public void test_availableReturnsZero_whenEmptyArrayWithOutOfBoundsOffset() throws Throwable {
        byte[] emptyData = new byte[0];
        int offsetBeyondEnd = 1;

        UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(emptyData, offsetBeyondEnd);

        assertEquals(0, stream.available());
    }
}
