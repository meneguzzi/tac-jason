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
import org.soton.orpheus.jason.TermTranslation;

import se.sics.tac.aw.TACAgent;

/**
 * Class implementing the internal action orpheus.getAuctionFor/4. This class takes three
 * input parameters and unifies the result to the fourth parameter.
 * Parameters are as follows:<br/>
 * <ul>
 * 	<li>Category  - The category of the intended auction.</li>
 *  <li>Type 	  - The type of the intended auction.</li>
 *  <li>Day 	  - The day of the intended auction.</li>
 *  <li>Auction	  - The variable to which the return value will unify.</li>
 * </ul>
 * @author frm05r
 *
 */
public class getAuctionFor implements OrpheusAction<OrpheusAgentArch> {

	/* (non-Javadoc)
	 * @see jason.asSemantics.InternalAction#execute(jason.asSemantics.TransitionSystem, jason.asSemantics.Unifier, jason.asSyntax.Term[])
	 */
	public boolean execute(OrpheusAgentArch agentArch, TransitionSystem ts, Unifier un, Term[] args) {
		ts.getLogger().fine("'getAuctionFor' Invoked");
		//We want to be able to place a bid specifying the auction, the quantity and the price.
		if(args.length < 4) {
			return false;
		}
		ts.getLogger().fine("getAuctionFor arguments ok");
		Term categoryTerm = args[0];
		Term typeTerm	  = args[1];
		NumberTerm dayTerm= (NumberTerm) args[2];
		
		int category = TermTranslation.getTermTranslation().getCategory(categoryTerm);
		int type	 = TermTranslation.getTermTranslation().getType(typeTerm);
		int day		 = (int) dayTerm.solve();
		
		int auction = TACAgent.getAuctionFor(category, type, day);
		
		if(auction < 0)
			return false;
		
		NumberTermImpl numberTerm = new NumberTermImpl(auction);
		Term auctionTerm  = args[3];
		
		return un.unifies(auctionTerm, numberTerm);
	}
}
