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
public class XmlStreamWriter_ESTest_test7 extends XmlStreamWriter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        XmlStreamWriter xmlStreamWriter0 = new XmlStreamWriter((OutputStream) null);
        Writer writer0 = xmlStreamWriter0.append((CharSequence) "<?xml");
        CharBuffer charBuffer0 = CharBuffer.allocate(8192);
        // Undeclared exception!
        try {
            writer0.append((CharSequence) charBuffer0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("java.io.Writer", e);
        }
    }
}
