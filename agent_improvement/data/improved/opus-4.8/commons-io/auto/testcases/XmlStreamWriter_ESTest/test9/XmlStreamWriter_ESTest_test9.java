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
     * Closing an {@link XmlStreamWriter} twice is harmless: the second call is a
     * no-op and the writer still reports its default encoding (UTF-8), which is
     * picked when no XML prolog has been written to detect a charset from.
     */
    @Test(timeout = 4000)
    public void closingTwiceLeavesDefaultUtf8Encoding() throws Throwable {
        MockFile targetFile = new MockFile("nz", "nz");
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter(targetFile);

        xmlStreamWriter.close();
        xmlStreamWriter.close();

        assertEquals("UTF-8", xmlStreamWriter.getEncoding());
    }
}
