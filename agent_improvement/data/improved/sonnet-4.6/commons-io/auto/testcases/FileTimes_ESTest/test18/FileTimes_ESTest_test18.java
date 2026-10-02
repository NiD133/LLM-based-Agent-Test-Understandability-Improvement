package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test18 extends FileTimes_ESTest_scaffolding {

    // All six Date fields set to extreme negative to produce a timestamp so far before
    // the Unix epoch that the NTFS conversion overflows and is clamped to Long.MAX_VALUE.
    private static final int EXTREME_NEGATIVE = -2147483646;

    @Test(timeout = 4000)
    public void test_toNtfsTime_dateWithExtremeNegativeFields_returnsLongMaxValue() throws Throwable {
        MockDate extremePastDate = new MockDate(
            EXTREME_NEGATIVE, // year (offset from 1900)
            EXTREME_NEGATIVE, // month
            EXTREME_NEGATIVE, // day
            EXTREME_NEGATIVE, // hour
            EXTREME_NEGATIVE, // minute
            EXTREME_NEGATIVE  // second
        );

        long ntfsTime = FileTimes.toNtfsTime((Date) extremePastDate);

        assertEquals(Long.MAX_VALUE, ntfsTime);
    }
}
