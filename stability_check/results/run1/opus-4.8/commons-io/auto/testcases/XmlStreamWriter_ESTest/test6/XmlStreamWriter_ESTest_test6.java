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
     * When an XmlStreamWriter is created from a File without specifying a
     * default encoding, its default encoding should be UTF-8.
     */
    @Test(timeout = 4000)
    public void defaultEncodingIsUtf8WhenNoneSpecified() throws Throwable {
        MockFile parentDirectory = new MockFile("");
        File targetFile = MockFile.createTempFile("$VALUES", "ij*`!AFN", (File) parentDirectory);
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter(targetFile);

        String defaultEncoding = xmlStreamWriter.getDefaultEncoding();

        assertEquals("UTF-8", defaultEncoding);
    }
}
