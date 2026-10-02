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
public class FileTimes_ESTest_test03 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that converting a {@code null} Date yields a {@code null} FileTime,
     * as documented on {@link FileTimes#toFileTime(Date)}.
     */
    @Test(timeout = 4000)
    public void toFileTime_withNullDate_returnsNull() throws Throwable {
        FileTime resultForNullDate = FileTimes.toFileTime((Date) null);

        assertNull(resultForNullDate);
    }
}
