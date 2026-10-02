package org.apache.commons.io.output;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.io.OutputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test2 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        XmlStreamWriter writerWithNullOutputStream = new XmlStreamWriter((OutputStream) null);

        writerWithNullOutputStream.flush();

        assertEquals("UTF-8", writerWithNullOutputStream.getDefaultEncoding());
    }
}
