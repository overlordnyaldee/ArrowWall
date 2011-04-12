package com.amaranthinecorporation.ArrowWall;

import java.util.TimerTask;
import java.util.ArrayList;
import org.bukkit.entity.Arrow;

public class ArrowCleanupTimer extends TimerTask{
	private ArrayList<Arrow> arrows;
	int thecount = 1;

    public ArrowCleanupTimer(ArrayList<Arrow> arrows) {
        this.arrows = arrows;
    }
    
	public void run() {
		
		// remove all arrows
		for (Arrow a : arrows) {
			a.remove();
		}
		
		// remove timer
		this.cancel();
		
	}
		
}