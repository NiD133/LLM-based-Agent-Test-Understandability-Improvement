package org.apache.commons.io.output;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XmlStreamWriter_ESTest_test1 extends XmlStreamWriter_ESTest_scaffolding {

    /**
     * Flushing an XmlStreamWriter after it has been closed must fail, because
     * close() materializes the underlying writer and then closes it; a
     * subsequent flush() therefore operates on an already-closed writer and
     * raises an IOException.
     */
    @Test(timeout = 4000)
    public void flushAfterCloseThrowsIOException() throws Throwable {
        MockFile targetFile = new MockFile("DQf{xmx %q-", "DQf{xmx %q-");
        XmlStreamWriter writer = new XmlStreamWriter(targetFile);
        writer.close();

        try {
            writer.flush();
            fail("Expected an IOException when flushing a closed XmlStreamWriter");
        } catch (IOException expected) {
            // expected: the underlying writer is already closed
        }
    }
}
