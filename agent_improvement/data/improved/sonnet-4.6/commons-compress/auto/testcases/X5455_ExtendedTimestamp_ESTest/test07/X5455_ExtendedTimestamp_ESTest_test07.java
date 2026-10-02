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
public class X5455_ExtendedTimestamp_ESTest_test07 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Setting the modify time to null should clear the modify-time-present flag.
     *
     * When a null Date is passed to setModifyJavaTime(), the internal modify
     * timestamp is removed, which clears bit 0 from the flags byte. As a result,
     * getFlags() must return 0 (no timestamp bits set) and isBit0_modifyTimePresent()
     * must return false.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Setting modify time to null signals that no modify timestamp is present
        extendedTimestamp.setModifyJavaTime((Date) null);

        // Flags byte must be 0: null date clears the MODIFY_TIME_BIT (bit 0)
        assertEquals((byte) 0, extendedTimestamp.getFlags());

        // Bit 0 must be unset because no modify time was supplied
        assertFalse(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
