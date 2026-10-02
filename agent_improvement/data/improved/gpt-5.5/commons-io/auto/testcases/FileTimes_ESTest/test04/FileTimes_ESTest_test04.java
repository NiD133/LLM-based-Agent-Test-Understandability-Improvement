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
public class FileTimes_ESTest_test04 extends FileTimes_ESTest_scaffolding {

    private static final long MOCK_DATE_MILLIS = -116444736000000000L;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final MockDate sourceDate = new MockDate(MOCK_DATE_MILLIS);

        final FileTime fileTime = FileTimes.toFileTime(sourceDate);
        final Date convertedDate = FileTimes.toDate(fileTime);

        assertEquals("Sat Dec 12 00:00:00 GMT 3687943", convertedDate.toString());
    }
}
