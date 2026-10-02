package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test03 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting a non-null create time via setCreateTime() automatically
     * sets the create-time-present flag (bit2), and that toString() correctly reflects
     * the updated state without throwing.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Arrange: a fresh extended-timestamp field and a ZipLong value to use as the create time
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        ZipLong createTimeValue = ZipLong.CFH_SIG;

        // Act: store the create time and exercise the string-formatting path
        extendedTimestamp.setCreateTime(createTimeValue);
        extendedTimestamp.toString();

        // Assert: supplying a non-null ZipLong must set bit2, indicating create time is present
        assertTrue(extendedTimestamp.isBit2_createTimePresent());
    }
}
