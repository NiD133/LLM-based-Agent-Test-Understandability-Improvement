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
        final long negativeReaderSize = -1187L;
        final int expectedCharacter = 0;
        final long expectedPositionAfterSingleRead = 1L;

        final NullReader reader = new NullReader(negativeReaderSize);

        final int actualCharacter = reader.read();

        assertEquals(expectedPositionAfterSingleRead, reader.getPosition());
        assertEquals(expectedCharacter, actualCharacter);
    }
}
