package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test7 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Writing enough characters to overflow the XML-prolog detection buffer
     * forces the writer to flush to its underlying OutputStream. Since this
     * writer was created with a {@code null} OutputStream, that flush fails
     * with a NullPointerException raised from inside {@link java.io.Writer}.
     */
    @Test(timeout = 4000)
    public void appendOverflowingPrologToNullStreamThrowsNullPointerException() throws Throwable {
        // Underlying stream is null, so any eventual flush of detected content fails.
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter((OutputStream) null);

        // Start an XML prolog; this is buffered while the encoding is being detected.
        Writer writer = xmlStreamWriter.append("<?xml");

        // A full buffer (8192 chars) pushes total content past the prolog buffer size,
        // triggering a write to the (null) underlying stream.
        CharBuffer overflowingContent = CharBuffer.allocate(8192);

        try {
            writer.append(overflowingContent);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // No message in the exception (getMessage() returns null);
            // it originates from java.io.Writer.
            verifyException("java.io.Writer", e);
        }
    }
}
