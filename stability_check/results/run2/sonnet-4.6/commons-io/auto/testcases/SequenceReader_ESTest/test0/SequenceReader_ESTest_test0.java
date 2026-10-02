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

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build a SequenceReader backed by a single StringReader
        LinkedHashSet<StringReader> readerSet = new LinkedHashSet<StringReader>();
        StringReader source = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        readerSet.add(source);
        SequenceReader sequenceReader = new SequenceReader(readerSet);

        // Read one character into a CharBuffer wrapping a 1-element char array
        char[] destination = new char[1];
        CharBuffer charBuffer = CharBuffer.wrap(destination);
        int charsRead = sequenceReader.read(charBuffer);

        // The first character 'o' should be placed in the array, with a count of 1
        assertArrayEquals(new char[] { 'o' }, destination);
        assertEquals(1, charsRead);
    }
}
