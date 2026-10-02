import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import java.io.PrintWriter;

/**
 * Minimal JUnit Platform runner used by coverage_runner's PATH C (direct
 * JUnit-5 execution). It runs ONE test class in the current JVM via the
 * JUnit Platform Launcher API — no Maven, no surefire fork, so the JaCoCo
 * `-javaagent` attached to this JVM instruments the test + CUT directly.
 *
 * Requires junit-platform-launcher + a TestEngine (junit-jupiter-engine, and
 * optionally junit-vintage-engine for JUnit 4 classes) on the classpath — all
 * already present in the projects' own test classpaths.
 *
 * Usage:  java -javaagent:jacocoagent.jar=destfile=... -cp <cp> JUnit5Runner <FQN>
 * Exit:   0 = all passed, 1 = at least one test failed/errored.
 */
public class JUnit5Runner {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("JUnit5Runner: missing test class FQN argument");
            System.exit(2);
        }
        String fqn = args[0];
        try {
            Launcher launcher = LauncherFactory.create();
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            launcher.registerTestExecutionListeners(listener);
            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(selectClass(fqn))
                    .build();
            launcher.execute(request);
            TestExecutionSummary summary = listener.getSummary();
            PrintWriter out = new PrintWriter(System.out, true);
            summary.printTo(out);
            if (summary.getTestsFailedCount() > 0) {
                summary.printFailuresTo(out);
                System.exit(1);
            }
            // No tests found is NOT a hard failure here — the caller checks
            // coverage separately; print a marker for diagnostics.
            if (summary.getTestsStartedCount() == 0) {
                System.out.println("JUnit5Runner: WARNING no tests started for " + fqn);
            }
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("JUnit5Runner: launch error for " + fqn);
            t.printStackTrace();
            System.exit(3);
        }
    }
}
