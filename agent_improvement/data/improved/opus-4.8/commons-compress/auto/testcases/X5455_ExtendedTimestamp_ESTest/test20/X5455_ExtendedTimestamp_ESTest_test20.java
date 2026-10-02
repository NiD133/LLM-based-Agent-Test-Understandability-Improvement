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
public class X5455_ExtendedTimestamp_ESTest_test20 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Two X5455 extended-timestamp fields whose flags and timestamps differ must
     * not be considered equal:
     *   - the first field only has its flags byte set (no timestamp values),
     *   - the second field has a create time set, which both turns on the
     *     create-time bit and stores a ZipLong value.
     * Because their create-time presence and stored values differ, equals() returns false.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseWhenFlagsAndCreateTimeDiffer() throws Throwable {
        X5455_ExtendedTimestamp fieldWithRawFlags = new X5455_ExtendedTimestamp();
        fieldWithRawFlags.setFlags((byte) -76);

        X5455_ExtendedTimestamp fieldWithCreateTime = new X5455_ExtendedTimestamp();
        fieldWithCreateTime.setCreateTime(ZipLong.DD_SIG);

        boolean fieldsAreEqual = fieldWithRawFlags.equals(fieldWithCreateTime);

        // Setting a non-null create time turns on the create-time presence bit.
        assertTrue(fieldWithCreateTime.isBit2_createTimePresent());
        assertFalse(fieldsAreEqual);
    }
}
