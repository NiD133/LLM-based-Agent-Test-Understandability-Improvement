package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.chrono.MinguoDate;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test21 extends Half_ESTest_scaffolding {

    // Half.from() must handle non-ISO chronologies by converting to LocalDate first;
    // a Minguo date whose month falls in Jan–Jun resolves to H1.
    @Test(timeout = 4000)
    public void test_fromNonIsoChronology_minguoDateInFirstHalf_returnsH1() throws Throwable {
        MinguoDate minguoDate = MockMinguoDate.now();
        Half result = Half.from(minguoDate);
        assertEquals(Half.H1, result);
    }
}
