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
public class SequenceReader_ESTest_test4 extends SequenceReader_ESTest_scaffolding {

    /**
     * Verifies that SequenceReader.close() completes without error when the
     * reader array contains nulls alongside a valid PipedReader. The array
     * has four slots but only index 1 holds an actual reader; the remaining
     * slots are null, so close() must tolerate null entries while still
     * releasing the PipedReader.
     */
    @Test(timeout = 4000)
    public void test_close_withSparseReaderArray_containingNullsAndOnePipedReader() throws Throwable {
        // Build a 4-element array where only slot 1 is populated.
        Reader[] readers = new Reader[4];
        PipedReader pipedReader = new PipedReader(3262);
        readers[1] = pipedReader;

        SequenceReader sequenceReader = new SequenceReader(readers);
        sequenceReader.close();
    }
}
