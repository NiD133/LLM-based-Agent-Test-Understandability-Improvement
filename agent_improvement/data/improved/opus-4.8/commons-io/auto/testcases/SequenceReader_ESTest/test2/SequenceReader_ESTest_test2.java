package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SequenceReader_ESTest_test2 extends SequenceReader_ESTest_scaffolding {

    /**
     * Reading from a SequenceReader that wraps a single empty reader should
     * immediately report end-of-stream (-1), since there are no characters to return.
     */
    @Test(timeout = 4000)
    public void readReturnsEofWhenAllReadersAreEmpty() throws Throwable {
        StringReader emptyReader = new StringReader("");
        LinkedHashSet<StringReader> readers = new LinkedHashSet<StringReader>();
        readers.add(emptyReader);

        SequenceReader sequenceReader = new SequenceReader(readers);
        int result = sequenceReader.read();

        int expectedEndOfStream = -1;
        assertEquals(expectedEndOfStream, result);
    }
}
