package org.apache.commons.io.output;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test1 extends XmlStreamWriter_ESTest_scaffolding {

    private static final String FILE_NAME = "DQf{xmx %q-";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        MockFile fileWithSameNameAndParent = new MockFile(FILE_NAME, FILE_NAME);
        XmlStreamWriter writer = new XmlStreamWriter(fileWithSameNameAndParent);

        writer.close();

        try {
            writer.flush();
            fail("Expecting exception: IOException");
        } catch (IOException expected) {
        }
    }
}
