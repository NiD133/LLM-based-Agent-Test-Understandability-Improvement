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
public class NullReader_ESTest_test14 extends NullReader_ESTest_scaffolding {

    private static final long NEGATIVE_READER_SIZE = -827L;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        NullReader reader = new NullReader(NEGATIVE_READER_SIZE);

        boolean markIsSupported = reader.markSupported();

        assertTrue(markIsSupported);
        assertEquals(NEGATIVE_READER_SIZE, reader.getSize());
    }
}
