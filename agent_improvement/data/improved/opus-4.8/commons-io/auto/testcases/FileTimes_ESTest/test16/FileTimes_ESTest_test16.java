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
public class FileTimes_ESTest_test16 extends FileTimes_ESTest_scaffolding {

    /**
     * Subtracting a non-zero number of nanoseconds from a FileTime must yield a
     * different FileTime. Here we start from a FileTime at -2021 Unix seconds and
     * subtract -2021 nanoseconds (i.e. add 2021 nanoseconds), so the result must
     * not be equal to the original.
     */
    @Test(timeout = 4000)
    public void shiftingByNanosProducesDifferentFileTime() throws Throwable {
        FileTime originalTime = FileTimes.fromUnixTime(-2021L);
        FileTime shiftedTime = FileTimes.minusNanos(originalTime, -2021L);

        assertFalse(shiftedTime.equals((Object) originalTime));
    }
}
