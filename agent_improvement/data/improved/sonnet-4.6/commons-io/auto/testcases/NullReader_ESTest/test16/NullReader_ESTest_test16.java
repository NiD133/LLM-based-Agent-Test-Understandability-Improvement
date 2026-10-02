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
     * A NullReader with a negative size has no defined end-of-file boundary
     * (position 0 does not equal size -1187), so read() succeeds: it advances
     * the position to 1 and returns the default processChar() value of 0.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        NullReader readerWithNegativeSize = new NullReader(-1187L);

        int charRead = readerWithNegativeSize.read();

        assertEquals("Position should advance to 1 after one read", 1L, readerWithNegativeSize.getPosition());
        assertEquals("read() should return the default processChar() value of 0", 0, charRead);
    }
}
