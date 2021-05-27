package ch.thechi2000asbyx.common.listeners;

import org.bukkit.event.Listener;

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