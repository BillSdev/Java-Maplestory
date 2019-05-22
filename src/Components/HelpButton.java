package Components;

import GameState.Mode;
import GameState.ModeChanger;
import Helpers.Content;

public class HelpButton extends Button {

ModeChanger mc;
	
	public HelpButton(ModeChanger mc, Mode actionListener) { 
		this.mc = mc;
		this.actionListener = actionListener;
		imageToDraw = image = Content.helpButton;
		imageOn = Content.helpButtonOn;
		width = 334;
		height = 112;
		x = 500;
		y = 400;
	}
	
	public void action() {
		System.out.println("Not implemented...yet.");
	}
	
	
}
