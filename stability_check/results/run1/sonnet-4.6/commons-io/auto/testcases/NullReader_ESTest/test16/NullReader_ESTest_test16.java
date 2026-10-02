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
     * A NullReader constructed with a negative size never reaches EOF via normal
     * position-equals-size comparison, so read() proceeds normally: it increments
     * position to 1 and returns the default processChar() value of 0.
     */
    @Test(timeout = 4000)
    public void test_readSingleChar_withNegativeSize_advancesPositionAndReturnsZero() throws Throwable {
        // Negative size: position (0) never equals size (-1187), so no EOF on first read
        NullReader reader = new NullReader(-1187L);

        int charRead = reader.read();

        assertEquals("Position should advance to 1 after one read", 1L, reader.getPosition());
        assertEquals("read() should return the default processChar() value of 0", 0, charRead);
    }
}
