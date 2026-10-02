package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test10 extends CSVRecord_ESTest_scaffolding {

    /**
     * A record whose value count does not match its parser's header mapping is
     * reported as inconsistent, while its size and position accessors still
     * return the values supplied at construction time.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // The header has two columns, but both use the same name. Because the
        // header map keys are unique, the parser ends up with a single mapping.
        final String columnName = "*;Ax}g<";
        final String[] values = { columnName, columnName };

        final CSVFormat format = CSVFormat.Builder.create()
                .setHeader(values)
                .get();
        final CSVParser parser = CSVParser.parse(columnName, format);

        // Build a record with two values but only one distinct header mapping.
        final long position = -1013L;
        final CSVRecord record = new CSVRecord(parser, values, columnName, position, position, position);

        final boolean consistent = record.isConsistent();

        // The record carries two values, but the header maps only one column,
        // so the sizes disagree and the record is inconsistent.
        assertFalse(consistent);
        assertEquals(2, record.size());

        // The position values are returned exactly as supplied.
        assertEquals(-1013L, record.getBytePosition());
        assertEquals(-1013L, record.getCharacterPosition());
        assertEquals(-1013L, record.getRecordNumber());
    }
}
