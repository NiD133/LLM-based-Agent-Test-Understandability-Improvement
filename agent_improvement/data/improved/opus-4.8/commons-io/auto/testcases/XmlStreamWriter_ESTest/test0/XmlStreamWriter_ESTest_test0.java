package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.DataOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test0 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * A writer built via the Builder, without overriding the default encoding,
     * should report UTF-8 as its default encoding.
     */
    @Test(timeout = 4000)
    public void defaultEncodingIsUtf8WhenBuiltFromOutputStream() throws Throwable {
        // Provide some destination output stream for the writer to wrap.
        PipedInputStream sink = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(sink);
        DataOutputStream outputStream = new DataOutputStream(pipedOutputStream);

        // Build the XmlStreamWriter targeting that output stream.
        XmlStreamWriter.Builder builder = XmlStreamWriter.builder();
        builder.setOutputStream(outputStream);
        XmlStreamWriter writer = builder.get();

        // The Builder defaults the charset to UTF-8.
        assertEquals("UTF-8", writer.getDefaultEncoding());
    }
}
