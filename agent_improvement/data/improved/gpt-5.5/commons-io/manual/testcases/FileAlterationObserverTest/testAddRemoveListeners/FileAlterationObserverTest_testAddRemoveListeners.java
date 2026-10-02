package org.apache.commons.io.monitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class FileAlterationObserverTest_testAddRemoveListeners extends AbstractMonitorTest {

    private static final String PATH_STRING_FIXTURE = "/foo";

    /**
     * Test add/remove listeners.
     */
    @Test
    void testAddRemoveListeners() {
        final FileAlterationObserver observer = FileAlterationObserver.builder().setFile(PATH_STRING_FIXTURE).getUnchecked();

        // Null listeners are ignored when adding and removing.
        observer.addListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[1]");

        observer.removeListener(null);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[2]");

        // A registered listener is returned by the listener iterator.
        final FileAlterationListenerAdaptor listener = new FileAlterationListenerAdaptor();
        observer.addListener(listener);

        final Iterator<FileAlterationListener> listeners = observer.getListeners().iterator();
        assertTrue(listeners.hasNext(), "Listeners[3]");
        assertEquals(listener, listeners.next(), "Added");
        assertFalse(listeners.hasNext(), "Listeners[4]");

        // Removing the listener leaves the observer with no listeners.
        observer.removeListener(listener);
        assertFalse(observer.getListeners().iterator().hasNext(), "Listeners[5]");
    }
}
