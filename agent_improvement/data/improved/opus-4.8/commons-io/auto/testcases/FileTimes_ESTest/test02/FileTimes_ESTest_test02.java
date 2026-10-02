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
public class FileTimes_ESTest_test02 extends FileTimes_ESTest_scaffolding {

    /**
     * Converting a Date so far in the past that its NTFS representation underflows
     * the 64-bit range should saturate to {@link Long#MIN_VALUE} rather than overflow.
     */
    @Test(timeout = 4000)
    public void toNtfsTime_withExtremelyEarlyDate_saturatesToLongMinValue() throws Throwable {
        // A Java time (milliseconds since epoch) extreme enough that the NTFS
        // conversion falls below Long.MIN_VALUE.
        Date extremelyEarlyDate = new MockDate(-116444736000000000L);

        long ntfsTime = FileTimes.toNtfsTime(extremelyEarlyDate);

        assertEquals(Long.MIN_VALUE, ntfsTime);
    }
}
