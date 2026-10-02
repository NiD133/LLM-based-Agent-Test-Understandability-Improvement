package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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

    /**
     * Reading into a read-only {@link CharBuffer} must fail with a
     * {@link ReadOnlyBufferException}. The buffer here is read-only because it
     * wraps an (immutable) {@link CharSequence}, so the underlying reader's
     * attempt to store characters into it is rejected.
     */
    @Test(timeout = 4000)
    public void readIntoReadOnlyCharBufferThrowsReadOnlyBufferException() throws Throwable {
        // A SequenceReader backed by a single StringReader with some content.
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        readers.add(new StringReader("org.apache.commons.io.filefilter.AgeFileFilter"));
        SequenceReader sequenceReader = new SequenceReader(readers);

        // Consume one character so that content is still available afterwards.
        sequenceReader.read();

        // CharBuffer.wrap(CharSequence) returns a read-only buffer.
        CharBuffer readOnlyBuffer =
                CharBuffer.wrap((CharSequence) "org.apache.commons.io.filefilter.AgeFileFilter");

        try {
            sequenceReader.read(readOnlyBuffer);
            fail("Expecting exception: ReadOnlyBufferException");
        } catch (ReadOnlyBufferException e) {
            // The write into the read-only buffer is rejected by StringCharBuffer.
            verifyException("java.nio.StringCharBuffer", e);
        }
    }
}
