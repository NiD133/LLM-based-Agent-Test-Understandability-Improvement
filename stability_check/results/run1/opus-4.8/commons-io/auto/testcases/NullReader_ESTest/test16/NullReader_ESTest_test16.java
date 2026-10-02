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
     * Reading a single character from a NullReader advances the position by one
     * and returns the default character value (0) produced by processChar().
     */
    @Test(timeout = 4000)
    public void readSingleCharAdvancesPositionAndReturnsZero() throws Throwable {
        NullReader nullReader = new NullReader(-1187L);

        int character = nullReader.read();

        assertEquals("position should advance to 1 after reading one character", 1L, nullReader.getPosition());
        assertEquals("read() should return the default character value 0", 0, character);
    }
}
