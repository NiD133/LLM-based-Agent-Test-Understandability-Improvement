package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test06 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        X5455_ExtendedTimestamp x5455_ExtendedTimestamp0 = new X5455_ExtendedTimestamp();
        Instant instant0 = MockInstant.ofEpochMilli((byte) 4);
        Date date0 = Date.from(instant0);
        x5455_ExtendedTimestamp0.setModifyJavaTime(date0);
        x5455_ExtendedTimestamp0.toString();
        assertEquals((byte) 1, x5455_ExtendedTimestamp0.getFlags());
    }
}
