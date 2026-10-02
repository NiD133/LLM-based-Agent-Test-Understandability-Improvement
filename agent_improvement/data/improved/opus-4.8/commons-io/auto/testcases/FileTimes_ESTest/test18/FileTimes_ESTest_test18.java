package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test18 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that {@link FileTimes#toNtfsTime(Date)} saturates to
     * {@link Long#MAX_VALUE} when the date is so far in the future that its
     * NTFS representation would overflow a 64-bit signed value.
     */
    @Test(timeout = 4000)
    public void toNtfsTimeClampsFarFutureDateToLongMaxValue() throws Throwable {
        // A date built from extreme field values; its millisecond value is large
        // enough that the equivalent NTFS time exceeds Long.MAX_VALUE.
        final int extremeField = -2147483646;
        final Date farFutureDate = new MockDate(
                extremeField, extremeField, extremeField,
                extremeField, extremeField, extremeField);

        final long ntfsTime = FileTimes.toNtfsTime(farFutureDate);

        assertEquals(Long.MAX_VALUE, ntfsTime);
    }
}
