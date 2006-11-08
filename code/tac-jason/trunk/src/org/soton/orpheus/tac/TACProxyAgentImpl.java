package org.soton.orpheus.tac;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.soton.orpheus.jason.OrpheusAgentArch;

import se.sics.tac.aw.AgentImpl;
import se.sics.tac.aw.Bid;
import se.sics.tac.aw.Quote;
import se.sics.tac.aw.TACAgent;
import se.sics.tac.aw.Transaction;
import se.sics.tac.util.ArgEnumerator;

/**
 * A proxy class used to translate TAC messages into Jason/AgentSpeak(L) events 
 * and updates.
 * @author frm05r
 *
 */
public class TACProxyAgentImpl extends AgentImpl implements TACProxyAgent {
	public static Logger logger = Logger.getLogger(TACProxyAgent.class.getName());
	
	protected OrpheusAgentArch agentArch;
	protected List<TACEvent> events;
	
	/**
	 * Creates a new TAC Proxy Agent for interfacing with Jason attaching 
	 * the specified AgentArchitecture.
	 */
	public TACProxyAgentImpl() {
		this(null);
	}
	
	/**
	 * Creates a new TAC Proxy Agent for interfacing with Jason attaching 
	 * the specified AgentArchitecture.
	 * 
	 * @param agentArch    A reference to an OrpheusAgentArchitecture for 
	 * 					   interfacing with Jason
	 */
	public TACProxyAgentImpl(OrpheusAgentArch agentArch) {
		this.init(agentArch);
	}
	
	protected static String trim(String text) {
		return (text == null || (text = text.trim()).length() == 0) ? null
				: text;
	}
	
	public void init(OrpheusAgentArch agentArch) {
		this.agentArch = agentArch;
		this.events = new Vector<TACEvent>();
		logger.fine("'init("+agentArch+")' invoked");
	}

	@Override
	protected void init(ArgEnumerator arg) {
		
	}
	
	@Override
	public void transaction(Transaction transaction) {
		logger.finer("'transaction("+transaction+")' invoked");
		addEvent("transaction("+transaction.getAuction()+","+
								transaction.getQuantity()+","+
								transaction.getPrice()+")");
	}
	
	@Override
	public void quoteUpdated(int quote) {
		logger.finer("'quoteUpdated("+quote+")' invoked");
		addEvent("quoteUpdated("+quote+")");
	}
	
	@Override
	public void quoteUpdated(Quote quote) {
		logger.finer("'quoteUpdated("+quote+")' invoked");
		//agentArch.addBelief("quoteUpdated("+quote.getAuction()+","+quote.getAskPrice()+")");
		//quote.getBid();
		addEvent("quoteUpdated("+quote.getAuction()+","
								+quote.getAskPrice()+","
								+quote.getBidPrice()+")");
	}

	@Override
	public void bidUpdated(Bid bid) {
		logger.finer("'bidUpdated("+bid+")' invoked");
		addEvent("bidUpdated("+bid.getAuction()+")");
	}

	@Override
	public void bidRejected(Bid bid) {
		logger.finer("'bidRejected("+bid+")' invoked");
		addEvent("bidRejected("+bid.getAuction()+",\""+
				               bid.getRejectReasonAsString()+"\")");
	}

	@Override
	public void bidError(Bid bid, int error) {
		logger.finer("'bidError("+bid+","+error+")' invoked");
		addEvent("bidError("+translateBidString(bid)+","+error+")");
	}
	
	protected String translateBidString(Bid bid) {
		String sBid = bid.getBidString();
		StringBuffer sRes = new StringBuffer();
		sRes.append("[");
		sRes.append(sBid.substring(1,sBid.length()-2));
		sRes.append("]");
		logger.info("Translated into: "+sRes);
		return sRes.toString();
	}

	@Override
	public void gameStarted() {
		logger.finer("Game " + agent.getGameID() + " started!");
		addEvent("gameStarted("+agent.getGameID()+")");
		getCustomerPreferences();
	}

	@Override
	public void gameStopped() {
		logger.finer("'gameStopped()' invoked");
		addEvent("gameStopped("+agent.getGameID()+")");
	}

	@Override
	public void auctionClosed(int auction) {
		logger.finer("'auctionClosed("+auction+")' invoked");
		addEvent("auctionClosed("+auction+")");
	}
	
	protected void getCustomerPreferences() {
		for (int i = 0; i < 8; i++) {
			int inFlight 	= agent.getClientPreference(i, TACAgent.ARRIVAL);
			int outFlight 	= agent.getClientPreference(i, TACAgent.DEPARTURE);
			int hotel 		= agent.getClientPreference(i, TACAgent.HOTEL_VALUE);
			int aw 			= agent.getClientPreference(i, TACAgent.E1);
			int ap			= agent.getClientPreference(i, TACAgent.E2);
			int mu			= agent.getClientPreference(i, TACAgent.E3);
			//We assume preferences represented as:
			//preference(Client, InFlight, OutFlight, 
			//			HotelPremium, AW, AP, MU). 
			//agentArch.addBelief("preference(client"+i+", inFlight, "+inFlight+")");
			//agentArch.addBelief("preference(client"+i+", outFlight, "+outFlight+")");
			//agentArch.addBelief("preference(client"+i+", hotel, "+hotel+")");
			agentArch.addBelief("preference(client"+i+","
											+inFlight+","
											+outFlight+","
											+hotel+","
											+aw+","
											+ap+","
											+mu+")"
					);
			/*int type;

			// Get the flight preferences auction and remember that we are
			// going to buy tickets for these days. (inflight=1, outflight=0)
			int auction = agent.getAuctionFor(TACAgent.CAT_FLIGHT,
					TACAgent.TYPE_INFLIGHT, inFlight);
			agent.setAllocation(auction, agent.getAllocation(auction) + 1);
			auction = agent.getAuctionFor(TACAgent.CAT_FLIGHT,
					TACAgent.TYPE_OUTFLIGHT, outFlight);
			agent.setAllocation(auction, agent.getAllocation(auction) + 1);

			// if the hotel value is greater than 70 we will select the
			// expensive hotel (type = 1)
			if (hotel > 70) {
				type = TACAgent.TYPE_GOOD_HOTEL;
			} else {
				type = TACAgent.TYPE_CHEAP_HOTEL;
			}
			// allocate a hotel night for each day that the agent stays
			for (int d = inFlight; d < outFlight; d++) {
				auction = agent.getAuctionFor(TACAgent.CAT_HOTEL, type, d);
				log.finer("Adding hotel for day: " + d + " on " + auction);
				agent.setAllocation(auction, agent.getAllocation(auction) + 1);
			}

			int eType = -1;
			while ((eType = nextEntType(i, eType)) > 0) {
				auction = bestEntDay(inFlight, outFlight, eType);
				log.finer("Adding entertainment " + eType + " on " + auction);
				agent.setAllocation(auction, agent.getAllocation(auction) + 1);
			}*/
		}
	}

	public List<TACEvent> getEvents() {
		Vector<TACEvent> retEvents = new Vector<TACEvent>(events);
		events.clear();
		return retEvents;
	}

	public void addEvent(TACEvent event) {
		events.add(event);
	}
	
	public void addEvent(String event) {
		this.addEvent(new TACEvent(event));
	}

	public void submitBid(int auction, int allocation, float price) {
		Bid bid = new Bid(auction);
		bid.addBidPoint(allocation, price);
		agent.submitBid(bid);
	}

}
