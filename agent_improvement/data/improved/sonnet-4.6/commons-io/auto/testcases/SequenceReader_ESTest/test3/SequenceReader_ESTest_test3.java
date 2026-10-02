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
     * Verifies that reading from a SequenceReader into a read-only CharBuffer
     * throws ReadOnlyBufferException. CharBuffer.wrap(CharSequence) produces a
     * read-only buffer, so any attempt to write data into it must fail.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Arrange: build a SequenceReader backed by a single StringReader
        String content = "org.apache.commons.io.filefilter.AgeFileFilter";
        LinkedHashSet<StringReader> readerSet = new LinkedHashSet<StringReader>();
        readerSet.add(new StringReader(content));
        SequenceReader sequenceReader = new SequenceReader(readerSet);

        // Advance the reader by one character to exercise a partially-consumed reader
        sequenceReader.read();

        // CharBuffer.wrap(CharSequence) returns a read-only view — writes into it are forbidden
        CharBuffer readOnlyBuffer = CharBuffer.wrap((CharSequence) content);

        // Act & Assert: reading into a read-only buffer must throw ReadOnlyBufferException
        try {
            sequenceReader.read(readOnlyBuffer);
            fail("Expecting exception: ReadOnlyBufferException");
        } catch (ReadOnlyBufferException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("java.nio.StringCharBuffer", e);
        }
    }
}
