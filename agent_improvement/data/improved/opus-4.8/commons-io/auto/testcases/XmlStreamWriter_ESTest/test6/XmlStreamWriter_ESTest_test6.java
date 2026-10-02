package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test6 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * When an XmlStreamWriter is created for a file without specifying a default
     * encoding, getDefaultEncoding() should report UTF-8 (the built-in default).
     */
    @Test(timeout = 4000)
    public void defaultEncodingIsUtf8WhenConstructedFromFile() throws Throwable {
        // Create a temporary file to write XML to. The exact name/suffix is
        // irrelevant to the behaviour under test; any valid temp file works.
        MockFile parentDirectory = new MockFile("");
        File xmlFile = MockFile.createTempFile("$VALUES", "ij*`!AFN", (File) parentDirectory);

        // Construct the writer using the File constructor (no default encoding given).
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter(xmlFile);

        // The default encoding falls back to UTF-8.
        String defaultEncoding = xmlStreamWriter.getDefaultEncoding();
        assertEquals("UTF-8", defaultEncoding);
    }
}
