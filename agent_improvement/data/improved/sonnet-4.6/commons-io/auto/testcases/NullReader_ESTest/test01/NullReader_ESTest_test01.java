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
public class NullReader_ESTest_test01 extends NullReader_ESTest_scaffolding {

    /**
     * When NullReader is constructed with a negative size, skip() still clamps position
     * to that negative size. The returned skip count reflects how many characters were
     * "skipped" before hitting the (negative) boundary, which is also negative.
     *
     * size = -827, initial position = 0, skip requested = 382
     * After skip: position becomes -827 (clamped to size), return value = 382 - (382 - (-827)) = -827
     */
    @Test(timeout = 4000)
    public void test01_skipWithNegativeSize_clampsPositionToNegativeBoundary() throws Throwable {
        final long negativeSize = -827L;
        final long skipAmount = 382L;
        final long expectedPositionAndReturnValue = -827L;

        NullReader readerWithNegativeSize = new NullReader(negativeSize);

        long actualSkipped = readerWithNegativeSize.skip(skipAmount);

        assertEquals("Position should be clamped to the negative size boundary",
                expectedPositionAndReturnValue, readerWithNegativeSize.getPosition());
        assertEquals("Skip return value should equal the negative size when clamped",
                expectedPositionAndReturnValue, actualSkipped);
    }
}
