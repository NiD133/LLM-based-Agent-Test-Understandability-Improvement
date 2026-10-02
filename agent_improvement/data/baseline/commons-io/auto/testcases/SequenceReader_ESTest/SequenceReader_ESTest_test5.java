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
public class SequenceReader_ESTest_test5 extends SequenceReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        Reader[] readerArray0 = new Reader[0];
        SequenceReader sequenceReader0 = new SequenceReader(readerArray0);
        long long0 = sequenceReader0.skip(161L);
        assertEquals(0L, long0);
    }
}
