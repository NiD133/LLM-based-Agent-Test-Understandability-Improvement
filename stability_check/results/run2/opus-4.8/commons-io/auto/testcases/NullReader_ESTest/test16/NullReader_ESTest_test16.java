package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test16 extends NullReader_ESTest_scaffolding {

    /**
     * Reading a single character from a non-empty NullReader should:
     * - return the placeholder character produced by processChar(), which is 0, and
     * - advance the current position from 0 to 1.
     */
    @Test(timeout = 4000)
    public void readSingleCharacterAdvancesPositionAndReturnsZero() throws Throwable {
        NullReader nullReader = new NullReader(-1187L);

        int characterRead = nullReader.read();

        assertEquals("read() should return the placeholder character 0", 0, characterRead);
        assertEquals("position should advance to 1 after reading one character", 1L, nullReader.getPosition());
    }
}
