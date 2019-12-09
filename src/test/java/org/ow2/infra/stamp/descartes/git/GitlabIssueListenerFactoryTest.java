package org.ow2.infra.stamp.descartes.git;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ow2.infra.stamp.descartes.git.GitlabIssueListenerFactory;

public class GitlabIssueListenerFactoryTest {

	@Test
	public void testName() {
		GitlabIssueListenerFactory factory = new GitlabIssueListenerFactory();
		assertEquals("GITLAB-ISSUES", factory.name());
	}

}
