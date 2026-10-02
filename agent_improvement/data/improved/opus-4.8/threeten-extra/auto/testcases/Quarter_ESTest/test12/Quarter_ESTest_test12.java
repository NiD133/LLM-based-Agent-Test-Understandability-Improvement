package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test12 extends Quarter_ESTest_scaffolding {

    /**
     * Calling {@link Quarter#getLong(TemporalField)} with a {@code null} field must fail.
     * <p>
     * A null field is neither {@code QUARTER_OF_YEAR} nor a {@code ChronoField}, so the
     * implementation falls through to {@code field.getFrom(this)}, which dereferences the
     * null field and throws a {@code NullPointerException} (with no message).
     */
    @Test(timeout = 4000)
    public void getLong_withNullField_throwsNullPointerException() throws Throwable {
        Quarter quarter = Quarter.Q2;

        try {
            quarter.getLong((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The NPE originates inside Quarter and carries no message.
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
