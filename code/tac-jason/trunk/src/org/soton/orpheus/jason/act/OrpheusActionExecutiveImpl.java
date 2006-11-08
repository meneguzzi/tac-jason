/**
 * 
 */
package org.soton.orpheus.jason.act;

import java.util.HashMap;

import jason.JasonException;
import jason.asSemantics.ActionExec;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

import org.soton.orpheus.jason.OrpheusAgentArch;

/**
 * @author frm05r
 *
 */
public class OrpheusActionExecutiveImpl implements
		OrpheusActionExecutive<OrpheusAgentArch> {
	protected HashMap<String, OrpheusAction<OrpheusAgentArch>> actions;
	
	public OrpheusActionExecutiveImpl() {
		actions = new HashMap<String, OrpheusAction<OrpheusAgentArch>>();
	}
	
	public boolean executeAction(OrpheusAgentArch agArch, ActionExec actionExec) throws JasonException {
		String actionName = actionExec.getActionTerm().getFunctor();
		OrpheusAction<OrpheusAgentArch> action = getAction(actionName);
		
		TransitionSystem ts = agArch.getTS();
		Term args[] = actionExec.getActionTerm().getTermsArray();
		Unifier un = ts.getC().getSelectedIntention().peek().getUnif();
		
		return action.execute(agArch, ts, un, args);
	}

	@SuppressWarnings("unchecked")
	public OrpheusAction<OrpheusAgentArch> getAction(String actionName) throws JasonException {
		OrpheusAction<OrpheusAgentArch> action = actions.get(actionName);
		if(action == null) {
			try {
				Class actionClass = Class.forName("org.soton.orpheus.jason.act."+actionName);
				if(OrpheusAction.class.isAssignableFrom(actionClass)) {
					action = (OrpheusAction<OrpheusAgentArch>) actionClass.newInstance();
					actions.put(actionName, action);
				} else
					throw new JasonException("Incompatible action type for '"+actionName+"'");
			} catch (ClassNotFoundException e) {
				throw new JasonException("Action '"+actionName+"' does not exist");
			} catch (Exception e) {
				throw new JasonException(e.getMessage());
			} 
		}
		
		return action;
	}

}
