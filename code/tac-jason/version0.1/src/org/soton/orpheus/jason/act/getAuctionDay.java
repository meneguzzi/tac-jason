/**
 * 
 */
package org.soton.orpheus.jason.act;

import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

import org.soton.orpheus.jason.OrpheusAgentArch;

import se.sics.tac.aw.TACAgent;

/**
 * Class implementing the internal action orpheus.getAuctionDay/2. This class takes one
 * input parameter and unifies the result to the second parameter.
 * Parameters are as follows:<br/>
 * <ul>
 * 	<li>Auction   - The auction to discover the day for.</li>
 *  <li>Day		  - The variable to which the return value will unify.</li>
 * </ul>
 * @author frm05r
 *
 */
public class getAuctionDay implements OrpheusAction<OrpheusAgentArch> {

	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#execute(jason.asSemantics.TransitionSystem, jason.asSemantics.Unifier, jason.asSyntax.Term[])
	 */
	public boolean execute(OrpheusAgentArch agentArch, TransitionSystem ts, Unifier un, Term[] args) {
		ts.getLogger().fine("'getAuctionDay' Invoked");
		//We want to be able to place a bid specifying the auction, the quantity and the price.
		if(args.length < 2) {
			return false;
		}
		ts.getLogger().fine("getAuctionDay arguments ok");
		NumberTerm auctionTerm = (NumberTerm) args[0];
		
		int auction  = (int) auctionTerm.solve();
		if(auction < 0)
			return false;
		
		int day = TACAgent.getAuctionDay(auction);
		
		if(day < 0)
			return false;
		
		NumberTermImpl dayTerm = new NumberTermImpl(day);
		
		return un.unifies(args[1], dayTerm);
	}

}
