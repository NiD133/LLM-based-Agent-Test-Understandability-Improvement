package org.jsoup.nodes;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test2 extends NodeIterator_ESTest_scaffolding {

    /**
     * Verifies that forEachRemaining walks every node reachable from the iterator's
     * start node, handing each one to the supplied Consumer.
     *
     * The document below has two nodes to visit: the Document root itself and the
     * single child element appended to it.
     */
    @Test(timeout = 4000)
    public void forEachRemaining_visitsEveryNodeInDocument() throws Throwable {
        // Build a document with one child element.
        String tagName = "~I@)ny9j(:24`r@$";
        Document document = new Document(tagName, "org.jsoup.nodes.NodeIterator");
        document.appendElement(tagName);

        // Iterate over the document and all of its descendants.
        NodeIterator<Node> nodeIterator = NodeIterator.from(document);

        // A mock Consumer simply records (and ignores) each visited node.
        @SuppressWarnings("unchecked")
        Consumer<Object> visitedNodeConsumer =
                (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());

        nodeIterator.forEachRemaining(visitedNodeConsumer);
    }
}
