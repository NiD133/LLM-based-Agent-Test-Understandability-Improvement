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
        StringReader sharedReader = new StringReader("^jsd-+DG7(%74v");
        LineIterator iteratorReadingAfterClose = new LineIterator(sharedReader);
        LineIterator iteratorThatClosesReader = new LineIterator(sharedReader);

        LineIterator.closeQuietly(iteratorThatClosesReader);

        try {
            iteratorReadingAfterClose.nextLine();
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.io.LineIterator", e);
        }
    }
}
