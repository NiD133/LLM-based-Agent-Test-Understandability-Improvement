package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test3 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Once the full XML prolog ("&lt;?xml ... ?&gt;") has been written, the writer
     * stops buffering and tries to flush its content to the underlying output
     * stream. When that stream is {@code null}, wrapping it in an
     * {@code OutputStreamWriter} fails with a {@link NullPointerException}
     * thrown from {@link java.io.Writer}'s constructor.
     */
    @Test(timeout = 4000)
    public void completingPrologWithNullOutputStreamThrowsNpe() throws Throwable {
        XmlStreamWriter writer = new XmlStreamWriter((OutputStream) null);

        // Write the opening of the prolog; this is still buffered, so it succeeds.
        writer.write("<?xml");

        // Writing the prolog's closing "?>" completes the prolog and forces the
        // writer to create an OutputStreamWriter around the null output stream.
        try {
            writer.write("?>");
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The NPE originates from java.io.Writer's constructor and carries no message.
            verifyException("java.io.Writer", e);
        }
    }
}
