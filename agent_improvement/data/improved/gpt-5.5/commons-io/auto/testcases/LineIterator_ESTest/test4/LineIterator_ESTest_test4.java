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
public class LineIterator_ESTest_test4 extends LineIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        StringReader readerContainingSingleLine = new StringReader("_CB");
        LineIterator iterator = new LineIterator(readerContainingSingleLine);

        String nextLine = iterator.next();

        assertEquals("_CB", nextLine);
    }
}
