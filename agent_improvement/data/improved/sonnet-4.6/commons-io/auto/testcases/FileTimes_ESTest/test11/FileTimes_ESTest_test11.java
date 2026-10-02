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

    // Subtracting a negative millisecond value effectively adds time,
    // so the resulting FileTime must differ from the original.
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        FileTime originalTime = FileTimes.fromUnixTime(-2021L);
        FileTime adjustedTime = FileTimes.minusMillis(originalTime, -2021L);
        assertNotEquals(adjustedTime, originalTime);
    }
}
