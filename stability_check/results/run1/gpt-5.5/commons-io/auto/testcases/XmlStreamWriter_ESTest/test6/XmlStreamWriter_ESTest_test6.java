package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test6 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        MockFile tempDirectory = new MockFile("");
        File xmlTargetFile = MockFile.createTempFile("$VALUES", "ij*`!\u0002AFN", (File) tempDirectory);

        XmlStreamWriter writer = new XmlStreamWriter(xmlTargetFile);
        String defaultEncoding = writer.getDefaultEncoding();

        assertEquals("UTF-8", defaultEncoding);
    }
}
