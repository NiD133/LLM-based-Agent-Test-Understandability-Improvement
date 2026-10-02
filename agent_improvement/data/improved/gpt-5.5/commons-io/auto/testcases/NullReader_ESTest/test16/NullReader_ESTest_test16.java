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

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        NullReader readerWithNegativeSize = new NullReader((-1187L));

        int firstReadResult = readerWithNegativeSize.read();

        // A negative configured size is not EOF at position 0, so one read advances the position.
        assertEquals(1L, readerWithNegativeSize.getPosition());
        assertEquals(0, firstReadResult);
    }
}
