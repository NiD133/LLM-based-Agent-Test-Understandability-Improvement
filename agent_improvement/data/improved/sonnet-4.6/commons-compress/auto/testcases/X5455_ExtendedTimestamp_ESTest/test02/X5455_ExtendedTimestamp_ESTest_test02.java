package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Instant;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test02 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void setModifyJavaTime_setsModifyTimePresentFlag() throws Throwable {
        // Setting a non-null modify time should activate bit0 (modify-time-present flag)
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        Instant epochPlusFourMillis = MockInstant.ofEpochMilli(4L);
        Date modifyDate = Date.from(epochPlusFourMillis);
        extendedTimestamp.setModifyJavaTime(modifyDate);

        extendedTimestamp.getCentralDirectoryData();

        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
