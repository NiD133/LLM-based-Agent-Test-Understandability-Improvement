package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test30 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A freshly constructed X5455_ExtendedTimestamp has no create timestamp set,
     * so getCreateJavaTime() should return null.
     */
    @Test(timeout = 4000)
    public void getCreateJavaTimeReturnsNullWhenCreateTimeUnset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        Date createTime = extendedTimestamp.getCreateJavaTime();

        assertNull(createTime);
    }
}
