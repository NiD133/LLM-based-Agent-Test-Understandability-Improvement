package org.apache.commons.io.output;

import static org.junit.Assert.assertEquals;

import java.io.OutputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test2 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Flushing a writer that has not produced any output yet is a no-op and
     * must not change the configured default encoding. When constructed from a
     * plain OutputStream the default encoding is UTF-8.
     */
    @Test(timeout = 4000)
    public void flushBeforeWriteKeepsDefaultUtf8Encoding() throws Throwable {
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter((OutputStream) null);

        xmlStreamWriter.flush();

        assertEquals("UTF-8", xmlStreamWriter.getDefaultEncoding());
    }
}
