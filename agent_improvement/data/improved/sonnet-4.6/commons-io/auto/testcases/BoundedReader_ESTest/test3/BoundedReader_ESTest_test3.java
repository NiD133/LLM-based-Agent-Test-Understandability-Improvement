package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test3 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Source has 4 characters; the bound is set well above the source length
        String sourceContent = "@</'";
        int maxChars = 481;
        StringReader stringReader0 = new StringReader(sourceContent);
        BoundedReader boundedReader0 = new BoundedReader(stringReader0, maxChars);

        // reset() without a prior mark() resets charsRead to markedAt's initial value (-1)
        boundedReader0.reset();

        // skip() can advance over at most 4 actual characters in the source,
        // even though the requested skip count exceeds the source length
        long charsSkipped = boundedReader0.skip(maxChars);
        assertEquals(4L, charsSkipped);

        // Place a mark with a read-ahead limit of 1
        boundedReader0.mark(1);

        // After exhausting the source and with a tight read-ahead limit, read() returns EOF (-1)
        int readResult = boundedReader0.read();
        assertEquals(-1, readResult);
    }
}
