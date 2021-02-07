/* Name - Siddhartha Singh
 * ID - 40138762
 * COMP-249
 * Assignment 1
 * Part II
 * Due Date - February 9th, 2021
 */
import java.io.*;

/**driver class to prompt user to ask for number of players playing the game
 *  
 * @author siddharthasingh
 *
 */
public class PlayLadderAndSnake
{
	/**main method
	 * object of ladder and snake class to start the game
	 * method variables- String no & integer i  for implementation to obtain a valid prompt 
	 */
	public static void main(String[] args)throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String no = "";
		int i =0;
		System.out.println("Welcome to the game of Snakes and Ladders by - Siddhartha Singh");

		LadderAndSnake g1;
		while(i<100)
		{
			System.out.println("Enter the number of players that would like to play. There can 2-4 players inclusively. Attempt "+(i+1)+" of 4:");
			no = br.readLine();
			
			if(Integer.parseInt(no)>4||Integer.parseInt(no)<2)
			{
				System.out.println("Bad attempt, Invalid number of players.");
				if(i>2)
				{
					System.exit(0);
				}
			}
			else 
			{
				g1 = new LadderAndSnake(Integer.parseInt(no));
				g1.play();
				break;	
			}
			i++;
		}					
	}
}
	
