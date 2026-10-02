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
        LinkedHashSet<StringReader> linkedHashSet0 = new LinkedHashSet<StringReader>();
        StringReader stringReader0 = new StringReader("org.apache.commons.io.filefilter.AgeFileFilter");
        linkedHashSet0.add(stringReader0);
        SequenceReader sequenceReader0 = new SequenceReader(linkedHashSet0);
        sequenceReader0.read();
        CharBuffer charBuffer0 = CharBuffer.wrap((CharSequence) "org.apache.commons.io.filefilter.AgeFileFilter");
        // Undeclared exception!
        try {
            sequenceReader0.read(charBuffer0);
            fail("Expecting exception: ReadOnlyBufferException");
        } catch (ReadOnlyBufferException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("java.nio.StringCharBuffer", e);
        }
    }
}
