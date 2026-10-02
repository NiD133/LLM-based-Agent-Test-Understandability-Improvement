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
public class NullReader_ESTest_test15 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies that a NullReader constructed with a negative size correctly reports
     * its initial state: position starts at zero, size reflects the value given to
     * the constructor, and mark is supported by default.
     */
    @Test(timeout = 4000)
    public void test_initialStateWithNegativeSize() throws Throwable {
        final long negativeSize = -827L;
        NullReader reader = new NullReader(negativeSize);

        assertEquals(0L, reader.getPosition());
        assertEquals(negativeSize, reader.getSize());
        assertTrue(reader.markSupported());
    }
}
