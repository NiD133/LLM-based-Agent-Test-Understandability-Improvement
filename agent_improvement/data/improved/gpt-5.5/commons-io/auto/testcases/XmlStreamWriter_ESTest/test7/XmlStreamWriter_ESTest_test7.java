package org.apache.commons.io.output;

import static org.evosuite.runtime.EvoAssertions.*;
import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test7 extends XmlStreamWriter_ESTest_scaffolding {

    private static final String XML_DECLARATION_START = "<?xml";
    private static final int PROLOG_BUFFER_SIZE = 8192;

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        XmlStreamWriter writerWithNullOutputStream = new XmlStreamWriter((OutputStream) null);
        Writer sameWriter = writerWithNullOutputStream.append((CharSequence) XML_DECLARATION_START);
        CharBuffer prologSizedBuffer = CharBuffer.allocate(PROLOG_BUFFER_SIZE);

        try {
            sameWriter.append((CharSequence) prologSizedBuffer);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.io.Writer", e);
        }
    }
}
