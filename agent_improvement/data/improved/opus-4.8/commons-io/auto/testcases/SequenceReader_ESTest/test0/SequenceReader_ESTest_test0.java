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
     * Reading into a CharBuffer should pull characters from the underlying
     * source reader and report how many were transferred. Here the buffer has
     * room for a single character, so exactly one character (the first letter
     * of the source string, 'o') is read.
     */
    @Test(timeout = 4000)
    public void testReadIntoSingleCharBufferReadsFirstCharacter() throws Throwable {
        // Destination buffer with capacity for exactly one character.
        char[] destination = new char[1];
        CharBuffer charBuffer = CharBuffer.wrap(destination);

        // A SequenceReader backed by a single source reader.
        StringReader sourceReader = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        readers.add(sourceReader);
        SequenceReader sequenceReader = new SequenceReader(readers);

        int charsRead = sequenceReader.read(charBuffer);

        // One character was read, and it is the first letter of the source string.
        assertEquals(1, charsRead);
        assertArrayEquals(new char[] { 'o' }, destination);
    }
}
