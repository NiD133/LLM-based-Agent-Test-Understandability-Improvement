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
public class SequenceReader_ESTest_test0 extends SequenceReader_ESTest_scaffolding {

    /**
     * Reading into a CharBuffer should pull characters from the single wrapped
     * source reader, copying the first character of its content into the buffer
     * and returning the number of characters read.
     */
    @Test(timeout = 4000)
    public void readIntoCharBufferReadsFirstCharacterFromSingleSource() throws Throwable {
        // Given a SequenceReader backed by one source reader.
        StringReader sourceReader = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        LinkedHashSet<StringReader> sourceReaders = new LinkedHashSet<StringReader>();
        sourceReaders.add(sourceReader);
        SequenceReader sequenceReader = new SequenceReader(sourceReaders);

        // And a single-character destination buffer.
        char[] destination = new char[1];
        CharBuffer buffer = CharBuffer.wrap(destination);

        // When reading into the buffer.
        int charsRead = sequenceReader.read(buffer);

        // Then exactly one character is read: the first character of the source.
        assertEquals(1, charsRead);
        assertArrayEquals(new char[] { 'o' }, destination);
    }
}
