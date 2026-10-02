package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test9 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test9() throws Throwable {
        MockFile mockFile0 = new MockFile("nz", "nz");
        XmlStreamWriter xmlStreamWriter0 = new XmlStreamWriter(mockFile0);
        xmlStreamWriter0.close();
        xmlStreamWriter0.close();
        assertEquals("UTF-8", xmlStreamWriter0.getEncoding());
    }
}
