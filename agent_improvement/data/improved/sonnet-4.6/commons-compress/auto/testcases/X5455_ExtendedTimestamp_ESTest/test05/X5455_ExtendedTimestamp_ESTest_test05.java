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
public class X5455_ExtendedTimestamp_ESTest_test05 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    // Flags byte 85 (0x55 = 0b01010101): only the lowest 3 bits matter per spec.
    // 85 & 0x07 = 5 (0b101): bit0 (modify time) and bit2 (create time) are set,
    // bit1 (access time) is not set.
    private static final byte FLAGS_MODIFY_AND_CREATE_PRESENT = (byte) 85;

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Setting flags with bit0=1 (modify time present) and bit2=1 (create time present).
        extendedTimestamp.setFlags(FLAGS_MODIFY_AND_CREATE_PRESENT);

        // toString() exercises the flag-based conditional formatting logic.
        extendedTimestamp.toString();

        // Bit0 in the flags byte signals that modify time is present.
        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
