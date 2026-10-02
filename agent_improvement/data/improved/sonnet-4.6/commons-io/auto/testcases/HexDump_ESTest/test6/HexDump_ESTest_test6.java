package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PipedWriter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFile;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintWriter;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class HexDump_ESTest_test6 extends HexDump_ESTest_scaffolding {

    /**
     * Verifies that HexDump can be instantiated without throwing an exception.
     * Although HexDump is a utility class, it exposes a public constructor,
     * so this test confirms that construction succeeds and produces a non-null object.
     */
    @Test(timeout = 4000)
    public void testConstructorCreatesInstance() throws Throwable {
        HexDump hexDump = new HexDump();
        assertNotNull(hexDump);
    }
}
