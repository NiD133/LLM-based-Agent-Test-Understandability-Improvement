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
    public void test5() throws Throwable {
        Reader[] emptyReaders = new Reader[0];
        SequenceReader sequenceReader = new SequenceReader(emptyReaders);

        // With no backing readers, skipping cannot advance the sequence.
        long skippedCharacterCount = sequenceReader.skip(161L);

        assertEquals(0L, skippedCharacterCount);
    }
}
