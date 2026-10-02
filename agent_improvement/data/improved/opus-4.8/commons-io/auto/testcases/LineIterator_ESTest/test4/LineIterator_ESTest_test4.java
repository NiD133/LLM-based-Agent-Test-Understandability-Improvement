package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LineIterator_ESTest_test4 extends LineIterator_ESTest_scaffolding {

    /**
     * Verifies that next() returns the single line of input when the reader
     * holds one line with no trailing line terminator.
     */
    @Test(timeout = 4000)
    public void nextReturnsSingleLineWithoutTerminator() throws Throwable {
        StringReader singleLineReader = new StringReader("_CB");
        LineIterator lineIterator = new LineIterator(singleLineReader);

        String firstLine = lineIterator.next();

        assertEquals("_CB", firstLine);
    }
}
