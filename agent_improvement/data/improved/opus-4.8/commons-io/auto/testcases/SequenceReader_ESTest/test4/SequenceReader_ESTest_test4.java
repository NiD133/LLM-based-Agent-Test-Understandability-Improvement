package org.apache.commons.io.input;

import org.junit.Test;
import java.io.PipedReader;
import java.io.Reader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequenceReader_ESTest_test4 extends SequenceReader_ESTest_scaffolding {

    /**
     * Verifies that {@link SequenceReader#close()} succeeds even when the
     * backing reader array is sparsely populated, i.e. it contains both a real
     * reader and {@code null} slots. Closing iterates over every element, so
     * this confirms the {@code null} entries are tolerated without error.
     */
    @Test(timeout = 4000)
    public void closeIgnoresNullReadersInArray() throws Throwable {
        // Build a reader array of length 4 with only index 1 populated;
        // the remaining three slots stay null.
        Reader[] readers = new Reader[4];
        readers[1] = new PipedReader(3262);

        SequenceReader sequenceReader = new SequenceReader(readers);

        // close() walks through all four elements; the null slots must not
        // cause a failure.
        sequenceReader.close();
    }
}
