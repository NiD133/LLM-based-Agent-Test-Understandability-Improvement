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
public class NullInputStream_ESTest_test02 extends NullInputStream_ESTest_scaffolding {

    /**
     * When NullInputStream is constructed with a negative size, skip() clamps the
     * position to that negative size value and returns the clamped amount rather
     * than the requested number of bytes.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        final long negativeSize = -2268L;
        NullInputStream stream = new NullInputStream(negativeSize);

        // Skipping 2163 bytes overshoots the negative 'end', so position is clamped
        // to negativeSize and the returned count reflects the clamped skip distance.
        long bytesSkipped = stream.skip(2163L);

        assertEquals(negativeSize, stream.getPosition());
        assertEquals(negativeSize, bytesSkipped);
    }
}
