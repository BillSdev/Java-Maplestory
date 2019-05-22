package Components;

import GameState.Mode;
import GameState.ModeChanger;
import Helpers.Content;

public class ExitButton extends Button {

ModeChanger mc;
	
	public ExitButton(ModeChanger mc, Mode actionListener) { 
		this.mc = mc;
		this.actionListener = actionListener;
		imageToDraw = image = Content.exitButton;
		imageOn = Content.exitButtonOn;
		width = 334;
		height = 112;
		x = 500;
		y = 550;
	}
	
	public void action() {
		System.exit(0);
	}
	
	
}
