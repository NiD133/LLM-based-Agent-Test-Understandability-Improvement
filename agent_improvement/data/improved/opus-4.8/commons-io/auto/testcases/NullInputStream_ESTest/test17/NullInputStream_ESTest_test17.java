package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test17 extends NullInputStream_ESTest_scaffolding {

    /**
     * The default constructor builds an empty stream that supports marking,
     * so a freshly created instance reports mark support as true.
     */
    @Test(timeout = 4000)
    public void defaultStreamSupportsMark() throws Throwable {
        NullInputStream defaultStream = new NullInputStream();

        // Reading the position on a fresh stream leaves it untouched.
        defaultStream.getPosition();

        assertTrue("Default NullInputStream should support mark()",
                defaultStream.markSupported());
    }
}
