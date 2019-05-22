package GameState;

import Audio.AudioPlayer;
import Entity.Cannonball;
import Entity.Enemy;
import Entity.HUD;
import Entity.PlayerBullet;
import Entity.Projectile;
import Entity.Mob.BlueSnail;
import Entity.Mob.GreenSnail;
import Entity.Mob.RedSnail;
import Entity.Player;
import Equipments.Equipment;
import Helpers.Characters;
import Helpers.Content;
import Main.GamePanel;
import BlockMap.Background;
import BlockMap.Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.ArrayList;

public class PlayMode
extends Mode
{

	public static int countRespawn = 0;

	private Map map;
	private Background bg;
	private Player player;
	private HUD hd;
	private AudioPlayer bgMusic;
	private long bgMusicTimer;
	private int bgMusicLength;
	private ArrayList<Enemy> enemies;
	private ArrayList<Projectile> bullets;
	private String commandString;
	private boolean textMode;
	private ArrayList<BufferedImage> charactersDraw;
	
	private int currentLetterX;
	private int currentLetterY;

	public PlayMode(ModeChanger mc)
	{
		this.mc = mc;
		init();
	}

	public void init()
	{  

		map = new Map(90, 60);
		map.loadBlocks("/Blocksets/IceBlockSet.gif");
		map.loadMap("/Maps/icemap1.txt");
		map.setPosition(0.0, 0.0);
		map.setTween(0.015);

		bg = new Background(0.1D);
		bg.setImage(Content.gamebg);
		bgMusicLength = 137000000;

		player = new Player(map);
		player.setPosition(200.0, 200.0);
		bullets = new ArrayList<>();
		populateMobs();

		hd = new HUD(player);

		bgMusic = new AudioPlayer("/BackgroundMusic/Henesys.mp3");
		bgMusic.play();
		bgMusicTimer = System.nanoTime();

		Characters.loadCharacters();
		commandString = "";
		textMode = false;
		charactersDraw = new ArrayList<>();

	}

	public void populateMobs()
	{
		enemies = new ArrayList<>();

		Point[] point = { new Point(1120, 300), new Point(1000, 300), new Point(900, 400), new Point(1400, 400), new Point(1100, 200) };
		BlueSnail bs = new BlueSnail(map, player, bullets);
		bs.setPosition(point[0].x, point[0].y);
		enemies.add(bs);
		GreenSnail gs = new GreenSnail(map, player);
		gs.setPosition(point[1].x, point[1].y);
		enemies.add(gs);

		RedSnail rs = new RedSnail(map, player, bullets);
		rs.setPosition(point[2].x, point[2].y);
		enemies.add(rs);
		bs = new BlueSnail(map, player, bullets);
		bs.setPosition(point[3].x, point[3].y);
		enemies.add(bs);

		gs = new GreenSnail(map, player);
		gs.setPosition(point[4].x, point[4].y);
		enemies.add(gs);
	}

	public void update()
	{
		player.update();
		//map.setPosition(GamePanel.WIDTH/2 - player.getx(), GamePanel.HEIGHT/2 - player.gety());
		map.setPosition(player.getx() - GamePanel.WIDTH/2, player.gety() - GamePanel.HEIGHT/2);
		bg.setPosition(map.getx(), map.gety());
		hd.update();

		for (int i = 0 ; i < bullets.size() ; i++) {
			Projectile p = bullets.get(i);
			p.update();
			p.checkHit(enemies, player);
			if(p.isDestroyed() == true) { bullets.remove(i); }
		}

		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy e = (Enemy)enemies.get(i);
			e.update();
			if (e.isDead())
			{
				enemies.remove(i);
				i--;
				player.addExp(e.getExp());
			}
		}
		player.checkAttack(enemies);



		if (enemies.size() == 0) {
			populateMobs();
			countRespawn++;
		}
		if ((System.nanoTime() - bgMusicTimer) / 1000L >= bgMusicLength - 100000)
		{
			bgMusic.play();bgMusicTimer = System.nanoTime();
		}
	}

	public void draw(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		bg.draw(g);
		map.draw(g);
		player.draw(g);
		hd.draw(g);
		for (int i = 0; i < enemies.size(); i++) {
			((Enemy)enemies.get(i)).draw(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		drawCharacters(g);
	}

	public void drawCharacters(Graphics2D g) {
		currentLetterX = 6;
		currentLetterY = 715;
		for(int i = 0 ; i < charactersDraw.size() ; i++) {
			g.drawImage(charactersDraw.get(i), currentLetterX, currentLetterY, null);
			currentLetterX += charactersDraw.get(i).getWidth();
		}
	}

	public void keyPressed(int k)
	{
		if(textMode == false)
			normalModeKeys(k);
		else
			textModeKeys(k);
	}

	public void keyReleased(int k)
	{
		if (k == 39) {
			player.setRight(false);
		}
		if (k == 37) {
			player.setLeft(false);
		}
		if (k == 87) {
			player.setJumping(false);
		}
		if (k == 38) {
			player.setUp(false);
		}
		if (k == 40) {
			player.setDown(false);
		}
	}

	public void mousePressed(MouseEvent e)
	{
		if ((e.getX() > 200) && (e.getX() < 500)) {
			//System.out.println("Pressed");
		}
	}

	public void mouseReleased(MouseEvent e) {}


	public void normalModeKeys(int k) {
		if(k == 89) {
			textMode = true;
			charactersDraw.add(Content.startType);
		}
		if(k == 70) {
			System.out.println("cannooonnn");
			bullets.add(new Cannonball(map, player.getx(), player.gety()+10, 40, 40, 10, 0, player.getCannonVector(), player.getCannonAngle(), player.getCannonDamage(), player.getCannonGravity(), player.isFacingRight()));
		}
		if (k == 86) {
			if(player.getMana() >= 1){
				bullets.add(new PlayerBullet(map, player.getx(), player.gety()+10, 20, 8, 10, 0, player.getLevel()*(player.getLevel()/4) + 2, player.isFacingRight()));
				player.setMana((int)player.getMana() - 1);}
		}
		if (k == 74) {
			player.setHealth(10);
		}
		if (k == 39) {
			player.setRight(true);
		}
		if (k == 37) {
			System.out.println("Pressing left.");
			player.setLeft(true);
		}
		if (k == 87) {
			player.setJumping(true);
		}
		if (k == 38) {
			player.setUp(true);
		}
		if (k == 40) {
			player.setDown(true);
		}
		if (k == 88) {
			player.setMeleeAttacking();
		}
		if (k == 79)
		{
			if (player.getCurrentTop().getEquipID() == 1) {
				player.setCurrentTop(Helpers.Content.equipArray[0]);
			} else {
				player.setCurrentTop(Helpers.Content.equipArray[1]);
			}
			player.setCurrentAction(10);
		}
		if (k == 80)
		{
			if (player.getCurrentFace() == 1) {
				player.setCurrentFace(0);
			} else {
				player.setCurrentFace(player.getCurrentFace() + 1);
			}
			player.setCurrentAction(10);
		}
		if (k == 76)
		{
			if (player.getCurrentBottom().getEquipID() == 101) {
				player.setCurrentBottom(Helpers.Content.equipArray[0]);
			} else {
				player.setCurrentBottom(Helpers.Content.equipArray[2]);
			}
			player.setCurrentAction(10);
		}
		if (k == 75)
		{
			if (player.getCurrentBody() == 0) {
				player.setCurrentBody(0);
			} else {
				player.setCurrentBody(player.getCurrentBody() + 1);
			}
			player.setCurrentAction(10);
		}
		if (!player.getMakingFace()) {
			if (k == 112)
			{
				player.setCurrentSmile(1);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
			else if (k == 113)
			{
				player.setCurrentSmile(2);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
			else if (k == 114)
			{
				player.setCurrentSmile(3);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
			else if (k == 115)
			{
				player.setCurrentSmile(4);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
			else if (k == 116)
			{
				player.setCurrentSmile(5);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
			else if (k == 117)
			{
				player.setCurrentSmile(6);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
			else if (k == 118)
			{
				player.setCurrentSmile(7);player.setMakingFace(true);player.setCurrentFaceTime(System.nanoTime());
			}
		}	
	}

	public void textModeKeys(int k) {

		switch(k) {
		case 65 : if(currentLetterX - 6 <= 418){ commandString += "a";
		charactersDraw.add(Characters.alphabet[1]); }
		break;
		case 66 : if(currentLetterX - 6 <= 418){ commandString += "b";
		charactersDraw.add(Characters.alphabet[2]); }
		break;
		case 67 : if(currentLetterX - 6 <= 418){ commandString += "c";
		charactersDraw.add(Characters.alphabet[3]); }
		break;
		case 68 : if(currentLetterX - 6 <= 418){ commandString += "d";
		charactersDraw.add(Characters.alphabet[4]); }
		break;
		case 69 : if(currentLetterX - 6 <= 418){ commandString += "e";
		charactersDraw.add(Characters.alphabet[5]); }
		break;
		case 70 : if(currentLetterX - 6 <= 418){ commandString += "f";
		charactersDraw.add(Characters.alphabet[6]); }
		break;
		case 71 : if(currentLetterX - 6 <= 418){ commandString += "g";
		charactersDraw.add(Characters.alphabet[7]); }
		break;
		case 72 : if(currentLetterX - 6 <= 418){ commandString += "h";
		charactersDraw.add(Characters.alphabet[8]); }
		break;
		case 73 : if(currentLetterX - 6 <= 418){ commandString += "i";
		charactersDraw.add(Characters.alphabet[9]); }
		break;
		case 74 : if(currentLetterX - 6 <= 418){ commandString += "j";
		charactersDraw.add(Characters.alphabet[10]); }
		break;
		case 75 : if(currentLetterX - 6 <= 418){ commandString += "k";
		charactersDraw.add(Characters.alphabet[11]); }
		break;
		case 76 : if(currentLetterX - 6 <= 418){ commandString += "l";
		charactersDraw.add(Characters.alphabet[12]); }
		break;
		case 77 : if(currentLetterX - 6 <= 418){ commandString += "m";
		charactersDraw.add(Characters.alphabet[13]); }
		break;
		case 78 : if(currentLetterX - 6 <= 418){ commandString += "n";
		charactersDraw.add(Characters.alphabet[14]); }
		break;
		case 79 : if(currentLetterX - 6 <= 418){ commandString += "o";
		charactersDraw.add(Characters.alphabet[15]); }
		break;
		case 80 : if(currentLetterX - 6 <= 418){ commandString += "p";
		charactersDraw.add(Characters.alphabet[16]); }
		break;
		case 81 : if(currentLetterX - 6 <= 418){ commandString += "q";
		charactersDraw.add(Characters.alphabet[17]); }
		break;
		case 82 : if(currentLetterX - 6 <= 418){ commandString += "r";
		charactersDraw.add(Characters.alphabet[18]); }
		break;
		case 83 : if(currentLetterX - 6 <= 418){ commandString += "s";
		charactersDraw.add(Characters.alphabet[19]); }
		break;
		case 84 : if(currentLetterX - 6 <= 418){ commandString += "t";
		charactersDraw.add(Characters.alphabet[20]); }
		break;
		case 85 : if(currentLetterX - 6 <= 418){ commandString += "u";
		charactersDraw.add(Characters.alphabet[21]); }
		break;
		case 86 : if(currentLetterX - 6 <= 418){ commandString += "v";
		charactersDraw.add(Characters.alphabet[22]); }
		break;
		case 87 : if(currentLetterX - 6 <= 418){ commandString += "w";
		charactersDraw.add(Characters.alphabet[23]); }
		break;
		case 88 : if(currentLetterX - 6 <= 418){ commandString += "x";
		charactersDraw.add(Characters.alphabet[24]); }
		break;
		case 89 : if(currentLetterX - 6 <= 418){ commandString += "y";
		charactersDraw.add(Characters.alphabet[25]); }
		break;
		case 90 : if(currentLetterX - 6 <= 418){ commandString += "z";
		charactersDraw.add(Characters.alphabet[26]); }
		break;
		case 32 : if(currentLetterX - 6 <= 418){ commandString += " ";
		charactersDraw.add(Characters.alphabet[0]); }
		break;
		case 49 : commandString += "1";
		charactersDraw.add(Characters.numbers[1]);
		break;
		case 50 : commandString += "2";
		charactersDraw.add(Characters.numbers[2]);
		break;
		case 51 : commandString += "3";
		charactersDraw.add(Characters.numbers[3]);
		break;
		case 52 : commandString += "4";
		charactersDraw.add(Characters.numbers[4]);
		break;
		case 53 : commandString += "5";
		charactersDraw.add(Characters.numbers[5]);
		break;
		case 54 : commandString += "6";
		charactersDraw.add(Characters.numbers[6]);
		break;
		case 55 : commandString += "7";
		charactersDraw.add(Characters.numbers[7]);
		break;
		case 56 : commandString += "8";
		charactersDraw.add(Characters.numbers[8]);
		break;
		case 57 : commandString += "9";
		charactersDraw.add(Characters.numbers[9]);
		break;
		case 48 : commandString += "0";
		charactersDraw.add(Characters.numbers[0]);
		break;
		case 46 : commandString += ".";
		break;
		case 45 : commandString += "-";
		break;
		case 8 : if(charactersDraw.size() > 1) {
			commandString = commandString.substring(0 , commandString.length() - 1);
			charactersDraw.remove(charactersDraw.size()-1); }
		break;
		case 27 : textMode = false;
		System.out.println(commandString);
		Characters.checkString(commandString, player);
		commandString = "";
		charactersDraw.clear();
		break;
		}

	}
}