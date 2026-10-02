package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test0 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        XmlStreamWriter.Builder builder = XmlStreamWriter.builder();
        PipedInputStream pipeReader = new PipedInputStream();
        PipedOutputStream pipeWriter = new PipedOutputStream(pipeReader);
        DataOutputStream outputStream = new DataOutputStream(pipeWriter);

        builder.setOutputStream(outputStream);
        XmlStreamWriter writer = builder.get();

        assertEquals("UTF-8", writer.getDefaultEncoding());
    }
}
