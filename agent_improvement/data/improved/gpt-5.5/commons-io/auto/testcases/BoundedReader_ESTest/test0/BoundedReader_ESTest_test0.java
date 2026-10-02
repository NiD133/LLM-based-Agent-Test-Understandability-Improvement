package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test0 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        final int bufferLength = 14;
        final int maxCharsToRead = 1;
        final int offset = 1;
        final int requestedLength = 1;

        final char[] targetBuffer = new char[bufferLength];
        final StringReader sourceReader = new StringReader("KnLjCdWGnB@(}p3qC");
        final BoundedReader boundedReader = new BoundedReader(sourceReader, maxCharsToRead);

        final int charsRead = boundedReader.read(targetBuffer, offset, requestedLength);

        assertEquals(maxCharsToRead, charsRead);
    }
}
