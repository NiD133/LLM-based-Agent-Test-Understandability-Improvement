package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test01 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        BHSDCodec bHSDCodec0 = Codec.DELTA5;
        PopulationCodec populationCodec0 = new PopulationCodec(bHSDCodec0, 4, bHSDCodec0);
        RunCodec runCodec0 = new RunCodec(4, populationCodec0, bHSDCodec0);
        int[] intArray0 = CodecEncoding.getSpecifier(runCodec0, bHSDCodec0);
        assertNotNull(intArray0);
        assertArrayEquals(new int[] { 133, 144, 0 }, intArray0);
    }
}
