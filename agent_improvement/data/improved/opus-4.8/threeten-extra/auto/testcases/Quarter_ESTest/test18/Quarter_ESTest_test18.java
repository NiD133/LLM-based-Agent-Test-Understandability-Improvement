package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test18 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.from(...) must reject a TemporalAccessor that carries no
     * quarter-of-year information. A ZoneOffset only describes a time-zone
     * offset, so the conversion cannot succeed and a DateTimeException is
     * expected, thrown from within the Quarter class.
     */
    @Test(timeout = 4000)
    public void from_zoneOffsetWithoutQuarterInfo_throwsDateTimeException() throws Throwable {
        ZoneOffset offsetWithoutQuarter = ZoneOffset.MIN;

        try {
            Quarter.from(offsetWithoutQuarter);
            fail("Expected DateTimeException: a ZoneOffset has no quarter-of-year to extract");
        } catch (DateTimeException expected) {
            // Message: "Unable to obtain Quarter from TemporalAccessor: -18:00 of type java.time.ZoneOffset"
            verifyException("org.threeten.extra.Quarter", expected);
        }
    }
}
