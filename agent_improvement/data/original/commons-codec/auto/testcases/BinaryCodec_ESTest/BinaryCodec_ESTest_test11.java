package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test11 extends BinaryCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        BinaryCodec binaryCodec0 = new BinaryCodec();
        Object object0 = binaryCodec0.decode((Object) "");
        Object object1 = binaryCodec0.decode(object0);
        assertSame(object1, object0);
    }
}
