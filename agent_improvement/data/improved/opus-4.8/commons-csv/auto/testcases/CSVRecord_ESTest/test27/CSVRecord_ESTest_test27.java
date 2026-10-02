package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test27 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single line of input and verifies the metadata exposed by the
     * resulting {@link CSVRecord}: it is the first record, holds exactly one
     * value (the whole line, since it contains no delimiter), and starts at the
     * very beginning of the source stream.
     */
    @Test(timeout = 4000)
    public void parsingSingleFieldLineYieldsFirstRecordAtStreamStart() throws Throwable {
        String singleFieldLine = "org.apache.commons.io.input.UncheckedBufferedReader$Builder";

        CSVParser parser = CSVParser.parse(singleFieldLine, CSVFormat.ORACLE);
        CSVRecord firstRecord = parser.nextRecord();

        String[] values = firstRecord.values();
        assertEquals("Line without a delimiter is a single value", 1, values.length);
        assertEquals("First parsed record is numbered 1", 1L, firstRecord.getRecordNumber());
        assertEquals("Record starts at the first character", 0L, firstRecord.getCharacterPosition());
        assertEquals("Record starts at the first byte", 0L, firstRecord.getBytePosition());
    }
}
