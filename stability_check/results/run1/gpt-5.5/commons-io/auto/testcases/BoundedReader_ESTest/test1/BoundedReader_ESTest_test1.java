package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedReader;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test1 extends BoundedReader_ESTest_scaffolding {

    private static final int READER_LIMIT = 511;
    private static final int EOF = -1;

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        StringReader emptySource = new StringReader("");
        BoundedReader boundedReader = new BoundedReader(emptySource, READER_LIMIT);

        boundedReader.reset();
        boundedReader.skip(READER_LIMIT);
        boundedReader.mark(READER_LIMIT);

        int actualCharacter = boundedReader.read();

        assertEquals(EOF, actualCharacter);
    }
}
