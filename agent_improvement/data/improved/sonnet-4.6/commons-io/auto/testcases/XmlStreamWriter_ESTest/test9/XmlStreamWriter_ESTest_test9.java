package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test9 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Verifies that closing an XmlStreamWriter twice is safe and that the
     * encoding defaults to UTF-8 when no XML prolog was written before closing.
     */
    @Test(timeout = 4000)
    public void test_closeIsIdempotentAndDefaultEncodingIsUtf8() throws Throwable {
        MockFile outputFile = new MockFile("nz", "nz");
        XmlStreamWriter writer = new XmlStreamWriter(outputFile);

        // First close: writer is lazily initialized with the default charset and then closed.
        // Second close: must not throw even though the underlying writer is already closed.
        writer.close();
        writer.close();

        assertEquals("UTF-8", writer.getEncoding());
    }
}
