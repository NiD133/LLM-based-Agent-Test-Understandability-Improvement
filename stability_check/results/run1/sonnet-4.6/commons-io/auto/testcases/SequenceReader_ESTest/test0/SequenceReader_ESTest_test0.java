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
     * Tests that reading into a CharBuffer of capacity 1 from a SequenceReader
     * backed by a single StringReader returns exactly one character — the first
     * character of the underlying string — and that the backing char array is
     * updated accordingly.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build a SequenceReader from a set containing one StringReader
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        StringReader stringReader = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        readers.add(stringReader);
        SequenceReader sequenceReader = new SequenceReader(readers);

        // Wrap a single-element char array in a CharBuffer so we can pass it to read(CharBuffer)
        char[] destination = new char[1];
        CharBuffer charBuffer = CharBuffer.wrap(destination);

        // Read up to one character; the buffer capacity limits the transfer to 1 char
        int charsRead = sequenceReader.read(charBuffer);

        // The first character of the string is 'o'; the backing array must reflect this
        assertArrayEquals(new char[] { 'o' }, destination);
        assertEquals(1, charsRead);
    }
}
