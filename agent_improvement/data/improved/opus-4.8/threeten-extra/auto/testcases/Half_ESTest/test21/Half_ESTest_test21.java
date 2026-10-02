package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.chrono.MinguoDate;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test21 extends Half_ESTest_scaffolding {

    /**
     * {@link Half#from(java.time.temporal.TemporalAccessor)} should accept a non-ISO
     * temporal (a Minguo date) by first converting it to a {@code LocalDate}.
     * The mocked "now" is fixed in the first half of the year, so the result is H1.
     */
    @Test(timeout = 4000)
    public void from_minguoDateInFirstHalfOfYear_returnsH1() throws Throwable {
        MinguoDate firstHalfMinguoDate = MockMinguoDate.now();

        Half half = Half.from(firstHalfMinguoDate);

        assertEquals(Half.H1, half);
    }
}
