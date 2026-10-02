package org.apache.commons.io.output;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.io.File;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test6 extends XmlStreamWriter_ESTest_scaffolding {

    private static final String TEMP_FILE_PREFIX = "$VALUES";
    private static final String TEMP_FILE_SUFFIX = "ij*`!\u0002AFN";
    private static final String DEFAULT_XML_ENCODING = "UTF-8";

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        MockFile tempDirectory = new MockFile("");
        File tempFile = MockFile.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX, (File) tempDirectory);

        XmlStreamWriter writer = new XmlStreamWriter(tempFile);

        assertEquals(DEFAULT_XML_ENCODING, writer.getDefaultEncoding());
    }
}
