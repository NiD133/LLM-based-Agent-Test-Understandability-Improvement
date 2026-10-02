package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testBuilder_File extends AbstractMonitorTest {

    private static final String OBSERVED_DIRECTORY_PATH = "/foo";

    @Test
    void testBuilder_File() {
        final File observedDirectory = new File(OBSERVED_DIRECTORY_PATH);

        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(observedDirectory)
                .getUnchecked();

        assertEquals(observedDirectory, observer.getDirectory());
    }
}
