package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test41 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test41() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Passing null clears the create-time ZipLong internally, which must also
        // clear CREATE_TIME_BIT (bit2) from the flags byte.
        extendedTimestamp.setCreateJavaTime((Date) null);

        assertEquals((byte) 0, extendedTimestamp.getFlags());
        assertFalse(extendedTimestamp.isBit2_createTimePresent());
    }
}
