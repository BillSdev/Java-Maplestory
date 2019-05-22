package Helpers;

import java.awt.image.BufferedImage;

import Entity.Player;

public class Characters {

	public static BufferedImage[] alphabet = new BufferedImage[27];
	public static BufferedImage[] numbers = new BufferedImage[10];
	
	public static void loadCharacters() {
		//Alphabet	
		    alphabet[0] = Content.alphabet.getSubimage(0, 0, 7, 12);
			alphabet[1] = Content.alphabet.getSubimage(7, 0, 7, 12);
			alphabet[2] = Content.alphabet.getSubimage(14, 0, 6, 12);
			alphabet[3] = Content.alphabet.getSubimage(20, 0, 7, 12);
			alphabet[4] = Content.alphabet.getSubimage(27, 0, 7, 12);
			alphabet[5] = Content.alphabet.getSubimage(34, 0, 7, 12);
			alphabet[6] = Content.alphabet.getSubimage(41, 0, 5, 12);
			alphabet[7] = Content.alphabet.getSubimage(46, 0, 7, 12);
			alphabet[8] = Content.alphabet.getSubimage(53, 0, 7, 12);
			alphabet[9] = Content.alphabet.getSubimage(60, 0, 3, 12);
			alphabet[10] = Content.alphabet.getSubimage(63, 0, 3, 12);
			alphabet[11] = Content.alphabet.getSubimage(66, 0, 7, 12);
			alphabet[12] = Content.alphabet.getSubimage(73, 0, 3, 12);
			alphabet[13] = Content.alphabet.getSubimage(76, 0, 11, 12);
			alphabet[14] = Content.alphabet.getSubimage(87, 0, 7, 12);
			alphabet[15] = Content.alphabet.getSubimage(94, 0, 7, 12);
			alphabet[16] = Content.alphabet.getSubimage(101, 0, 7, 12);
			alphabet[17] = Content.alphabet.getSubimage(108, 0, 7, 12);
			alphabet[18] = Content.alphabet.getSubimage(115, 0, 5, 12);
			alphabet[19] = Content.alphabet.getSubimage(120, 0, 7, 12);
			alphabet[20] = Content.alphabet.getSubimage(127, 0, 4, 12);
			alphabet[21] = Content.alphabet.getSubimage(131, 0, 7, 12);
			alphabet[22] = Content.alphabet.getSubimage(138, 0, 5, 12);
			alphabet[23] = Content.alphabet.getSubimage(143, 0, 11, 12);
			alphabet[24] = Content.alphabet.getSubimage(154, 0, 6, 12);
			alphabet[25] = Content.alphabet.getSubimage(160, 0, 6, 12);
			alphabet[26] = Content.alphabet.getSubimage(166, 0, 6, 12);
			
		//Numbers
			for(int i = 0 ; i < numbers.length ; i++) {
				numbers[i] = Content.numbers.getSubimage(i*6, 0, 6, 12);
			}
			
	}
	
	public static void checkString(String string, Player player) {
		if(string.contains("cannonangle ")) {
			System.out.println("number 1 success");
			for(int i = 12 ; i < string.length(); i++) {
				if(Character.isLetter(string.charAt(i)) || string.charAt(i) == ' ' || string.charAt(i) == '.')
					break;
				if(i == string.length()-1) player.setCannonAngle(-Integer.parseInt(string.substring(12, string.length())));
				System.out.println(player.getCannonAngle());
			}
		}
	
		if(string.contains("cannonvector ")) {
			System.out.println("number 2 success");
			for(int i = 13 ; i < string.length(); i++) {
				if(Character.isLetter(string.charAt(i)) || string.charAt(i) == ' ' || string.charAt(i) == '.')
					break;
				if(i == string.length()-1) player.setCannonVector(Integer.parseInt(string.substring(13, string.length())));
				System.out.println(player.getCannonVector());
			}
		}
		
		if(string.contains("cannongravity ")) {
			System.out.println("number 3 success");
			for(int i = 14 ; i < string.length(); i++) {
				if(Character.isLetter(string.charAt(i)) || string.charAt(i) == ' ')
					break;
				System.out.println(i);
				if(i == string.length()-1){ player.setCannonGravity(Double.parseDouble(string.substring(14, string.length())));
				System.out.println(player.getCannonGravity());}
			}
		}
		
	}
	
}
