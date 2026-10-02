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
     * Reading a single character from a non-empty NullReader returns the
     * default character value (0) and advances the position by one.
     */
    @Test(timeout = 4000)
    public void readSingleCharAdvancesPositionAndReturnsDefaultChar() throws Throwable {
        NullReader reader = new NullReader(-1187L);

        int character = reader.read();

        assertEquals("read() returns the default character value", 0, character);
        assertEquals("position advances by one after a single read", 1L, reader.getPosition());
    }
}
