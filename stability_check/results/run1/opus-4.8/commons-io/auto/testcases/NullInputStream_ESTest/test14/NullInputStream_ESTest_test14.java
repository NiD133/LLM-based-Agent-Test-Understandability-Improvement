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
     * Verifies that {@link NullInputStream#available()} caps its result at
     * {@link Integer#MAX_VALUE} when the emulated stream size exceeds the range
     * of an {@code int}, and that marking is supported by default.
     */
    @Test(timeout = 4000)
    public void availableIsCappedAtIntegerMaxValueForOversizedStream() throws Throwable {
        // Size chosen to be larger than Integer.MAX_VALUE so available() must saturate.
        final long oversizedStreamSize = 2147485141L;
        NullInputStream nullInputStream = new NullInputStream(oversizedStreamSize);

        int availableBytes = nullInputStream.available();

        assertEquals(Integer.MAX_VALUE, availableBytes);
        assertTrue(nullInputStream.markSupported());
    }
}
