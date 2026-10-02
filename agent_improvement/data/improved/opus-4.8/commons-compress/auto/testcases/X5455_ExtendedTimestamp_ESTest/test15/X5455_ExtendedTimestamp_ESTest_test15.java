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
public class X5455_ExtendedTimestamp_ESTest_test15 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting a (non-null) modify time should flip on the "modify time present"
     * flag bit (bit 0), as reported by {@link X5455_ExtendedTimestamp#isBit0_modifyTimePresent()}.
     */
    @Test(timeout = 4000)
    public void settingModifyTimeMarksModifyTimeAsPresent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        Date modifyTime = Date.from(MockInstant.ofEpochMilli(4L));
        extendedTimestamp.setModifyJavaTime(modifyTime);

        // hashCode() exercised here for coverage; its value is not asserted.
        extendedTimestamp.hashCode();

        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
