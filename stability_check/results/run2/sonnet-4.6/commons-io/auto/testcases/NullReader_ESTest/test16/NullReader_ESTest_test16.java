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
public class NullReader_ESTest_test16 extends NullReader_ESTest_scaffolding {

    /**
     * A NullReader with a negative size never reaches position == size, so read()
     * always advances the position and returns the processChar() value (0) rather
     * than signalling EOF.
     */
    @Test(timeout = 4000)
    public void test_read_withNegativeSize_advancesPositionAndReturnsZero() throws Throwable {
        final long negativeSize = -1187L;
        NullReader readerWithNegativeSize = new NullReader(negativeSize);

        int charRead = readerWithNegativeSize.read();

        assertEquals(1L, readerWithNegativeSize.getPosition());
        assertEquals(0, charRead);
    }
}
