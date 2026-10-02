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
     * Verifies that available() returns 0 when the stream is constructed from an empty byte array,
     * even when a non-zero offset is specified. Because the underlying data is empty, there are
     * no bytes to read regardless of the offset value.
     */
    @Test(timeout = 4000)
    public void test_availableReturnsZero_whenConstructedWithEmptyArrayAndNonZeroOffset() throws Throwable {
        byte[] emptyData = new byte[0];
        int offset = 1;

        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(emptyData, offset);

        assertEquals(0, stream.available());
    }
}
