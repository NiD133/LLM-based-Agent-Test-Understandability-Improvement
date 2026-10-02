package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test5 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * When the written content contains no XML prolog (and therefore no
     * "encoding=..." declaration), the writer keeps its default encoding,
     * which is UTF-8 for the {@code XmlStreamWriter(File)} constructor.
     */
    @Test(timeout = 4000)
    public void defaultEncodingRemainsUtf8WhenContentHasNoXmlProlog() throws Throwable {
        MockFile targetFile = new MockFile("nz", "nz");
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter(targetFile);

        // Plain text without an "<?xml ... encoding=...?>" prolog.
        xmlStreamWriter.write("\t\tm;+");
        xmlStreamWriter.write("nz");

        assertEquals("UTF-8", xmlStreamWriter.getDefaultEncoding());
    }
}
