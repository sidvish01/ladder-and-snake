/* Name - Siddhartha Singh
 * ID - 40138762
 * COMP-249
 * Assignment 1
 * Part I
 * Due Date - February 9th, 2021
 */

import java.io.*;

/**  class LadderAndSnake contains the methods that run core engine of the game 
 * @author siddharthasingh
 *
 *
 */
public class LadderAndSnake 
{
	/**stores the board layout in a 2D array*/
	private int[][] board = new int[10][10];
	/**array stores position of snake tails at cell address snake head*/
	private int[] snake = new int[100]; 
	/**array stores ladder end position at cell address ladder start*/
	private int[] ladder = new int[100];
	/**array stores position of players on the board*/
	private int[] position; 
	/**array stores order of play*/
	private int[] order; 

/**default constructor to initialize class variables with default values*/
public LadderAndSnake()
{
	board = new int[10][10];
	snake = new int[100];
	ladder = new int[100];
	position = new int[1];
	order = new int[1];
}

/**parameterized constructor that takes number of player as a parameter
 * initializes board pattern in board array
 * snake positions in snake array
 * position size corresponding to no of players
 * order size corresponding to no of players
 */
public LadderAndSnake(int no)
{
	int cnt=1;
	for (int i=0; i<10; i++)
	{
		for (int j=0; j<10; j++)
		{
			if (i%2!=0)
			{
				board[i][9-j] = cnt;
			}
			else
			{
				board[i][j] = cnt;
			}
			cnt++;
		}
		
	}
	snake[97] = 4;
	snake[96] = 76;
	snake[94] = 24;
	snake[92] = 68;
	snake[78] = 19;
	snake[63] = 60;
	snake[47] = 30;
	snake[15] = 6;
	ladder[0] = 38;
	ladder[3] = 14;
	ladder[8] = 31;
	ladder[27] = 84;
	ladder[20] = 42;
	ladder[35] = 44;
	ladder[50] = 67;
	ladder[70] = 91;
	ladder[79] = 100;
	position = new int[no];
	for(int i=0; i<no; i++)
	{
		position[i] = 0;
	}
	order = new int[no];
}

/**method to print the board grid with snake, ladder and player positions
 * cnt, pos method variables used for print operation
 * @param no - Number of players playing the game
 */
public void print(int no)
{
	
	for(int i=9; i>=0; i--)
	{
		int cnt = 0;
		int pos = 100;
		System.out.print("*---*----*----*----*----*----*----*----*----*----*");
		System.out.println();
		System.out.print("|");
		for(int j=0; j<10; j++)
		{
			for (int k=0; k<no; k++)
			{
				if (position[k]==board[i][j])
				{
					cnt++;
					pos = k;
				}
			}
			
			if(ladder[board[i][j]-1]!=0)
			{
				System.out.print(" L | ");
			}
			else if(snake[board[i][j]-1]!=0)
			{
				System.out.print(" S | ");
			}
			else if(i==9 && j==0 && cnt==0)
			{				
				System.out.print(board[i][j]+"| ");				
			}
			else if(i==0 && cnt==0)
			{
				if (board[i][j]!=10)
					System.out.print(" ");
				System.out.print(board[i][j]+" | ");
			}
			else if(cnt!=0)
			{
				System.out.print("P#"+(pos+1)+"| ");
				cnt = 0;
			}
			else
			{
				System.out.print(board[i][j]+" | ");
			}
		}
		System.out.println();
		
	}
System.out.println("*---*----*----*----*----*----*----*----*----*----*");
}

/**method returns a random integer between 1 and 6 inclusive
 * 
 * @return - integer between 1 and 6
 */
public int flipDice()
{
	return (int)((Math.random()*6)+1);
}


/**method returns integer array with order in which players will roll dependent on descending values on first die roll
 * integer array a, b, c and d used in tie-breaking operations 
 * integer array player_order stores the final order and is returned
 * @param no - Number of players playing the game
 * @return decided player order
 */
private int[] orDer(int no) 
{
	/*order() should roll die for no of people 
	  check_tie will check whether there is a tie 
	  if a tie found re-roll for those 2 players
	  if tie between more than two players re-roll everyone
	 */
	/** integer array to store the roll values*/
	int a[] = new int[no];
	/**stores no of tie at index 0 and players tied at index 1 & 2*/
	int b[] = new int[3]; 
	/**stores value of new rolls when tie between two players*/
	int c[] = new int[2]; 
	/**integer array stores the decided player order*/
	int player_order[] = new int[no]; 
	
	/**loop to initialize array from 1 to no of players*/
	for (int i=0; i<no; i++)
	{
		player_order[i] = i+1;
	}
	
	b[0] = 10;
	int temp = 0;
	/**store which two players have a tie which is passed as argument to roll function*/
	int d[] = new int[2]; 
	
	/**loop to re-roll every time there is more than one tie, will always be executed at least once*/
	while(b[0]>1)
	{
		a = roLl(player_order);
		b = checkTie(a);
		
		/**loop to process exactly one tie*/
		while(b[0]==1)
		{
			d[0] = b[1]+1;
			d[1] = b[2]+1;
			System.out.println("There is a tie between player "+(b[1]+1)+" and player "+(b[2]+1)+". Re-rolling for them.");
			c = roLl(d);
			a[b[1]] = c[0];
			a[b[2]] = c[1];
			b = checkTie(a);
		}
		if (b[0]>1)
		{
			System.out.println("Re-rolling for everyone as there is a tie between 3 or more players.");
		}
	}
	
	
	/**loop to sort player_order according to the values in roll array*/
	for (int i=0; i<no; i++)
	{
		for (int j=0; j<no-i-1; j++)
		{
			if (a[j]<a[j+1])
			{
				temp = a[j];
				a[j] = a[j+1];
				a[j+1] = temp;
				
				temp = player_order[j];
				player_order[j] = player_order[j+1];
				player_order[j+1] = temp;
			}
		}
	}
	
	System.out.println("The final player order has been decided.");
	for (int i=1; i<=no; i++)
	{
		System.out.println("Player "+player_order[i-1]+" will go at number "+(i)+".");
	}
	
	
	return player_order;
	
}


/** method to returns an array with roll values corresponding to the length of parameter array
 * method variabel roll_values- integer array to store the roll values 
 * @param no - array of size equal to the no of rolls required
 * @return roll values for no of player in parameter
 */
public int[] roLl(int no[])
{
	int[] roll_values = new int[no.length];
	/**loop to flip die corresponding to no of players*/
	for (int i=0; i<no.length; i++)
	{
		roll_values[i] = flipDice();
	}
	for (int i=0; i<no.length; i++)
	{
		System.out.println("Player "+(no[i])+" rolled: "+roll_values[i]);
	}
	return roll_values;
}

/** method checks for tie and returns an array 
 * method variable tie_data- integer array with no of tie at 0 index and tied players at following indexes
 * @param array with rolled values for all player
 * @return tie data containing no of ties and players tied
 */
public int[] checkTie(int[] roll_values)
{
	int tie_data[] = new int[]{0,0,0};
	/**loop to check for tie*/
	for (int i=0; i<(roll_values.length-1); i++)
	{
		for(int j=i+1; j<roll_values.length; j++)
		{
			if (roll_values[i]==roll_values[j])
			{
				/* tie_data[0] counts no of ties, tie_data[1,2] store postion of ties*/
				
				tie_data[0]++;
				tie_data[1] = i;
				tie_data[2] = j;
			}
		}
	}
	return tie_data;
}

/**method that contains core engine of the gameplay
 * method variables- integer accumulators to store player numbers
 * integer dp to prompt for board display
 * @throws input output exceptions during player interupt
 */
public void play()throws IOException
{	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int acc1 = 0;
	int acc2 = 0;
	order = orDer(order.length);
	String dp;
	
	System.out.println("Starting for all players as position ZERO. Let's Go!!");
	
	first:
	for (int un=0; un<1;)
	{
		for (int i=0; i<order.length; i++)
		{
			acc1 = order[i]-1;
			acc2 = flipDice();
			System.out.print("Player "+(acc1+1)+" got "+acc2);
			
			
			position[acc1] = position[acc1] + acc2;
			
			if (position[acc1]>100)
			{
				System.out.println(" and went past 100, moving back to "+(100-(position[acc1]-100)));
				position[acc1] = 100-(position[acc1]-100);
			}
			
			else if (snake[position[acc1]-1]!=0)
			{
				System.out.print(" and got bit by snake at "+position[acc1]+", moving down to "+ladder[position[acc1]-1]);
				position[acc1] = snake[position[acc1]-1];
			}
			
			else if (ladder[position[acc1]-1]!=0)
			{
				System.out.print(" and caught a ladder at "+position[acc1]+", moving up to "+ladder[position[acc1]-1]);
				position[acc1] = ladder[position[acc1]-1];
			}
			
			else
			{
				System.out.print(" and moved to "+position[acc1]);
			}
			System.out.println();
			
			if (position[acc1]==100)
			{
				System.out.println("Player "+ order[i]+ " won the game. CONGRATULATIONS!!");
				print(order.length);
				System.out.println("This is the end state of Board Grid");
				System.out.println("!!!!GOODBYE!!!!");
				
				break first;
			}
			
			
		}
		
		
		System.out.println("One round of flips done, would you like to see the board grid? Enter 1 for Yes, else press ENTER to continue:");
		dp = br.readLine();
		if (dp.equals("1"))
		{
			print(order.length);
		}
		System.out.println("Not Over, going for another round of flips.");
		

		
	}	
	
}

}

