package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test07 extends Years_ESTest_scaffolding {

    /**
     * Verifies that calling {@link Years#addTo(Temporal)} with a null temporal
     * throws a {@link NullPointerException}, because the method attempts to call
     * {@code plus} on the given temporal without a null check.
     */
    @Test(timeout = 4000)
    public void addTo_withNullTemporal_throwsNullPointerException() throws Throwable {
        Years oneYear = Years.ONE;

        try {
            oneYear.addTo((Temporal) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException expected) {
            // addTo(null) dereferences the null temporal inside Years, so the
            // exception originates from the Years class with no message.
            verifyException("org.threeten.extra.Years", expected);
        }
    }
}
