/**
 * 
 */
package org.soton.orpheus.jason.act;

import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;

import org.soton.orpheus.jason.OrpheusAgentArch;
import org.soton.orpheus.tac.TACProxyAgent;

/**
 * Class implementing the internal action orpheus.placeBid/3. Parameters are as follows:<br/>
 * <ul>
 * 	<li>Auction  - The auction for which the bid is placed.</li>
 *  <li>Quantity - The intended amount of goods to bid for.</li>
 *  <li>Bid 	 - The size of the bid.</li>
 * </ul>
 * @author frm05r
 *
 */
public class placeBid implements OrpheusAction<OrpheusAgentArch> {

	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#execute(jason.asSemantics.TransitionSystem, jason.asSemantics.Unifier, jason.asSyntax.Term[])
	 */
	public boolean execute(OrpheusAgentArch agentArch, TransitionSystem ts, Unifier un, Term[] args) {
		ts.getLogger().fine("'placeBid' Invoked");
		//We want to be able to place a bid specifying the auction, the quantity and the price.
		if(args.length < 3) {
			return false;
		}
		ts.getLogger().fine("placeBid arguments ok");
		NumberTerm auctionTerm  = (NumberTerm) args[0];
		NumberTerm quantityTerm = (NumberTerm) args[1];
		NumberTerm bidTerm 		= (NumberTerm) args[2];
		
		int auction  = (int) auctionTerm.solve();
		int quantity = (int) quantityTerm.solve();
		float bid    = (float) bidTerm.solve();
		
		TACProxyAgent proxyAgent = agentArch.getProxyAgent();
		proxyAgent.submitBid(auction, quantity, bid);
		
		return true;
	}

}
