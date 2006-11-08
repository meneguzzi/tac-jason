/**
 * 
 */
package org.soton.orpheus.jason.act;

import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

import org.soton.orpheus.jason.OrpheusAgentArch;
import org.soton.orpheus.jason.TermTranslation;

import se.sics.tac.aw.TACAgent;

/**
 * Class implementing the action getAuctionCategory/2. This class takes one
 * input parameter and unifies the result to the second parameter.
 * Parameters are as follows:<br/>
 * <ul>
 * 	<li>Auction   - The auction to discover the category for.</li>
 *  <li>Category  - The variable to which the return value will unify.</li>
 * </ul>
 * @author frm05r
 *
 */
public class getAuctionCategory implements OrpheusAction<OrpheusAgentArch> {

	public boolean execute(OrpheusAgentArch arch, TransitionSystem ts, Unifier un, Term args[]) {
		ts.getLogger().fine("'getAuctionCategory' Invoked");
		//We want to be able to place a bid specifying the auction, the quantity and the price.
		if(args.length < 2) {
			return false;
		}
		ts.getLogger().fine("getAuctionCategory arguments ok");
		NumberTerm auctionTerm = (NumberTerm) args[0];
		
		int auction  = (int) auctionTerm.solve();
		int category = TACAgent.getAuctionCategory(auction);
		
		if(auction < 0)
			return false;
		
		String cat = TermTranslation.getTermTranslation().getCategory(category);
		
		if(cat == null)
			return false;
		
		StringTermImpl categoryTerm = new StringTermImpl(cat);
		
		return un.unifies(args[1], categoryTerm);
	}

}
