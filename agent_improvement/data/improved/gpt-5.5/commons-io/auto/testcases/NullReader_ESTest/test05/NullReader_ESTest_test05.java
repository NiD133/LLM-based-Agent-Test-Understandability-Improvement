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

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final NullReader reader = new NullReader(1017L);
        final char[] destination = new char[8];

        final int charactersRead = reader.read(destination);

        assertEquals(8L, reader.getPosition());
        assertEquals(8, charactersRead);
    }
}
