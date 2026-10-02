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

    /**
     * Converting a Date to a FileTime and back to a Date should preserve the
     * original moment in time. Here the Date is built from an extreme negative
     * epoch-millisecond value, which corresponds to a date far in the future
     * when interpreted (the value overflows into the year 3687943).
     */
    @Test(timeout = 4000)
    public void toDateAfterToFileTimeRoundTripsTheOriginalDate() throws Throwable {
        long epochMillis = -116444736000000000L;
        Date originalDate = new MockDate(epochMillis);

        FileTime fileTime = FileTimes.toFileTime(originalDate);
        Date roundTrippedDate = FileTimes.toDate(fileTime);

        assertEquals("Sat Dec 12 00:00:00 GMT 3687943", roundTrippedDate.toString());
    }
}
