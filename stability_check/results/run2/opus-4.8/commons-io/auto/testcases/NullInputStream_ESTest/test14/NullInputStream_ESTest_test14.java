package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test14 extends NullInputStream_ESTest_scaffolding {

    /**
     * When the emulated stream size exceeds {@link Integer#MAX_VALUE},
     * {@link NullInputStream#available()} must clamp its result to
     * {@code Integer.MAX_VALUE} rather than overflow. The single-argument
     * constructor also enables mark support by default.
     */
    @Test(timeout = 4000)
    public void availableIsClampedToIntegerMaxValueForOversizedStream() throws Throwable {
        // Size is larger than Integer.MAX_VALUE, so available() cannot return it exactly.
        long oversizedStreamSize = (long) Integer.MAX_VALUE + 1494L;
        NullInputStream nullInputStream = new NullInputStream(oversizedStreamSize);

        int availableBytes = nullInputStream.available();

        assertEquals(Integer.MAX_VALUE, availableBytes);
        assertTrue(nullInputStream.markSupported());
    }
}
