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
public class LineIterator_ESTest_test0 extends LineIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        StringReader stringReader0 = new StringReader("^jsd-+DG7(%74v");
        LineIterator lineIterator0 = new LineIterator(stringReader0);
        LineIterator lineIterator1 = new LineIterator(stringReader0);
        LineIterator.closeQuietly(lineIterator1);
        // Undeclared exception!
        try {
            lineIterator0.nextLine();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // java.io.IOException: Stream closed
            //
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
