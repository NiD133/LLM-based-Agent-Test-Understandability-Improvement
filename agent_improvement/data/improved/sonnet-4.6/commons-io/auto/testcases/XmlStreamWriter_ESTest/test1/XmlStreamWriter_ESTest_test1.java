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
public class XmlStreamWriter_ESTest_test1 extends XmlStreamWriter_ESTest_scaffolding {

    // Verifies that flushing an already-closed XmlStreamWriter raises IOException.
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        MockFile xmlFile = new MockFile("DQf{xmx %q-", "DQf{xmx %q-");
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter(xmlFile);
        xmlStreamWriter.close();
        try {
            xmlStreamWriter.flush();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
        }
    }
}
