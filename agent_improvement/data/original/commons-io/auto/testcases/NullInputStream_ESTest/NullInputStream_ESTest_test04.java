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
public class NullInputStream_ESTest_test04 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream(1L, false, false);
        long long0 = nullInputStream0.INSTANCE.skip(0L);
        assertEquals(1, nullInputStream0.available());
        assertFalse(nullInputStream0.markSupported());
        assertEquals((-1L), long0);
    }
}
