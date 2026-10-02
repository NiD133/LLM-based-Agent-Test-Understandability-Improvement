package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.attribute.FileTime;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test29 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * A freshly constructed extended-timestamp field has no modify timestamp set,
     * so {@link X5455_ExtendedTimestamp#getModifyFileTime()} should return null.
     */
    @Test(timeout = 4000)
    public void getModifyFileTimeReturnsNullWhenUnset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        FileTime modifyFileTime = extendedTimestamp.getModifyFileTime();

        assertNull(modifyFileTime);
    }
}
