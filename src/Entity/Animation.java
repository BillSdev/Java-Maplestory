package Entity;

import java.awt.image.BufferedImage;

public class Animation
{
	
	//simple animation class.  gets an image array and delay and skips to the next frame if delay amount of time has passed.
	
	private BufferedImage[] frames;
	private int currentFrame;
	private int lengthOfFrames;
	private int delay;
	private long startTime;
	private boolean playedOnce;

	public Animation(){ 
		playedOnce = false; 
	}

	public void setDelay(int d){ delay = d; }

	public void setFrames(BufferedImage[] frames, int lengthOfFrames){
		this.lengthOfFrames = lengthOfFrames;
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	public void setFace(BufferedImage[] bi, int i){  
		frames = bi; 
		currentFrame = i; 
	}

	public void setFrame(int i){ currentFrame = i; }

	public void update() {
		if (delay == -1) 
			return;
		long timePassed = (System.nanoTime() - startTime) / 1000000;
		if (timePassed > delay) {
			currentFrame += 1;
			startTime = System.nanoTime();
		}
		//when animation ends change playedOnce to true and the other classes will decided how to act upon  that.
		if (currentFrame == lengthOfFrames) {
			currentFrame = 0;
			playedOnce = true;
		}
	}

	public int getFrame() { return currentFrame; }

	public BufferedImage getImage() { return frames[currentFrame]; }

	public boolean hasPlayedOnce() { 	return playedOnce; }
}
