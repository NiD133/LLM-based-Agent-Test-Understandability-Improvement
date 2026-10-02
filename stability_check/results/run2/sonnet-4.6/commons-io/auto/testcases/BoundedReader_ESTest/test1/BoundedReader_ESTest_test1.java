package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringReader;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BoundedReader_ESTest_test1 extends BoundedReader_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_readAfterSkipAndMarkOnEmptyReaderReturnsEOF() throws Throwable {
        StringReader emptySource = new StringReader("");
        BoundedReader boundedReader = new BoundedReader(emptySource, 511);
        boundedReader.reset();
        boundedReader.skip(511);
        boundedReader.mark(511);
        int result = boundedReader.read();
        assertEquals((-1), result);
    }
}
