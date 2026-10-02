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

    /**
     * Verifies that writing a single character to a freshly created writer does
     * not change its default encoding, which stays UTF-8 (the value supplied by
     * the deprecated single-argument constructor).
     *
     * <p>A lone character is too short to contain an XML prolog, so no encoding
     * detection takes place and the {@code null} output stream is never touched.</p>
     */
    @Test(timeout = 4000)
    public void writeSingleCharKeepsDefaultEncodingUtf8() throws Throwable {
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter((OutputStream) null);

        // 8192 is an arbitrary character code; one character cannot form an XML prolog.
        xmlStreamWriter.write(8192);

        assertEquals("UTF-8", xmlStreamWriter.getDefaultEncoding());
    }
}
