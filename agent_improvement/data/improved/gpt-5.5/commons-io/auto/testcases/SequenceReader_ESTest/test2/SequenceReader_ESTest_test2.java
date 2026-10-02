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
public class SequenceReader_ESTest_test2 extends SequenceReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        readers.add(emptyReader);

        SequenceReader sequenceReader = new SequenceReader(readers);
        int firstCharacter = sequenceReader.read();

        assertEquals((-1), firstCharacter);
    }
}
