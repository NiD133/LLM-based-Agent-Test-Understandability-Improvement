package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.OffsetDateTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test15 extends Half_ESTest_scaffolding {

    /**
     * Verifies that Half.from() correctly derives H1 from an OffsetDateTime
     * whose mocked "now" falls in the first half of the year (January–June).
     */
    @Test(timeout = 4000)
    public void test_fromOffsetDateTime_returnsFirstHalf() throws Throwable {
        OffsetDateTime dateTimeInFirstHalf = MockOffsetDateTime.now();
        Half result = Half.from(dateTimeInFirstHalf);
        assertEquals(Half.H1, result);
    }
}
