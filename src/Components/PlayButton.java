package Components;

import GameState.Mode;
import GameState.ModeChanger;
import Helpers.Content;

public class PlayButton extends Button {

	ModeChanger mc;
	
	public PlayButton(ModeChanger mc, Mode actionListener) { 
		this.mc = mc;
		this.actionListener = actionListener;
		imageToDraw = image = Content.playButton;
		imageOn = Content.playButtonOn;
		width = 334;
		height = 112;
		x = 500;
		y = 250;
	}
	
	public void action() {
		System.out.println("You started the game!");
		mc.setMode(1);
	}
	
	
}
