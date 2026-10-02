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

    private static final String SOURCE_TEXT = "org.apache.commons.io.filefilter.AgeFileFilter";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        StringReader sourceReader = new StringReader(SOURCE_TEXT);
        readers.add(sourceReader);

        char[] singleCharacterBuffer = new char[1];
        CharBuffer targetBuffer = CharBuffer.wrap(singleCharacterBuffer);
        SequenceReader sequenceReader = new SequenceReader(readers);

        int charactersRead = sequenceReader.read(targetBuffer);

        assertArrayEquals(new char[] { 'o' }, singleCharacterBuffer);
        assertEquals(1, charactersRead);
    }
}
