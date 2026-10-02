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
public class NullReader_ESTest_test12 extends NullReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        NullReader emptyReader = new NullReader();
        char[] readBuffer = new char[4];

        emptyReader.read(readBuffer);

        try {
            emptyReader.skip(2147483640L);
            fail("Expecting exception: IOException");
        } catch (IOException expectedException) {
            //
            // Skip after end of file
            //
            verifyException("org.apache.commons.io.input.NullReader", expectedException);
        }
    }
}
