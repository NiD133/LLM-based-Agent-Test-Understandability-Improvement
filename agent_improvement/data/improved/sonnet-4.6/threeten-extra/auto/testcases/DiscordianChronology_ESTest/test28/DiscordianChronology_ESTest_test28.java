package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DiscordianChronology_ESTest_test28 extends DiscordianChronology_ESTest_scaffolding {

    /**
     * Verifies that {@link DiscordianChronology#eraOf(int)} throws a
     * {@link DateTimeException} when given an invalid (negative) era value.
     * The Discordian calendar only has one valid era (YOLD, value 1), so -1
     * is out of range and must be rejected.
     */
    @Test(timeout = 4000)
    public void test_eraOf_withNegativeValue_throwsDateTimeException() throws Throwable {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;

        try {
            chronology.eraOf(-1);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.chrono.DiscordianEra", e);
        }
    }
}
