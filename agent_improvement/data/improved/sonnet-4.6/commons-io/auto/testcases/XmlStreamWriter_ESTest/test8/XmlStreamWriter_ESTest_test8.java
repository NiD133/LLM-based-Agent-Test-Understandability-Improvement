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
     * Verifies that getEncoding() throws NullPointerException when called before any content
     * has been written. The internal charset field remains null until detectEncoding() sets it
     * during the first write(), so calling getEncoding() prematurely dereferences null.
     */
    @Test(timeout = 4000)
    public void test_getEncoding_beforeAnyWrite_throwsNullPointerException() throws Throwable {
        File tempFile = MockFile.createTempFile("r>mbV&/Nog5v0I%", "r>mbV&/Nog5v0I%");
        XmlStreamWriter writer = new XmlStreamWriter(tempFile);

        // charset is null until write() triggers encoding detection
        try {
            writer.getEncoding();
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.io.output.XmlStreamWriter", e);
        }
    }
}
