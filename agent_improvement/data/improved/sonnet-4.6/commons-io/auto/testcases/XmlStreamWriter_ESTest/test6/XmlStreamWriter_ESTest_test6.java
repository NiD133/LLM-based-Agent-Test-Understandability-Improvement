package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test6 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Verifies that XmlStreamWriter constructed from a File reports "UTF-8" as its default encoding.
     * The File constructor delegates to the OutputStream constructor, which always sets
     * the default charset to StandardCharsets.UTF_8.
     */
    @Test(timeout = 4000)
    public void testGetDefaultEncodingIsUtf8WhenConstructedFromFile() throws Throwable {
        MockFile tempDir = new MockFile("");
        File tempFile = MockFile.createTempFile("$VALUES", "ij*`!AFN", (File) tempDir);
        XmlStreamWriter xmlWriter = new XmlStreamWriter(tempFile);

        String defaultEncoding = xmlWriter.getDefaultEncoding();

        assertEquals("UTF-8", defaultEncoding);
    }
}
