package org.soton.orpheus.tac;

public class TACEvent {
	
	protected String event;
	
	public TACEvent(String event) {
		this.event = event;
	}

	/**
	 * @return Returns the event.
	 */
	public String getEvent() {
		return event;
	}
	
	@Override
	public String toString() {
		return getEvent();
	}
	
}
