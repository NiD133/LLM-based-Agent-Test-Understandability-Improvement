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
        String sourceText = "org.apache.commons.io.filefilter.AgeFileFilter";
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        char[] destination = new char[1];
        CharBuffer destinationBuffer = CharBuffer.wrap(destination);
        StringReader sourceReader = new StringReader(sourceText);

        readers.add(sourceReader);
        SequenceReader sequenceReader = new SequenceReader(readers);
        int charsRead = sequenceReader.read(destinationBuffer);

        assertArrayEquals(new char[] { 'o' }, destination);
        assertEquals(1, charsRead);
    }
}
