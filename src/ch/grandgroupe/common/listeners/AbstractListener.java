package ch.grandgroupe.common.listeners;

import org.bukkit.event.Listener;

/**
 * Basic class to be extended by any Listener implemented in rules
 */
public abstract class AbstractListener implements Listener {
	private boolean isEnabled = false;

	public AbstractListener(){
		enable();
	}

	public void enable() {
		isEnabled = true;
	}

	public void disable() {
		isEnabled = false;
	}

	protected boolean isDisabled() {return !isEnabled;}
}