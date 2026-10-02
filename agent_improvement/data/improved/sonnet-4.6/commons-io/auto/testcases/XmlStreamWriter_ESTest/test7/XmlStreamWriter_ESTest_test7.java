package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Writer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test7 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Verifies that appending a large CharBuffer to an XmlStreamWriter backed by a null
     * OutputStream throws NullPointerException during XML encoding detection.
     *
     * When the writer first receives the XML prolog prefix "<?xml" it begins buffering
     * characters to detect the encoding.  A subsequent large append fills the prolog
     * buffer to capacity, at which point the writer attempts to create an
     * OutputStreamWriter around the underlying OutputStream — which is null — causing
     * the NullPointerException.
     */
    @Test(timeout = 4000)
    public void test_appendLargeBufferAfterXmlPrologPrefix_throwsNullPointerExceptionOnNullOutputStream() throws Throwable {
        // Arrange: writer backed by a null OutputStream; writing the XML prolog
        // prefix puts the writer into prolog-detection mode without yet committing
        // to a charset.
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter((java.io.OutputStream) null);
        Writer writerInPrologMode = xmlStreamWriter.append((CharSequence) "<?xml");

        // A CharBuffer large enough (8192 chars) to overflow the internal prolog
        // buffer (also 8192 chars), which forces the writer to finalise the
        // encoding and flush the prolog — an operation that fails because the
        // underlying OutputStream is null.
        CharBuffer largeCharBuffer = CharBuffer.allocate(8192);

        // Act & Assert
        try {
            writerInPrologMode.append((CharSequence) largeCharBuffer);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.io.Writer", e);
        }
    }
}
