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
public class FileTimes_ESTest_test05 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that {@link FileTimes#toDate(FileTime)} returns {@code null}
     * when given a {@code null} FileTime, as documented by the method contract.
     */
    @Test(timeout = 4000)
    public void toDate_returnsNull_whenFileTimeIsNull() throws Throwable {
        Date result = FileTimes.toDate((FileTime) null);

        assertNull("toDate(null) should return null", result);
    }
}
