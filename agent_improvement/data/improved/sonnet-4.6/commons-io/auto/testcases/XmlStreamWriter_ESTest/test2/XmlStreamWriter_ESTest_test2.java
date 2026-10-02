package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test2 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Verifies that flushing an XmlStreamWriter backed by a null OutputStream is a no-op
     * (no exception is thrown because flush() only delegates when an internal writer has
     * been initialised, which it has not at this point), and that the default encoding
     * reported by the writer is "UTF-8" as documented.
     */
    @Test(timeout = 4000)
    public void test_flushBeforeWrite_withNullOutputStream_doesNotThrow_andDefaultEncodingIsUtf8() throws Throwable {
        // Arrange: writer is created with a null stream; no write has been performed yet,
        // so no internal OutputStreamWriter has been initialised.
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter((java.io.OutputStream) null);

        // Act: flush before any write – the writer field is still null, so flush is a no-op.
        xmlStreamWriter.flush();

        // Assert: the declared default encoding must be "UTF-8".
        assertEquals("UTF-8", xmlStreamWriter.getDefaultEncoding());
    }
}
