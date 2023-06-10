import java.io.*;
import java.util.*;
public class wordle {
	static String word;
	static String actualword;
	static int nletter;
	static ArrayList<String> allWords;
	static ArrayList<String> posWords;
	static double freq [];
	static double tScore = 0;
	static int table [] = new int [7];
	static int xx;
	static int yy;
	static int eliminates = 0;
	static int trials;
	public static void main(String[] args) throws Exception{
		Scanner input = new Scanner(System.in);
		System.out.println("Instructions");
		System.out.println("An UPPERCASE letter means that the letter exists in the word, but is not in the right place");
		System.out.println("A LOWERCASE letter means that the letter is in the correct place, but does not guarantee it is the only letter in the word\n");
		commands();

		System.out.println("\nDifferent Game Modes");
		System.out.println("Normal Mode: Have computer randomly generate word");
		System.out.println("VS Mode: Users will manually enter words for others to guess");
		System.out.println("Bot Mode: Run a simulation to test out the Superbot\n");
		String inp2 = "";
		do
		{
			System.out.println("Press enter to play Normal, 'm' to play VS Mode, or 'b' for Bot mode");
			inp2 = input.nextLine();
		}while(!inp2.equals("m") && !inp2.equals("") && !inp2.equals("b"));
		allWords = new ArrayList<String> ();
		posWords = new ArrayList<String>();
		nletter = 0;
		do{
			System.out.println("Do you want to play with 3, 4, 5, 6, 7, or 8 letters?");
			nletter = input.nextInt();
		}while((nletter < 3 || nletter > 8) && nletter != 15);
		if(inp2.equals("b"))
		{
			do{
				System.out.println("How many trials? (Recommended: 100)");
				trials = input.nextInt();
			}while(trials < 1);
		}
		numLet();
		double max = 0;
		String best = "";
		int bcounts = 0;
		while(true)
		{
			setFreq();
			bcounts++;
			int rnd = (int)(Math.random()*yy);
			boolean win = false;
			//String s = allWords.get((int)(Math.random()*xx));
			String s = "";
			if(!inp2.equals("m"))
			{
				s = posWords.get(rnd);
				//s = posWords.get(bcounts);
			}
			else 
			{ 
				String ans3 = "y";
				do{
					System.out.println("Enter a word for the opponent to guess, or 'r' for a random word");
					s = input.next();
					s = s.toLowerCase();
					boolean lower = true;
					for(int k = 0; k < s.length(); k++)
					{
						lower = lower && Character.isLowerCase(s.charAt(k));
					}
					if(!lower)
					{
						System.out.println("The word needs to contain letter only");
						ans3 = "n";
						continue;
					}
					else if(s.equals("r"))
					{
						int rondz = (int)(Math.random()*yy);
						System.out.println("Generating random word: " + posWords.get(rondz));
						ans3 = "n";
						continue;
					}
					if(s.length() > nletter)
					{
						System.out.println("That word is too long");
						ans3 = "n";
						continue;
					}
					else if(s.length() < nletter) 
					{
						System.out.println("That word is too short");
						ans3 = "n";
						continue;
					}
					else if(!allWords.contains(s))
					{
						do
						{
							System.out.println("That word is not a recognized word. Is that okay (y/n)?");
							ans3 = input.next();
							if(ans3.equals("y"))
							{
								allWords.add(s);
								xx++;
							}
						}
						while(!ans3.equals("y") && !ans3.equals("n"));					
					}
					else
					{
						break;
					}

				}while(ans3.equals("n"));
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			}
			actualword = s;
			char arr [][] = new char[6][nletter];
			for(int i = 0; i < 6; i++)
			{
				for(int j = 0; j < nletter; j++)
				{
					arr[i][j] = '-';
				}
			}
			char letters [] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};  
			String used [] = {"none", "", "", "", "", ""};
			for(int i = 0; i < 6; i++)
			{

				display(arr,letters, used, i);
				String inp = "";
				System.out.println();
				do{
					System.out.println("Enter a " + nletter + "-letter word (or command): ");
					if(!inp2.equals("b"))
					{
						inp = input.next();
						inp = inp.toLowerCase();
					}
					else
					{
						if(i > 0)
							inp = superBot(arr, letters, used, i+1);
						else
							inp = superBot(arr, letters, used, 1);
						System.out.println(inp);
					}
					if(inp.equals("s"))
					{
						if(i > 0)
							System.out.println(superBot(arr, letters, used, i+1));
						else
							System.out.println(superBot(arr, letters, used, 1));
					}
					else if(inp.equals("b"))
					{
						if(i > 0)
							System.out.println(bot(arr, letters, used, i+1));
						else
							System.out.println(bot(arr, letters, used, 1));
					}else if(inp.equals("amogus") && nletter == 6)
					{
						System.out.println("sus");
					}else if(inp.equals("c"))
					{
						commands();
					}else if(inp.equals("f"))
					{
						break;
					}else if(inp.equals("d"))
					{
						display(arr,letters, used, i);
					}else if(inp.equals("wordle") && nletter == 6)
					{
						System.out.println("How original.");
					}else if(inp.equals("r"))
					{
						int ranz = (int)(Math.random()*xx);
						System.out.println("Generating random word: " + allWords.get(ranz));
					}else if(inp.length() < nletter)
					{
						System.out.println("That word is too short");
					}else if(inp.length() > nletter)
					{
						System.out.println("That word is too long");
					} else if(!allWords.contains(inp)) 
					{
						System.out.println("That word is not on the word list");
					}
				}while(!allWords.contains(inp) && !inp.equals("f"));
				if(inp.equals("f"))break;
				used[i] = inp;
				if(inp.equals(s))
				{
					System.out.println("YOU WIN! You guessed it in " + (i+1) + " tries");
					tScore += (double)(i+1);
					table[i]++;
					win = true;
					break;
				}
				boolean contained = false;
				for(int j = 0; j < nletter; j++)
				{
					char a = inp.charAt(j);
					if(containsL(s, a) == true)
					{
						contained = true;
						if(s.charAt(j) == a)
						{
							arr[i][j] = a;
						}
						else
						{
							arr[i][j] = Character.toUpperCase(a);
						}
					}
					else
					{
						letters[a - 'a'] = '*';
					}
				}
				if(!contained)
				{
					for(int k = 0; k < nletter; k++)
					{
						arr[i][k] = '*';
					}
				}
			}
			if(win == false)
			{
				tScore += 7;
				table[6]++;
				System.out.println("You lost...");
				System.out.println("The word was " + s);
			}
			String ask = "";
			if(!inp2.equals("b"))
			{
				do{
					System.out.println("Do you want to play again? (y/n)");
					ask = input.next();
				}while(!ask.equals("y") && !ask.equals("n"));
				if(ask.equals("n")) break;
				do{
					System.out.println("Do you want to play with 3, 4, 5, 6, 7, or 8 letters?");
					nletter = input.nextInt();
					numLet();
				}while((nletter < 3 || nletter > 8) && nletter != 15);
			}
			if(bcounts >= trials && inp2.equals("b"))
			{
				System.out.println("The superbot averages " + tScore/(double)bcounts + " guesses for the common " + nletter + "-letter words.");
				showTable();
				break;
			}
		}	
	}
	public static String bot (char arr[][], char letters[], String used[], int t)
	{
		String s = "";
		boolean out = false;
		double d = 0;
		while(out == false && d < 999999)
		{
			d++;
			out = true;
			int rand = (int)(Math.random()*xx);	
			s = allWords.get(rand);
			char temp [] = s.toCharArray();
			if(containsS(used, s)) out = false;
			for(int j = 0; j < t; j++)
			{
				for(int i = 0; i < arr[0].length; i++)
				{
					if(containsL(letters, temp[i]) == false)
					{
						out = false;
						//break;
					}
					if(arr[j][i] == '-' || arr[j][i] == '*') 
					{
						continue;
					}
					char low = Character.toLowerCase(arr[j][i]);

					if(arr[j][i] >= 97) //lowercase letter
					{
						if(temp[i] !=  arr[j][i]) 
						{
							out = false;
						}
					}
					else
					{	
						if(!(containsL(s,low) && temp[i] != low)) 
						{
							out = false;
						}
					}
				}
			}
		}
		if(d > 999998) return "bot_failed";	
		return s;
	}
	public static String superBot(char arr[][], char letters[], String used[], int t)
	{
		String s = "";
		String great = "bot_failed";
		double max = 0;
		boolean out = false;
		double d = 0;
		boolean init = posWords.contains(actualword);
		ArrayList<String> works = new ArrayList<>();
		for(int z = 0; z < allWords.size(); z++)
		{
			out = true;
			s = allWords.get(z);
			char temp [] = s.toCharArray();
			if(containsS(used, s)) out = false;
			for(int j = 0; j < t; j++)
			{
				for(int i = 0; i < arr[0].length; i++)
				{
					if(containsL(letters, temp[i]) == false)
					{
						out = false;
						//break;
					}
					if(arr[j][i] == '-' || arr[j][i] == '*') 
					{
						continue;
					}
					char low = Character.toLowerCase(arr[j][i]);

					if(arr[j][i] >= 97) //lowercase letter
					{
						if(temp[i] !=  arr[j][i]) 
						{
							out = false;
						}
					}
					else
					{	
						if(!(containsL(s,low) && temp[i] != low)) 
						{
							out = false;
						}
					}
				}
			}
			if(out == true)
			{
				works.add(s);
				if(Score(s) > max)
				{
					max = Score(s);
					great = s;
				}
			}
		}
		int cnts = 0;
		String greater = "";
		for(int i = 0; i < works.size(); i++)
		{
			String ss = works.get(i);
			if(posWords.contains(ss)) 
			{
				cnts++;
				if(greater.equals("") || 
						Score(ss) > Score(greater))
					greater = ss;
			}
		}
		if(t > 2 && init) return greater;
		if(cnts <= 2 && init) return greater;
		return great;
	}

	public static void toWord(char arr[])
	{
		word = "";
		for(int i = 0; i < arr.length; i++)
		{
			word += ("" + arr[i]);
		}
	}
	public static boolean containsS(String arr [], String s)
	{
		for(int i = 0; i < arr.length; i++) 
		{
			if(arr[i].equals(s)) return true;
		}
		return false;
	}
	public static boolean containsL(char arr [], char c)
	{
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i] == c) return true;
		}
		return false;
	}
	public static boolean containsL(String s, char c)
	{
		for(int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i) == c)
			{
				return true;
			}
		}
		return false;
	}
	public static void display(char arr[][], char letters[], String used [], int m)
	{
		int b = 0;
		System.out.println("________ATTEMPT " + (m+1) + "________");
		for(int j = 0; j < 6; j++)
		{
			toWord(arr[j]);
			for(int i = 0; i < word.length(); i++)
			{
			  char temp3 = word.charAt(i);
			  if(temp3 != '-' && temp3 != '*')
			  {
				if(temp3 < 91)
				  System.out.print("\u001b[33m" + word.charAt(i) + "\u001b[0m");
				else
				  System.out.print("\u001b[32m" + word.charAt(i) + "\u001b[0m");
			  }
			  else
				System.out.print(temp3);      
			}
			System.out.print("      ");
			for(int k = 0; k < 10; k++)
			{
				if(b >= 26) break;
				System.out.print(letters[b]);
				b++;

			}
			if(j == 5 && !used[0].equals("none"))
			{
				System.out.print("Submitted: ");
				for(int i = 0; i < used.length; i++) 
				{
					System.out.print(used[i] + " ");
				}
			}
			if(j == 4)
			{
				System.out.print("Enter 'c' to show special commands");
			}
			System.out.println();
		}
	}
	public static void commands()
	{
		System.out.println("Special Commands");
		System.out.println("Enter 'r' to generate a random playable word");
		System.out.println("Enter 'd' to redisplay the attempt screen");
		System.out.println("Enter 'f' to surrender");
		System.out.println("Enter 'b' to have a Bot generate plausible words, taking into account previous submissions");
		System.out.println("Enter 's' to have a SuperBot generate the best possible word");
	}
	public static void numLet()
	{
		allWords.clear();
		posWords.clear();
		try
		{
			Scanner input2;
			Scanner input3;
			if(nletter == 15)
			{
				File myObj = new File("wordle/15word");
				File myObj2 = new File("wordle/15common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 5688;
				yy = 141;
			}
			else if(nletter == 8)
			{
				File myObj = new File("wordle/8word");
				File myObj2 = new File("wordle/8common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 29762;
				yy = 477;
			}
			else if(nletter == 7)
			{
				File myObj = new File("wordle/7word");
				File myObj2 = new File("wordle/7common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 24004;
				yy = 498;
			}
			else if(nletter == 6)
			{
				File myObj = new File("wordle/6word");
				File myObj2 = new File("wordle/6common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 10550;
				yy = 466;
			}
			else if(nletter == 5)
			{
				File myObj = new File ("wordle/5word");
				File myObj2 = new File ("wordle/5common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 12485;
				yy = 420;
			}
			else if(nletter == 4)
			{
				File myObj = new File ("wordle/4word");
				File myObj2 = new File ("wordle/4common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 5456;
				yy = 349;
			}
			else
			{
				File myObj = new File ("wordle/3word");
				File myObj2 = new File ("wordle/3common");
				input2 = new Scanner(myObj);
				input3 = new Scanner(myObj2);
				xx = 1292;
				yy = 280;
			}
			for(int i = 0; i < xx; i++)
			{
				String temp = input2.next();
				allWords.add(temp);
			}
			for(int i = 0; i < yy; i++)
			{
				String temp = input3.next();
				posWords.add(temp);

			}
		}
		catch(Exception e)
		{

		}
	}
	public static double Score(String s)
	{
		double sum = 0;
		Set<Character> set = new HashSet<Character> ();
		for(int i = 0; i < s.length(); i++)
		{
			char temp = s.charAt(i);
			if(!set.contains(temp))
			{
				set.add(temp);
				sum +=freq[temp - 'a'];
			}
		}
		return sum;
	}
	public static void setFreq()
	{
		freq = new double[26];
		int upton = allWords.size();
		boolean lowerg = nletter <= 5;
		if(lowerg) upton = posWords.size();
		for(int i = 0; i < upton; i++)
		{
			String temp;
			if(lowerg) temp = posWords.get(i);
			else temp = allWords.get(i);
			for(int j = 0; j < temp.length(); j++)
			{
				char cc = temp.charAt(j);
				freq[cc - 'a']++;
			}
		}
	}
	public static void showTable()
	{
		System.out.println("Guess Distribution");
		for(int i = 0; i < 7; i++)
		{
			if(i < 6)
				System.out.print((i+1) + ":");
			else
				System.out.print("X:");
			double prop = (double)table[i]/(double)trials*100;
			int roundp = (int)prop;
			for(int j = 0; j < roundp; j++) 
			{
				if(j == 0) System.out.print(" ");
				System.out.print("*");
			}
			System.out.println(" (" + table[i] + ")");
		}
	}
}