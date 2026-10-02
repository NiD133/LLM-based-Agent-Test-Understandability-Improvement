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

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        LinkedHashSet<StringReader> emptyReaders = new LinkedHashSet<StringReader>();
        char[] oneCharacterBuffer = new char[1];
        CharBuffer targetBuffer = CharBuffer.wrap(oneCharacterBuffer);
        targetBuffer.flip();

        SequenceReader sequenceReader = new SequenceReader(emptyReaders);
        int charactersRead = sequenceReader.read(targetBuffer);

        assertEquals(0, charactersRead);
    }
}
