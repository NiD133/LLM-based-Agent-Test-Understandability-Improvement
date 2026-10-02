package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test35 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A newly constructed X5455_ExtendedTimestamp should have all timestamp flags cleared,
     * so isBit0_modifyTimePresent() must return false before any flags are set.
     */
    @Test(timeout = 4000)
    public void newInstance_shouldHaveModifyTimeFlagUnset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        boolean isModifyTimePresent = extendedTimestamp.isBit0_modifyTimePresent();

        assertFalse("Modify-time flag (bit0) should not be set on a default-constructed instance", isModifyTimePresent);
    }
}
