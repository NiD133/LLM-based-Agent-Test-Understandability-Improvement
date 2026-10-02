package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test11 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that subtracting a non-zero number of milliseconds from a FileTime
     * yields a FileTime that differs from the original.
     */
    @Test(timeout = 4000)
    public void minusMillisShiftsFileTimeSoItDiffersFromOriginal() throws Throwable {
        final long unixSeconds = -2021L;
        final long millisToSubtract = -2021L;

        FileTime originalTime = FileTimes.fromUnixTime(unixSeconds);
        FileTime shiftedTime = FileTimes.minusMillis(originalTime, millisToSubtract);

        assertFalse("Shifting the time by a non-zero amount should change it",
                shiftedTime.equals((Object) originalTime));
    }
}
