package org.soton.orpheus.jason.act;

import jason.JasonException;
import jason.architecture.AgArch;
import jason.asSemantics.ActionExec;

public interface OrpheusActionExecutive<Arch extends AgArch> {
	
	public boolean executeAction(Arch agArch, ActionExec actionExec) throws JasonException;
	
	public OrpheusAction<Arch> getAction(String actionName) throws JasonException;
}
