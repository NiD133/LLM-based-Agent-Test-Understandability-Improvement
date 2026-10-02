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
public class SequenceReader_ESTest_test4 extends SequenceReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        Reader[] readers = new Reader[4];
        PipedReader pipedReader = new PipedReader(3262);
        readers[1] = (Reader) pipedReader;

        SequenceReader sequenceReader = new SequenceReader(readers);
        sequenceReader.close();
    }
}
