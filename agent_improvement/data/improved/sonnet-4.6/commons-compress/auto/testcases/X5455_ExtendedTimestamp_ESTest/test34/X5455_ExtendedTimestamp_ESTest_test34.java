package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test34 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test34() throws Throwable {
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();
        // FileTime from 2 milliseconds since epoch — any non-null value triggers bit0
        FileTime modifyFileTime = FileTime.fromMillis((byte) 2);
        timestamp.setModifyFileTime(modifyFileTime);
        // Setting a non-null modify time must set bit0 (MODIFY_TIME_BIT) in the flags byte
        assertTrue(timestamp.isBit0_modifyTimePresent());
    }
}
