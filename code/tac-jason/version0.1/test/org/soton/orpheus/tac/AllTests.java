package org.soton.orpheus.tac;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static void main(String[] args) {
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.soton.orpheus.tac");
		//$JUnit-BEGIN$
		suite.addTestSuite(TACProxyAgentFactoryTest.class);
		suite.addTestSuite(TACProxyAgentImplTest.class);
		//$JUnit-END$
		return suite;
	}

}
