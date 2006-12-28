package org.soton.orpheus.tac;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import org.soton.orpheus.jason.OrpheusAgentArch;

import junit.framework.TestCase;

public class TACProxyAgentFactoryTest extends TestCase {
	protected static final Logger logger = Logger.getLogger(TestCase.class.getName());
	
	protected TACProxyAgentFactory agentFactory = null;
	protected OrpheusAgentArch agentArch = null;

	protected void setUp() throws Exception {
		super.setUp();
		this.testGetTACProxyAgentFactory();
		agentArch = new OrpheusAgentArch(TestConstants.TEST_AGENT_SOURCE);
		assertNotNull(agentArch);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		agentFactory = null;
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentFactory.getTACProxyAgentFactory()'
	 */
	public void testGetTACProxyAgentFactory() {
		agentFactory = null;
		agentFactory = TACProxyAgentFactory.getTACProxyAgentFactory();
		assertNotNull("Null reference after invocation of 'getTACProxyAgentFactory()'", agentFactory);
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentFactory.createTACProxyAgent(OrpheusAgentArch)'
	 */
	public void testCreateTACProxyAgentOrpheusAgentArch() {
		TACProxyAgent proxyAgent = agentFactory.createTACProxyAgent(agentArch);
		assertNotNull("Failed to create TACProxyAgent.",proxyAgent);
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentFactory.createTACProxyAgent(OrpheusAgentArch, String)'
	 */
	public void testCreateTACProxyAgentOrpheusAgentArchString() {
		TACProxyAgent proxyAgent = null;
		try {
			proxyAgent = agentFactory.createTACProxyAgent(agentArch, "/agent.conf");
		} catch (URISyntaxException e) {
			assertTrue("Failed to create TACProxyAgent.",false);
		}
		assertNotNull("Failed to create TACProxyAgent.",proxyAgent);
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentFactory.createTACProxyAgent(OrpheusAgentArch, URL)'
	 */
	public void testCreateTACProxyAgentOrpheusAgentArchURL() {
		TACProxyAgent proxyAgent = null;
		try {
			URL url = TACProxyAgentFactoryTest.class.getResource("/agent.conf");
			proxyAgent = agentFactory.createTACProxyAgent(agentArch, url);
		} catch (URISyntaxException e) {
			assertTrue("Failed to create TACProxyAgent.",false);
		}
		assertNotNull("Failed to create TACProxyAgent.",proxyAgent);
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentFactory.getConfig(File)'
	 */
	public void testGetConfig() {
		logger.info("Test case not implemented.");
	}

}
