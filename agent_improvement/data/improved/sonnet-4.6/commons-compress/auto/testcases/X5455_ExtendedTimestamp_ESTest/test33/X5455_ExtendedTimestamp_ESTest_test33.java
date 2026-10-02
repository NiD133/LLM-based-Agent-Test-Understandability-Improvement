package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test33 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting an access time via setAccessJavaTime() automatically sets
     * the bit1_accessTimePresent flag, and that hashCode() can be called without error
     * once the access time is populated.
     */
    @Test(timeout = 4000)
    public void test33() throws Throwable {
        // Arrange
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        MockDate accessDate = new MockDate();

        // Act
        extendedTimestamp.setAccessJavaTime(accessDate);
        extendedTimestamp.hashCode();

        // Assert: setting a non-null access time must activate the access-time-present flag
        assertTrue(extendedTimestamp.isBit1_accessTimePresent());
    }
}
