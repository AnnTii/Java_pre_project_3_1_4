package spring_boot_security;

import org.junit.jupiter.api.*;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.boot.test.context.SpringBootTest;
import spring_boot_security.service.UserServiceTest;

import java.io.PrintWriter;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringBootSecurityDemoApplicationTests {

	public static void main(String[] args) {

		Launcher launcher = LauncherFactory.create();
//		launcher.registerLauncherDiscoveryListeners();

		SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
//		launcher.registerTestExecutionListeners();
//		launcher.registerTestExecutionListeners(summaryGeneratingListener);

		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
				.request()
				.selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
//				.listeners()
				.filters(TagFilter.includeTags("userServiceTest"))
				.build();

		launcher.execute(request, summaryGeneratingListener);
//		launcher.execute(request);

		try (PrintWriter writer = new PrintWriter(System.out)) {
			summaryGeneratingListener.getSummary().printTo(writer);
		}
	}
}