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
public class FileTimes_ESTest_test15 extends FileTimes_ESTest_scaffolding {

    /**
     * Subtracting zero seconds from a FileTime should leave it unchanged,
     * so the result must be equal to the original FileTime.
     */
    @Test(timeout = 4000)
    public void minusZeroSecondsReturnsEqualFileTime() throws Throwable {
        FileTime originalTime = FileTimes.now();

        FileTime resultTime = FileTimes.minusSeconds(originalTime, 0L);

        assertTrue(resultTime.equals((Object) originalTime));
    }
}
