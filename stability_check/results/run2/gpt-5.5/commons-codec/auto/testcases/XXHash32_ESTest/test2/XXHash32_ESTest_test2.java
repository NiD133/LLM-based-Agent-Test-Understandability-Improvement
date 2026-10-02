package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test2 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        final XXHash32 checksum = new XXHash32(0);

        checksum.update(13);
        checksum.update(13);
        checksum.update((int) (byte) 0);
        checksum.update((int) (byte) 0);

        final long actualValue = checksum.getValue();
        final long expectedValue = 2114005244L;

        assertEquals(expectedValue, actualValue);
    }
}
