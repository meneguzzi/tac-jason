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
 * Class implementing the internal action orpheus.getAuctionType/2. This class takes one
 * input parameter and unifies the result to the second parameter.
 * Parameters are as follows:<br/>
 * <ul>
 * 	<li>Auction   - The auction to discover the type for.</li>
 *  <li>Type	  - The variable to which the return value will unify.</li>
 * </ul>
 * @author frm05r
 *
 */
public class getAuctionType implements OrpheusAction<OrpheusAgentArch> {

	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#execute(jason.asSemantics.TransitionSystem, jason.asSemantics.Unifier, jason.asSyntax.Term[])
	 */
	public boolean execute(OrpheusAgentArch agentArch, TransitionSystem ts, Unifier un, Term[] args) {
		ts.getLogger().fine("'getAuctionType' Invoked");
		//We want to be able to place a bid specifying the auction, the quantity and the price.
		if(args.length < 2) {
			return false;
		}
		ts.getLogger().fine("getAuctionType arguments ok");
		NumberTerm auctionTerm = (NumberTerm) args[0];
		
		int auction  = (int) auctionTerm.solve();
		if(auction < 0)
			return false;
		
		int category = TACAgent.getAuctionCategory(auction);
		int type = TACAgent.getAuctionType(auction);
		
		String typeString = null;
		
		switch (category) {
		case TACAgent.CAT_ENTERTAINMENT:
			typeString = TermTranslation.getTermTranslation().getEntertainmentType(type); 
			break;
		case TACAgent.CAT_FLIGHT:
			typeString = TermTranslation.getTermTranslation().getFlightType(type); 
			break;
		case TACAgent.CAT_HOTEL:
			typeString = TermTranslation.getTermTranslation().getHotelType(type); 
			break;
		}
		
		if(typeString == null)
			return false;
		
		StringTermImpl typeTerm = new StringTermImpl(typeString);
		
		return un.unifies(args[1], typeTerm);
	}

}
