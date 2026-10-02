package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test01 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CSVFormat.Builder cSVFormat_Builder0 = CSVFormat.Builder.create();
        String[] stringArray0 = new String[2];
        stringArray0[0] = "*;Ax}g<";
        stringArray0[1] = "*;Ax}g<";
        CSVFormat.Builder cSVFormat_Builder1 = cSVFormat_Builder0.setHeader(stringArray0);
        CSVFormat cSVFormat0 = cSVFormat_Builder1.get();
        CSVParser cSVParser0 = CSVParser.parse("*;Ax}g<", cSVFormat0);
        CSVRecord cSVRecord0 = new CSVRecord(cSVParser0, stringArray0, "*;Ax}g<", (-1013L), (-1013L), (-1013L));
        Map<String, String> map0 = cSVRecord0.toMap();
        assertEquals((-1013L), cSVRecord0.getCharacterPosition());
        assertEquals(1, map0.size());
        assertEquals((-1013L), cSVRecord0.getRecordNumber());
        assertEquals((-1013L), cSVRecord0.getBytePosition());
    }
}
