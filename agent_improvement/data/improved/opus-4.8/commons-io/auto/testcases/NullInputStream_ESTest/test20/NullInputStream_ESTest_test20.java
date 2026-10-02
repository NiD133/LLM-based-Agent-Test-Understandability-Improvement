package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test20 extends NullInputStream_ESTest_scaffolding {

    /**
     * Reading a single byte returns the emulated byte value (0) and advances
     * the position by one, even when the emulated size is negative (the stream
     * only reports end-of-file once the position equals the size, which a
     * negative size never reaches on the first read).
     */
    @Test(timeout = 4000)
    public void readSingleByteReturnsZeroAndAdvancesPosition() throws Throwable {
        NullInputStream nullInputStream = new NullInputStream(-30L);

        int firstByte = nullInputStream.read();

        assertEquals("read() should return the emulated byte value", 0, firstByte);
        assertEquals("position should advance by one after reading a byte", 1L, nullInputStream.getPosition());
    }
}
