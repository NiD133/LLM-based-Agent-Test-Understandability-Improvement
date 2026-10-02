package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.PipedReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequenceReader_ESTest_test5 extends SequenceReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_skipOnEmptySequenceReaderReturnsZero() throws Throwable {
        // A SequenceReader with no underlying readers has no data to skip over
        SequenceReader emptyReader = new SequenceReader(new Reader[0]);

        long skippedChars = emptyReader.skip(161L);

        // Skipping over an empty reader should skip 0 characters
        assertEquals(0L, skippedChars);
    }
}
