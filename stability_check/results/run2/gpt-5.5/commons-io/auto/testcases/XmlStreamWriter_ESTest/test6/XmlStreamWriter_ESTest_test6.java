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

    private static final String EXPECTED_DEFAULT_ENCODING = "UTF-8";
    private static final String TEMP_FILE_PREFIX = "$VALUES";
    private static final String TEMP_FILE_SUFFIX = "ij*`\u0002AFN";

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        MockFile parentDirectory = new MockFile("");
        File temporaryXmlFile = MockFile.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX, (File) parentDirectory);

        XmlStreamWriter writer = new XmlStreamWriter(temporaryXmlFile);
        String defaultEncoding = writer.getDefaultEncoding();

        assertEquals(EXPECTED_DEFAULT_ENCODING, defaultEncoding);
    }
}
