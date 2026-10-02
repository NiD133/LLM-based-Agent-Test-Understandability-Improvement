package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.PipedReader;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test1 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        final int characterLimit = 511;
        final StringReader emptyReader = new StringReader("");
        final BoundedReader boundedReader = new BoundedReader(emptyReader, characterLimit);

        boundedReader.reset();
        boundedReader.skip(characterLimit);
        boundedReader.mark(characterLimit);

        final int readResult = boundedReader.read();

        assertEquals(-1, readResult);
    }
}
