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
public class XmlStreamWriter_ESTest_test3 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Writing the opening of an XML prolog ("<?xml") is safely buffered, but
     * completing the prolog ("?>") forces XmlStreamWriter to create the underlying
     * OutputStreamWriter with the null OutputStream, which throws NullPointerException.
     */
    @Test(timeout = 4000)
    public void test_completingXmlPrologWithNullOutputStream_throwsNullPointerException() throws Throwable {
        XmlStreamWriter writerWithNullStream = new XmlStreamWriter((OutputStream) null);
        // The opening fragment is buffered; no write to the underlying stream yet.
        writerWithNullStream.write("<?xml");
        try {
            // Completing the prolog triggers encoding detection and creation of the
            // underlying OutputStreamWriter, which fails because the OutputStream is null.
            writerWithNullStream.write("?>");
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.io.Writer", e);
        }
    }
}
