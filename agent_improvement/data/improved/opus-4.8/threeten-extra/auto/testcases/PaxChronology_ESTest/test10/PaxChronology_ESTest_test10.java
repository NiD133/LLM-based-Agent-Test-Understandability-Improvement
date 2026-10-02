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
     * Calling {@code dateNow(ZoneId)} with a null zone must fail fast.
     * The zone is required, so the method rejects it via {@code Objects.requireNonNull},
     * which throws a {@link NullPointerException}.
     */
    @Test(timeout = 4000)
    public void dateNowWithNullZoneThrowsNullPointerException() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;

        try {
            paxChronology.dateNow((ZoneId) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null-check originates from java.util.Objects (the "zone" argument).
            verifyException("java.util.Objects", e);
        }
    }
}
