package org.soton.orpheus.tac;

import java.util.logging.Logger;

import junit.framework.TestCase;

import org.soton.orpheus.jason.OrpheusAgentArch;

public class TACProxyAgentImplTest extends TestCase {
	protected static final Logger logger = Logger.getLogger(TestCase.class.getName());
	
	protected OrpheusAgentArch orpheusAgentArch = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		orpheusAgentArch = new OrpheusAgentArch(TestConstants.TEST_AGENT_SOURCE);
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'org.soton.orhpeus.tac.TACProxyAgentImpl.quoteUpdated(int)'
	 */
	public void testQuoteUpdatedInt() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orhpeus.tac.TACProxyAgentImpl.gameStarted()'
	 */
	public void testGameStarted() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orhpeus.tac.TACProxyAgentImpl.gameStopped()'
	 */
	public void testGameStopped() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orhpeus.tac.TACProxyAgentImpl.auctionClosed(int)'
	 */
	public void testAuctionClosed() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orhpeus.tac.TACProxyAgentImpl.TACProxyAgentImpl(OrpheusAgentArch)'
	 */
	public void testTACProxyAgentImplOrpheusAgentArch() {
		logger.info("Testing TACProxyAgentImpl(orpheusAgentArch)");
		@SuppressWarnings("unused")
		TACProxyAgentImpl proxyAgent = new TACProxyAgentImpl(orpheusAgentArch);
		proxyAgent = null;
		logger.info("Done");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.TACProxyAgentImpl(String[])'
	 */
	public void testTACProxyAgentImplStringArray() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.trim(String)'
	 */
	public void testTrim() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.init(ArgEnumerator)'
	 */
	public void testInitArgEnumerator() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.transaction(Transaction)'
	 */
	public void testTransactionTransaction() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.quoteUpdated(Quote)'
	 */
	public void testQuoteUpdatedQuote() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.bidUpdated(Bid)'
	 */
	public void testBidUpdatedBid() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.bidRejected(Bid)'
	 */
	public void testBidRejectedBid() {
		logger.info("Test case not implemented.");
	}

	/*
	 * Test method for 'org.soton.orpheus.tac.TACProxyAgentImpl.bidError(Bid, int)'
	 */
	public void testBidErrorBidInt() {
		logger.info("Test case not implemented.");
	}

}
