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
public class FileTimes_ESTest_test08 extends FileTimes_ESTest_scaffolding {

    /**
     * A FileTime built from a date far outside the range representable as standard
     * Unix time should be reported as not a valid Unix time by isUnixTime.
     *
     * Here the date is the very large millisecond value -116444736000000000, whose
     * equivalent in whole seconds falls well below Integer.MIN_VALUE, so isUnixTime
     * is expected to return false.
     */
    @Test(timeout = 4000)
    public void isUnixTimeReturnsFalseForDateOutsideUnixTimeRange() throws Throwable {
        long millisOutsideUnixTimeRange = -116444736000000000L;
        Date dateOutsideUnixTimeRange = new MockDate(millisOutsideUnixTimeRange);

        FileTime fileTime = FileTimes.toFileTime(dateOutsideUnixTimeRange);
        boolean withinUnixTimeRange = FileTimes.isUnixTime(fileTime);

        assertFalse(withinUnixTimeRange);
    }
}
