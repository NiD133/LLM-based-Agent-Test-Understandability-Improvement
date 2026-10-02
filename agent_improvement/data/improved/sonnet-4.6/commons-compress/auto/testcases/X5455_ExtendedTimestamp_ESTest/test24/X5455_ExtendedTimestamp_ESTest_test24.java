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
public class X5455_ExtendedTimestamp_ESTest_test24 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that equals() returns false when compared to an object of a
     * completely unrelated type, satisfying the equals() contract requirement
     * that instances must not be equal to objects of different classes.
     */
    @Test(timeout = 4000)
    public void test24() throws Throwable {
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();
        Object unrelatedObject = new Object();

        boolean isEqual = timestamp.equals(unrelatedObject);

        assertFalse(isEqual);
    }
}
