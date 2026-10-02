package org.apache.commons.io;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import org.apache.commons.io.ByteOrderMark;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true)
public class ByteOrderMark_ESTest_test13 extends ByteOrderMark_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        ByteOrderMark utf32LittleEndianBom = ByteOrderMark.UTF_32LE;

        int bomLength = utf32LittleEndianBom.length();

        assertEquals(4, bomLength);
    }
}
