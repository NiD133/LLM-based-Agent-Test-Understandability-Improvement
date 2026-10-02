package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link FileAlterationObserver.Builder#setFile(File)} configures the
 * observer's directory, so the built observer reports that same file from
 * {@link FileAlterationObserver#getDirectory()}.
 */
public class FileAlterationObserverTest_testBuilder_File extends AbstractMonitorTest {

    /** Arbitrary path used to configure the builder; it need not exist on disk. */
    private static final String OBSERVED_PATH = "/foo";

    @Test
    void testBuilder_File() {
        final File observedDirectory = new File(OBSERVED_PATH);

        // Build an observer from the file, using getUnchecked() to avoid the checked IOException.
        final FileAlterationObserver observer = FileAlterationObserver.builder()
                .setFile(observedDirectory)
                .getUnchecked();

        // The observer should expose exactly the file it was configured with.
        assertEquals(observedDirectory, observer.getDirectory());
    }
}
