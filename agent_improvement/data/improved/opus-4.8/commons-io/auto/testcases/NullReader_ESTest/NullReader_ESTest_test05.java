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
public class NullReader_ESTest_test05 extends NullReader_ESTest_scaffolding {

    /**
     * Reading a full buffer from a reader larger than the buffer should fill the
     * whole buffer, returning the buffer length and advancing the position by
     * that many characters.
     */
    @Test(timeout = 4000)
    public void readFullBufferReturnsBufferLengthAndAdvancesPosition() throws Throwable {
        NullReader reader = new NullReader(1017L);
        char[] buffer = new char[8];

        int charsRead = reader.read(buffer);

        assertEquals("entire buffer should be filled", 8, charsRead);
        assertEquals("position should advance by the number of characters read",
                8L, reader.getPosition());
    }
}
