package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.OutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test4 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // XmlStreamWriter buffers characters in a prolog writer until it has seen
        // enough content (>= 5 chars) to detect the XML encoding declaration.
        // Until that threshold is reached the underlying OutputStream is never
        // touched, so passing null is safe for short writes.
        XmlStreamWriter writer = new XmlStreamWriter((OutputStream) null);

        // Write a single character (Unicode code point 8192 = U+2000 EN QUAD).
        // One character is well below the 5-character prolog threshold, so the
        // null OutputStream is never accessed and no exception is thrown.
        writer.write(8192);

        // Because the prolog was too short to detect a declared encoding, the
        // writer falls back to its default encoding of UTF-8.
        assertEquals("UTF-8", writer.getDefaultEncoding());
    }
}
