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
public class SequenceReader_ESTest_test1 extends SequenceReader_ESTest_scaffolding {

    /**
     * Reading into a CharBuffer that has no remaining space should read nothing
     * and return 0, even when the SequenceReader wraps no underlying readers.
     */
    @Test(timeout = 4000)
    public void readIntoFullBufferReturnsZero() throws Throwable {
        // A SequenceReader backed by an empty collection of readers.
        LinkedHashSet<StringReader> noReaders = new LinkedHashSet<StringReader>();
        SequenceReader sequenceReader = new SequenceReader(noReaders);

        // flip() after allocation leaves a buffer with zero remaining capacity.
        CharBuffer targetBuffer = CharBuffer.wrap(new char[1]);
        targetBuffer.flip();

        int charsRead = sequenceReader.read(targetBuffer);

        assertEquals(0, charsRead);
    }
}
