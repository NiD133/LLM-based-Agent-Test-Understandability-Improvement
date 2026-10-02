package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LineIterator_ESTest_test5 extends LineIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        StringReader stringReader0 = new StringReader("org.apache.commons.io.LineIterator");
        LineIterator lineIterator0 = new LineIterator(stringReader0);
        // Undeclared exception!
        try {
            lineIterator0.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //
            // remove not supported
            //
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
