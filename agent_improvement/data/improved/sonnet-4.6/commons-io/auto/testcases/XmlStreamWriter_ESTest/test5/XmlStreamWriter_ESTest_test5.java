package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test5 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Verifies that writing non-XML content (no XML prolog) does not change
     * the default encoding, which should remain "UTF-8".
     */
    @Test(timeout = 4000)
    public void test5() throws Throwable {
        MockFile outputFile = new MockFile("nz", "nz");
        XmlStreamWriter xmlWriter = new XmlStreamWriter(outputFile);
        xmlWriter.write("\t\tm;+");
        xmlWriter.write("nz");
        assertEquals("UTF-8", xmlWriter.getDefaultEncoding());
    }
}
