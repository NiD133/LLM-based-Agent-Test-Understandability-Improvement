package org.threeten.extra;

import static org.junit.Assert.assertNotSame;

import java.time.chrono.HijrahDate;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockHijrahDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test03 extends Months_ESTest_scaffolding {

    /**
     * Subtracting a non-zero amount of months from a date returns a brand new
     * temporal instance rather than mutating or returning the original date.
     */
    @Test(timeout = 4000)
    public void subtractFrom_returnsNewTemporalInstance() throws Throwable {
        Months oneMonth = Months.ONE;
        HijrahDate originalDate = MockHijrahDate.now();

        Temporal adjustedDate = oneMonth.subtractFrom(originalDate);

        assertNotSame(originalDate, adjustedDate);
    }
}
