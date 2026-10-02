package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test17 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Reading all bytes from a 2-byte stream into a same-sized buffer should
     * return 2 (the number of bytes read) and leave 0 bytes available afterward.
     */
    @Test(timeout = 4000)
    public void test17_readAllBytesLeavesStreamExhausted() throws Throwable {
        byte[] twoByteBuffer = new byte[2];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(twoByteBuffer);

        int bytesRead = stream.read(twoByteBuffer);

        assertEquals("All 2 bytes should have been read", 2, bytesRead);
        assertEquals("Stream should be fully consumed with 0 bytes remaining", 0, stream.available());
    }
}
