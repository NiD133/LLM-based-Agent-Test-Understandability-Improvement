package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequenceReader_ESTest_test0 extends SequenceReader_ESTest_scaffolding {

    /**
     * Reading into a single-character CharBuffer should copy exactly one
     * character - the first character of the underlying reader's content -
     * and report a read count of 1.
     */
    @Test(timeout = 4000)
    public void readIntoSingleCharBufferCopiesFirstCharacter() throws Throwable {
        // A CharBuffer with room for exactly one character.
        char[] destination = new char[1];
        CharBuffer singleCharBuffer = CharBuffer.wrap(destination);

        // A SequenceReader backed by one StringReader whose first character is 'o'.
        LinkedHashSet<StringReader> sources = new LinkedHashSet<StringReader>();
        sources.add(new StringReader("org.apache.commons.io.filefilter.AgeFileFilter"));
        SequenceReader sequenceReader = new SequenceReader(sources);

        int charsRead = sequenceReader.read(singleCharBuffer);

        assertArrayEquals(new char[] { 'o' }, destination);
        assertEquals(1, charsRead);
    }
}
