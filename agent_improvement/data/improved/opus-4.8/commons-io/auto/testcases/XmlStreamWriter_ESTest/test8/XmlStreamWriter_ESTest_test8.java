package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.File;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test8 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Verifies that calling getEncoding() before anything has been written
     * throws a NullPointerException.
     *
     * The encoding is only detected (and the internal charset assigned) once
     * the XML prolog is written. Until then the charset field is null, so
     * getEncoding() — which returns charset.name() — dereferences null.
     */
    @Test(timeout = 4000)
    public void getEncodingBeforeWritingThrowsNullPointerException() throws Throwable {
        File outputFile = MockFile.createTempFile("xmlStreamWriterTest", ".xml");
        XmlStreamWriter xmlStreamWriter = new XmlStreamWriter(outputFile);

        try {
            xmlStreamWriter.getEncoding();
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // charset has not been detected yet, so charset.name() fails.
            verifyException("org.apache.commons.io.output.XmlStreamWriter", e);
        }
    }
}
