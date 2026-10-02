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
     * Verifies that reading into a one-character CharBuffer from a SequenceReader
     * backed by a single StringReader returns exactly one character and fills the buffer
     * with the first character of the source string.
     */
    @Test(timeout = 4000)
    public void test_readIntoCharBuffer_returnsSingleCharAndFillsBuffer() throws Throwable {
        // Arrange: build a SequenceReader over one StringReader whose first char is 'o'
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        StringReader sourceReader = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        readers.add(sourceReader);
        SequenceReader sequenceReader = new SequenceReader(readers);

        // A one-element char array wrapped as a CharBuffer limits the read to one character
        char[] destination = new char[1];
        CharBuffer charBuffer = CharBuffer.wrap(destination);

        // Act
        int charsRead = sequenceReader.read(charBuffer);

        // Assert: exactly one character ('o') was read into the buffer
        assertEquals(1, charsRead);
        assertArrayEquals(new char[] { 'o' }, destination);
    }
}
