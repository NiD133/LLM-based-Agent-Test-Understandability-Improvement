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
public class BoundedReader_ESTest_test3 extends BoundedReader_ESTest_scaffolding {

    private static final String SOURCE_TEXT = "@</'";
    private static final int MAX_CHARS_FROM_TARGET_READER = 481;
    private static final int READ_AHEAD_LIMIT = 1;

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        StringReader sourceReader = new StringReader(SOURCE_TEXT);
        BoundedReader boundedReader = new BoundedReader(sourceReader, MAX_CHARS_FROM_TARGET_READER);

        boundedReader.reset();
        long skippedCharacters = boundedReader.skip(MAX_CHARS_FROM_TARGET_READER);
        assertEquals(4L, skippedCharacters);

        boundedReader.mark(READ_AHEAD_LIMIT);
        int nextCharacter = boundedReader.read();
        assertEquals((-1), nextCharacter);
    }
}
