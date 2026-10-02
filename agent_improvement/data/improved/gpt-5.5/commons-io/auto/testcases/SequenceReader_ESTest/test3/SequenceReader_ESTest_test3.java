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
public class SequenceReader_ESTest_test3 extends SequenceReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        final LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        final StringReader sourceReader = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        readers.add(sourceReader);

        final SequenceReader sequenceReader = new SequenceReader(readers);
        sequenceReader.read();

        final CharBuffer readOnlyTargetBuffer = CharBuffer.wrap((CharSequence) "org.apache.commons.io.filefilter.AgeFileFilter");
        // Undeclared exception!
        try {
            sequenceReader.read(readOnlyTargetBuffer);
            fail("Expecting exception: ReadOnlyBufferException");
        } catch (ReadOnlyBufferException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("java.nio.StringCharBuffer", e);
        }
    }
}
