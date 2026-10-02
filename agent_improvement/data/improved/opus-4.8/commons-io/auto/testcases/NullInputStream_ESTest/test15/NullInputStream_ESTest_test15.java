package org.apache.commons.io.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test15 extends NullInputStream_ESTest_scaffolding {

    /**
     * A stream constructed with a negative emulated size reports no available
     * bytes, because {@code available()} clamps any non-positive remaining
     * count to 0. The single-argument constructor also enables mark support.
     */
    @Test(timeout = 4000)
    public void availableIsZeroForNegativeSizeAndMarkIsSupported() throws Throwable {
        NullInputStream streamWithNegativeSize = new NullInputStream(-550L);

        int availableBytes = streamWithNegativeSize.available();

        assertEquals(0, availableBytes);
        assertTrue(streamWithNegativeSize.markSupported());
    }
}
