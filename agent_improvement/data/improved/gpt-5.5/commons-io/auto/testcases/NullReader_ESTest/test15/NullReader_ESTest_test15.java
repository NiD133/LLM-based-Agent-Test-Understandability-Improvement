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

    private static final long EMULATED_READER_SIZE = -827L;

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        NullReader reader = new NullReader(EMULATED_READER_SIZE);

        reader.getPosition();

        assertEquals(EMULATED_READER_SIZE, reader.getSize());
        assertTrue(reader.markSupported());
    }
}
