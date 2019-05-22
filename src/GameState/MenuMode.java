package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import Components.*;
import Helpers.Content;
import Main.GamePanel;
import BlockMap.Background;

public class MenuMode extends Mode {

	private Background bg;
	private Button[] buttons;

	public static final int PLAY = 0;
	public static final int EXIT = 2;
	public static final int HELP = 1;

	public MenuMode(ModeChanger mc)
	{
		this.mc = mc;
		init();

	}

	public void init() {

		bg = new Background(0.1);
		bg.setImage(Content.bg2);  
		bg.setVector(1, 0); 
		bg.setPosition(0, 0); 

		buttons = new Button[3];
		buttons[PLAY] = new PlayButton(mc, this); 
		buttons[HELP] = new HelpButton(mc, this); 
		buttons[EXIT] = new ExitButton(mc, this);

	}

	public void update() {
		bg.update();
	}

	public void draw(Graphics2D g) {
		bg.draw(g);
		g.drawImage(Content.bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		for(int i = 0 ; i < buttons.length ; i++) 
			buttons[i].draw(g);
	}

	public void keyPressed(int k) {}

	public void keyReleased(int k) {}

	public void mousePressed(MouseEvent e) {
		for(int i = 0 ; i < buttons.length ; i++){
			if(e.getX() >= buttons[i].getX() && e.getX() <= buttons[i].getX() + buttons[i].getWidth()
					&& e.getY() >= buttons[i].getY() && e.getY() <= buttons[i].getY() + buttons[i].getHeight()) {  
				buttons[i].setImage(buttons[i].getImageOn());
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		for(int i = 0 ; i < buttons.length ; i++){
			if(e.getX() >= buttons[i].getX() && e.getX() <= buttons[i].getX() + buttons[i].getWidth()
					&& e.getY() >= buttons[i].getY() && e.getY() <= buttons[i].getY() + buttons[i].getHeight()) {
				buttons[i].action();
			}
			buttons[i].setImage(buttons[i].getImage());
		}
	}
}
