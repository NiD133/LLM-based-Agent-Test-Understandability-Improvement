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
     * Half.from should extract the half-of-year from any temporal that carries a
     * date. The mocked "now" supplied by EvoSuite falls in the first half of the
     * year (January to June), so the resulting half must be H1.
     */
    @Test(timeout = 4000)
    public void from_offsetDateTimeInFirstHalfOfYear_returnsH1() throws Throwable {
        OffsetDateTime dateInFirstHalf = MockOffsetDateTime.now();

        Half half = Half.from(dateInFirstHalf);

        assertEquals(Half.H1, half);
    }
}
