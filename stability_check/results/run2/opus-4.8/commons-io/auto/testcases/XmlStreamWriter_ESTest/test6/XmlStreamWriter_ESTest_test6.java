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
     * A writer created for a plain file (no encoding argument) should report
     * UTF-8 as its default encoding.
     */
    @Test(timeout = 4000)
    public void defaultEncodingIsUtf8WhenNoEncodingGiven() throws Throwable {
        // Create a temporary target file for the writer to wrap.
        MockFile parentDirectory = new MockFile("");
        File targetFile = MockFile.createTempFile("$VALUES", "XREPLACEX`!AFN", parentDirectory);

        XmlStreamWriter writer = new XmlStreamWriter(targetFile);

        String defaultEncoding = writer.getDefaultEncoding();

        assertEquals("UTF-8", defaultEncoding);
    }
}
