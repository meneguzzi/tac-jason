package org.soton.orpheus.jason.act;

import jason.architecture.AgArch;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public interface OrpheusAction<Arch extends AgArch> {

	/**
	 * Executes an orpheus action
	 * @param arch The specific agent architecture within which the action is 
	 * 				executed.
	 * @param ts	TODO
	 * @param un 	The action that is to be executed
	 * @param args TODO
	 * @return		Whether or not the action was executed successfully
	 */
	public boolean execute(Arch arch, TransitionSystem ts, Unifier un, Term[] args);
}
