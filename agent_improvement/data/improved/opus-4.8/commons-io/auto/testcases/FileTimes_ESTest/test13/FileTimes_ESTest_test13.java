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
public class FileTimes_ESTest_test13 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that adding a non-zero number of milliseconds to a FileTime
     * produces a new, distinct FileTime that does not equal the original.
     */
    @Test(timeout = 4000)
    public void plusMillisReturnsDifferentFileTime() throws Throwable {
        final long millisToAdd = 2147483647L;
        FileTime originalTime = FileTime.fromMillis(2147483647L);

        FileTime shiftedTime = FileTimes.plusMillis(originalTime, millisToAdd);

        assertFalse("Shifting the time forward must yield a different FileTime",
                shiftedTime.equals((Object) originalTime));
    }
}
