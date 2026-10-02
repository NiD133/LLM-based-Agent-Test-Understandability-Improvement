package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test14 extends NullInputStream_ESTest_scaffolding {

    /**
     * When the emulated stream size exceeds Integer.MAX_VALUE, available()
     * should cap its result at Integer.MAX_VALUE. Marking is supported by default.
     */
    @Test(timeout = 4000)
    public void availableIsCappedAtIntegerMaxValueForHugeStream() throws Throwable {
        long sizeLargerThanIntMax = 2147485141L; // Integer.MAX_VALUE (2147483647) + 1494
        NullInputStream hugeStream = new NullInputStream(sizeLargerThanIntMax);

        int available = hugeStream.available();

        assertEquals(Integer.MAX_VALUE, available);
        assertTrue("mark() should be supported by default", hugeStream.markSupported());
    }
}
