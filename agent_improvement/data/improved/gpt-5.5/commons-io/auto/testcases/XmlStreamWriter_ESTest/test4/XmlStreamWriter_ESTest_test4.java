package org.apache.commons.io.output;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.io.OutputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test4 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        XmlStreamWriter writerWithNullOutputStream = new XmlStreamWriter((OutputStream) null);

        writerWithNullOutputStream.write(8192);

        assertEquals("UTF-8", writerWithNullOutputStream.getDefaultEncoding());
    }
}
