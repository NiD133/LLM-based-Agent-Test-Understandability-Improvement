package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.ZoneId;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test10 extends PaxChronology_ESTest_scaffolding {

    /**
     * Verifies that calling dateNow(ZoneId) with a null zone throws NullPointerException.
     * The JDK's Objects.requireNonNull check inside Clock.system(zone) enforces that
     * the zone argument must not be null, with the message "zone".
     */
    @Test(timeout = 4000)
    public void testDateNowWithNullZoneThrowsNullPointerException() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;

        try {
            paxChronology.dateNow((ZoneId) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.util.Objects", e);
        }
    }
}
