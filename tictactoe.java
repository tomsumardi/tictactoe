////////////////////////////////////////////////////////////
//NAME:THOMAS SUMARDI
//IDEA: 4x4 TICTACTOE with 2mode -human and computer
//								2marker- x and o
//								2difficulty settings:easy and hard

//describe the GUI: In this gui, a person can play with another players or play again 
//the computer. there are 2 settings easy and hard mode. a player can also choose
//the x or the o to play the game.
//how TO PLAY: just specify any mode the user wish to play and the mode will be 
//printted on the top . However, there is one default setting if the user didn't 
//have the time to specify everything. 
//the default setting is human(x) vs computer(o), the level is hard.but the user can
//change the game as he/she wishes 
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;


//////////////////////////////CLASS TICTACTOE////////////////////////
//purpose: this class creates the GUI, handles the printstatement and most importantly
//this class handles the cpu movements and the checking for all available move for the
//the cpu.this class implements actionListener for JMenubar on the top and
//the start button on the bottom. The hardest part to implement is the 4x4 algorithm adn
//all the neccessary movement chacking. 
public class tictactoe extends JApplet implements ActionListener
{
//all the variables used,,,,,might need to be reduced,,,
	Container content; 
	JMenuBar jmb;
	JMenu players,markers,difficulty;
	JMenuItem human,cpu,x,o,easy,hard;
	JPanel listPane,listPane2,boardlayout;
	JLabel message,message2;
	String statementtop,statementbottom;
	JButton start;
	JButton[][] board = new JButton[3][3];
	cell cl[]= new cell[16];
	int gamearray[];
	String player,marker;
	String opponent;
   	int currstatus;
    String markertemp;
  	String playertemp;
  	String opponenttemp;
  	String status;
  	String difficultlvl;
  	String difficulttemp;
  	int boardsize = 4;
	int wholesize = 16;
	boolean drawcheck;
    int playerswitch;
   public tictactoe()
	{	
		
	}
//this method is the firstone to be called when the program starts. it also 
//creates all the necessary components for the GUI. it uses 1 container and inside
//the container there are Jmenubar, 2 labels, GridLayout and a button.	
////if the user clicks on the start,2 things might happen.
	//1st- if the user never set the values then it is set to default value
	//2nd- if the user did set the value then the game will be set as the user
	//specified.
	public void init()
	{
		content = getContentPane();
		content.setLayout(new BorderLayout());	
		
		//JMENUBAR
		jmb = new JMenuBar();
		
		players = new JMenu("Player");
		players.add(human = new JMenuItem("Human"));
		players.add(cpu = new JMenuItem("CPU"));
		//item1.addActionListener(this);
		
		markers = new JMenu("Markers");
		markers.add(x = new JMenuItem("X"));
		markers.add(o = new JMenuItem("0"));
		//item2.addActionListener(this);
		
		difficulty = new JMenu("Dificulty");
		difficulty.add(easy = new JMenuItem("Easy"));
		difficulty.add(hard = new JMenuItem("Hard"));
		//item3.addActionListener(this);
		
		jmb.add(players);
		jmb.add(markers);
		jmb.add(difficulty);
		setJMenuBar(jmb);
		
		//Lay out the label and scroll pane from top to bottom.
		listPane = new JPanel();
		listPane.setLayout(new BorderLayout());
		statementtop= "Game is ready to begin";
		message = new JLabel(statementtop, JLabel.CENTER);
		message.setBorder(BorderFactory.createTitledBorder(""));
		message.setForeground(Color.red);
		
		//jmb.add(Box.createRigidArea(new Dimension(20,20)));
		//(y,x,?,?)
		listPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		listPane.add(message,BorderLayout.NORTH);
		listPane.setBackground(Color.green);
		
		//initializing the tictactoe board.....creating all the objects inside
		//the gridlayout...there are 16 objects created that are all distinct..
		//please refer to the cell class for more details
	 	boardlayout = new JPanel();
		boardlayout.setLayout(new GridLayout(4,4));
	 	for(int r = 0; r <16; r++)
	 		{
	 			cl[r]= new cell(r+1, this);//set the square number
	 			cl[r].setSize(50,50);
	 			boardlayout.add(cl[r]);
	 		}
	    listPane.add(boardlayout, BorderLayout.CENTER);
		boardlayout.setBackground(Color.orange);
		
	    //bottom part
	    listPane2 = new JPanel();
		listPane2.setLayout(new BoxLayout(listPane2, BoxLayout.X_AXIS));
		start = new JButton("RE-START");
		listPane2.add(start);
		statementbottom = "Thoms Tictactoe";
		message2 = new JLabel(statementbottom);
		listPane2.add(message2);
		message2.setForeground(Color.blue);
		listPane2.setBackground(Color.green);
		start.setBackground(Color.white);
		
		//adding all the panels into the container
	    content.add(jmb, BorderLayout.NORTH);
	    content.add(listPane, BorderLayout.CENTER);	
	    content.add(listPane2, BorderLayout.SOUTH);	
	  
	    
	    //setting the action listeners for all the GUI components
	    hard.addActionListener(this);
	    easy.addActionListener(this);
	    start.addActionListener(this);
	    cpu.addActionListener(this);
	    human.addActionListener(this);
	    x.addActionListener(this);
	    o.addActionListener(this);
	    
	    //these are just innitializing some of the global variables that
	 	//acts as a counter for the gui.
	    markertemp="";
  		playertemp="";
  		opponenttemp="";
  		difficulttemp="";
  		status= "";
  		gamearray = new int[16];
  		boardsize = 4;
		wholesize = 16;
		drawcheck = false;
		playerswitch = 1;
	}
	
	//method that services the request from the mouse click on the any of the buttons.
	//please read carefully some if statements need to be simplify.
	public void actionPerformed(ActionEvent e)
  {
  	
  	Object src = e.getSource();
	//if the user clicks on the cpu
	if(src == easy)
	{
		difficulttemp = "easy";
	}
	if(src == hard)
	{
		difficulttemp = "hard";
	}
  	if (src == cpu)
	{
		playertemp = "cpu";
		
	} 
	//if the user clicks on the human
	if (src == human)
	{
		playertemp = "human";
	} 
	//if the user clicks on the x
	if (src == x)
	{
		markertemp = "x";
		opponenttemp = "o";
		
	}
	//if the user clicks on the o 
	if (src == o)
	{
		markertemp = "o";
		opponenttemp = "x";
		
	} 
	//if the user clicks on the start,2 things might happen.
	//1st- if the user never set the values then it is set to default value
	//2nd- if the user did set the value then the game will be set as the user
	//specified.
	if (src == start)
	{
		statementtop  = "THOMS TICTACTOE (tm) ;)";
		printbottom(statementtop);
		if ((playertemp.length() == 0) && (markertemp.length() == 0) && (difficulttemp.length() == 0))
		{
			//default set!!
			marker = "x";
			player = "cpu";
			opponent = "o";
			status = "default";
			difficultlvl = "hard";
			statementtop  = "Default settings: player: x, Cpu: o, Lvl: HARD (user need to specify)";
			printtop(statementtop);
			repaint();
			
		}
		//the user did set the values....
		else 
		{
			
			player = playertemp;
			marker = markertemp;
			opponent = opponenttemp;
			status= "varies";
			difficultlvl = difficulttemp;
			printall();
			repaint();
		}
		
	}
	//the user never did set the values and clicking straight on the board.
	else
	{
		player = playertemp;
		marker = markertemp;
		opponent = opponenttemp;
		status= "varies";
		difficultlvl = difficulttemp;
		printall();
		repaint();
	}
	
	
  }
  //method printall ...this method handles all the possible print statement
  //it may need to be simplified
  public void printall()
  {
  		if(player.equals("cpu") && (int)difficultlvl.length() == 0)
		{
			difficultlvl="hard";
			if((int)marker.length() == 0)
			{
				statementtop  = "Default setting: player: o, Cpu: x  Lvl(notset-default): Hard";
			}
			else
			statementtop  = "User setting: player: "+marker+" , Cpu: "+ opponent+" Lvl(notset-default): Hard";
			
			printtop(statementtop);
		}
		else if (player.equals("cpu") && (int)difficultlvl.length() != 0)
		{
			if((int)marker.length() == 0)
			{
				statementtop  = "Default setting: player: o, Cpu: x  "+ " Lvl: "+difficultlvl;
			}
			else
			statementtop  = "User setting: player: "+marker+" , Cpu: "+ opponent+" Lvl: "+difficultlvl;
			printtop(statementtop);
		}
		else if (player.equals("human"))
		{
			if((int)marker.length() == 0)
			{
				statementtop  = "Default setting: player1: o , player2: x";
			}
			else
			statementtop  = "User setting: player1: "+marker+" , player2: "+ opponent;
			printtop(statementtop);
		}
		else if ( marker.equals("x"))
		{
			if((int)difficultlvl.length() !=0 )
			statementtop  ="Default settings: player: x, Cpu: o, Lvl: "+difficultlvl;
			else
			{
				difficultlvl="hard";
				statementtop  ="Default settings: player: x, Cpu: o, Lvl(notset-default): Hard ";
			}
			printtop(statementtop);
		}
		else if ( marker.equals("o"))
		{
			if((int)difficultlvl.length() !=0 )
			statementtop  ="Default settings: player: o, Cpu: x, Lvl: "+difficultlvl;
			else
			{
				difficultlvl="hard";
				statementtop  ="Default settings: player: o, Cpu: x, Lvl(notset-default): Hard ";
			}
			printtop(statementtop);
		}
		else if ( difficultlvl.equals("easy"))
		{
			statementtop  ="Default settings: player: o, Cpu: x, Lvl: easy ";
			printtop(statementtop);
		}
		else if ( difficultlvl.equals("hard"))
		{
			statementtop  ="Default settings: player: o, Cpu: x, Lvl: Hard ";
			printtop(statementtop);
		}
		
		else
		{
			//statementtop  = "Default settings: player: x, Cpu: o, "+ "Lvl: "+difficultlvl;
			//printtop(statementtop);
		}
  }
  	//method that initializes the tictactoe to blanks and 0
  	//it also set the win,tie,lose status to 0.
  	//the playerswitch is for human vs human game...need to be initialize
  	//everytime the game starts
  	public void initialize_tictactoe()
  	{
  		currstatus = 0;
  		playerswitch = 1;
  		for (int i=0; i<16; i++)
			{
				cl[i].setsboxes(0);
				gamearray[i] = 0;
			}
  	}
	//function that returns the win,tie,lose status
	public String getstatus()
	{
		return status;
	}
	//function that set the player marker for the game
	public int getmarker()
	{
		
		if(marker.equals("x") )
		{return 1;}
		else
		{return 2;}
		
	}
	//function that set the opponent marker for the game
	public int getopponent()
	{
		if(marker.equals("x"))
		{return 2; }
		else
		{return 1;}
	}
	//function that returns the player
	public String gethuman()
	{
		return player;
	}
	
	///////////////////game method starts here////////////////////////////
	//this method implements human vs human. it only uses  function that finds wheter
	//the winner is player one or 2...the implementation of the search is given
	//below
	//for two player game, we need to distinguished between the first and the second
	//player.....odd is for the 1st player and even is for the second
	public int humanmove(int location)
	{
		//odd
		if(((int)playerswitch%2) == 1 && playerswitch <= 16)
		{
			printbottom("Player2 turns");
			mymove(location);	
		}
		//even
		else
		{
			printbottom("Player1 turns");
			compmove(location);
		}
		playerswitch++;
		//I win
		if (isthereawinner((int)getmarker()))
           return currstatus= 1;
         //draw --only happends when the board is full
        if (isthereawinner((int)getopponent() ) )
            return currstatus= 2;   
        for(int i=0; i<16; i++)
        {
        	//if there is one blank left then exits...no way
        	//it can be draw...
        	if (gamearray[i] ==0)
        	{
        		drawcheck = false;
        		break;
        	}
        	//got to be draw
        	else
        		drawcheck = true;
        }
        if (drawcheck == true )
            return currstatus= 3;
        //nobody win yet!!
        //return next move to class mouseinterrupt
        else
        return currstatus= 0;
	}
	//this method finds the  right marker and draws the marker on 
	// the tictactoe board...this method also sets the array to the right
	//marker condition....this function is for the human player
	public void mymove(int k)
	{
		gamearray[k] = (int)getmarker();
		Graphics app = cl[k].getGraphics();
		cl[k].changestate((int)getmarker());
		cl[k].paint(app);
		
	}
	//this method finds the  right marker and draws the marker on 
	// the tictactoe board...this method also sets the array to the right
	//marker condition....this function is for the computer player
	public void compmove(int k)
	{
		gamearray[k] = (int)getopponent();
		Graphics app = cl[k].getGraphics();
		cl[k].changestate((int)getopponent());
		cl[k].paint(app);
		
	}
	//this method implements human vs cpu. this function is almost the same as above but sligthtly 
	//different....instead of odd and even, the computer will move and sets the board.
	///this method also checks for the winners on every row, column and slanted rows
	 public int cpumove()
    {
    	int i;
    	//scan for winner x 
        if (isthereawinner((int)getmarker()))
            return currstatus= 1;
        //the most complext part of the code is explained below.
        if(difficultlvl.equals("easy"))
        {
        	i = cpumoving(difficultlvl);
        }
        else  
        {
        	i = cpumoving(difficultlvl);
        }
        //draws it to the tictactoe board and change the array.
        compmove(i);
        if (isthereawinner((int)getopponent()))
            return currstatus= 2;
         //checks whether it is draw or not.
       	for(int k=0; k<16; k++)
        {
        	//if there is one blank left then exits...no way
        	//it can be draw...
        	if (gamearray[k] ==0)
        	{
        		drawcheck = false;
        		break;
        	}
        	//got to be draw
        	else
        		drawcheck = true;
        }
        ///last check for any winners 
        if (drawcheck == true && isthereawinner((int)getopponent())== false && isthereawinner((int)getmarker()) == false )
            return currstatus= 3;
        //scan for winner o
        //no winners then go to the next round
        else
           return currstatus= 0;
    }
    //this method checks for the winner...
    //the idea on checking is based on 3x3 tictactoe given by java sun....
    //it is almost the same it needs to checks all the H-rows, V-columns,right slanted rows
    // left slanted rows. it checks to see whether 4-inarows occur
    //I wrote this code separately....and may need to be simplified.
	public boolean isthereawinner(int i1)
    {
        int i3 = 0;
        //checks all horizontal columns
        for (int j1 = 1; j1 <= boardsize; j1++)
        {
        	//added int k2 = 0
        	
            for (int k2 = (j1*boardsize)-4; k2 < j1 * boardsize; k2++)
                if (gamearray[k2] == i1)
                    i3++;
            //if all four of them are x or o then declare the winner        
            if (i3 == boardsize)
                return true;
            i3 = 0;
        }
        
        //check all the vertical rows
        for (int k1 = 0; k1 < boardsize; k1++)
        {
        	//added int
            for (int k2 = 0; k2 < wholesize; k2 += boardsize)
                if (gamearray[k1 + k2] == i1)
                    i3++;
            if (i3 == boardsize)
                return true;
            i3 = 0;
        }
        //checks all the slanted boxes right..
        for (int i2 = 0; i2 < wholesize; i2 += boardsize + 1)
            if (gamearray[i2] == i1)
                i3++;
        if (i3 == boardsize)
            return true;
            
         i3 = 0;
        //check all the slanted boxes left....
        for (int j2 = boardsize - 1; j2 <= wholesize - boardsize; j2 += boardsize - 1)
            if (gamearray[j2] == i1)
                i3++;
        if (i3 == boardsize)
            return true;
        else
            return false;
    }
    
    //this method handles the movement of the cpu.
    //the complete descriptions of this method can be read inside the method itself
    //it uses the idea from 3x3 only more complicated....lot complicated....
    ///may not be perfect but this method covers most important gaps.
    //the easy is implemented by less variable checking than hard mode.
    //can be seen below.
    public int cpumoving(String level)
    {
        int k3 = -1;
        int j3 = 0;
    
        //added i3 = 0
        int i3 = 0;
        //find one blank on the horizontal column. and return it.
        //only finding if there are 3 x's or 3'o's therefore an o can
        //be placed.
        for (int i1 = 1; i1 <= boardsize; i1++)
        {
        	//added int k2 =0
            for (int k2 = (i1*boardsize)-4; k2 < i1 * boardsize; k2++)
            {
                if (gamearray[k2] == 1)
                    i3++;
                else if (gamearray[k2] == 2)
                    j3++;
                else if (gamearray[k2] == 0)
                    k3 = k2;
            }

            if ((i3 == boardsize - 1 || j3 == boardsize - 1) && k3 != -1)
                return k3;
            j3 = i3 = 0;
            k3 = -1;
        }
        
         //find one blank on the vertical column. and return it.
        //only finding if there are 3 x's or 3'o's therefore an o can
        //be placed.
        for (int j1 = 0; j1 < boardsize; j1++)
        {
        	//addded int k2
            for (int k2 = 0; k2 < wholesize; k2 += boardsize)
            {
                if (gamearray[j1 + k2] == 1)
                    i3++;
                else if (gamearray[j1 + k2] == 2)
                    j3++;
                else if (gamearray[j1 + k2] == 0)
                    k3 = j1 + k2;
            }
            if ((i3 == boardsize - 1 || j3 == boardsize - 1) && k3 != -1)
                return k3;
            j3 = i3 = 0;
            k3 = -1;
        }
        /*j3 = i3 = 0;
        k3 = -1;*/
        
        if (level.equals("hard"))
        {
        	if ((int)hardmode1() != 20)
        		return (int)hardmode1(); 
        }
        
        
            
            //0is not blank and 7 is also not blank than handle 7
        if (gamearray[wholesize % 2] != 0 && gamearray[(wholesize - 1) / 2] == 0)
            return (wholesize - 1) / 2;
            //0is not blank and 0 is also not blank than handle 0
        if (gamearray[wholesize % 2] != 0 && gamearray[0] == 0)
            return 0;
            //0is not blank and 3 is also not blank than handle 3
        if (gamearray[wholesize % 2] != 0 && gamearray[boardsize - 1] == 0)
            return boardsize - 1;
            //0is not blank and 12 is also not blank than handle 12
        if (gamearray[wholesize % 2] != 0 && gamearray[wholesize - boardsize] == 0)
            return wholesize - boardsize;
            //0is not blank and 15 is also not blank than handle 15
        if (gamearray[wholesize % 2] != 0 && gamearray[wholesize - 1] == 0)
            return wholesize - 1;
       
        if (level.equals("hard"))
        {
        	if ((int)hardmode2() != 20)
        		return (int)hardmode2(); 
        		
        }
        
        //handle 0
        if (gamearray[0] == 0)
            return 0;
        //handle 3    
        if (gamearray[boardsize - 1] == 0)
            return boardsize - 1;
        //handle 12
        if (gamearray[wholesize - boardsize] == 0)
            return wholesize - boardsize;
         //handle 15   
        if (gamearray[wholesize - 1] == 0)
            return wholesize - 1;
        else
        {
            for(int k=0; k<16; k++)
        	{
        		//if there is one blank left then exits...no way
        		//it can be draw...
        		if (gamearray[k] ==0)
        		{
        			return k;
        		}
        	}
        	
        }
        return -1;
    }
   //this method is called only on the hard mode....
   	public int hardmode1()
   	{
   		/*j3 = i3 = 0;
        k3 = -1;*/
        int i3;
   		int j3 = i3 = 0;
        int k3 = -1;
   	   
        
        //find one blank on the slanted right column. and return it.
        //only finding if there are 3 x's or 3'o's therefore an o can
        //be placed.
        for (int k1 = 0; k1 < wholesize; k1 += boardsize + 1)
        {
            if (gamearray[k1] == 1)
                i3++;
            else if (gamearray[k1] == 2)
                j3++;
            else if (gamearray[k1] == 0)
                k3 = k1;
        }
        if ((i3 == boardsize - 1 || j3 == boardsize - 1) && k3 != -1)
            return k3;   
   		j3 = i3 = 0;
        k3 = -1;
   	     //find one blank on the slanted left column. and return it.
        //only finding if there are 3 x's or 3'o's therefore an o can
        //be placed.
        for (int i2 = boardsize - 1; i2 <= wholesize - boardsize; i2 += boardsize - 1)
        {
            if (gamearray[i2] == 1)
                i3++;
            else if (gamearray[i2] == 2)
                j3++;
            else if (gamearray[i2] == 0)
                k3 = i2;
        }
        if ((i3 == boardsize - 1 || j3 == boardsize - 1) && k3 != -1)
            return k3;   
        return 20;
   	}
   	
   	//this method is called only on the hard mode...
   	public int hardmode2()
   	{
   		  ///1 or 4 empty and 0 also empty then place it on 0 (corners)
        if ((gamearray[1] == 1 || gamearray[boardsize] == 1) && gamearray[0] == 0)
            return 0;
             ///2 or 7 empty and 3 also empty then place it on 3 (corners)
		if ((gamearray[boardsize - 2] == 1 || gamearray[boardsize * 2 - 1] == 1) && gamearray[boardsize - 1] == 0)
            return boardsize - 1;
            ///8 or 13 empty and 12 also empty then place it on 12 (corners)
        if ((gamearray[wholesize - boardsize * 2] == 1 || gamearray[wholesize - boardsize + 1] == 1) && gamearray[wholesize - boardsize] == 0)
            return wholesize - boardsize;
            ///11 or 14 empty and 12 also empty then place it on 15 (corners)
        if ((gamearray[wholesize - boardsize - 1] == 1 || gamearray[wholesize - 2] == 1) && gamearray[wholesize - 1] == 0)
            return wholesize - 1;
            
   		 int j2 = 1;
        
        //added int k2
        //check 1  and 13 , 2 and 14
        for (int k2 = wholesize - boardsize + 1; k2 < wholesize - 1; k2++)
        {
            if (gamearray[j2] == 0)
                return j2;
            if (gamearray[k2] == 0)
                return k2;
            j2++;
        }
   		j2 = boardsize;
         //added int k2
         //covers 4,7--5,8--6,9--7,10--8,11--
        for (int k2 = boardsize * 2 - 1; k2 < wholesize - boardsize; k2++)
        {
            if (gamearray[j2] == 0)
                return j2;
            if (gamearray[k2] == 0)
                return k2;
            j2++;
        }
        return 20;
   	}
	//repainting the tictactoe board..
	public void repaint()
	{
		initialize_tictactoe();
		for (int i = 0; i<16 ; i++)
		{
			///Graphics app = cl[i].getGraphics();
			cl[i].repaint();
		}
	}
	//printing the bottom label
	public void printtop(String statementtop)
	{
		message.setText(statementtop);
	}
	//printing the top label
	public void printbottom(String argument)
	{
		message2.setText(argument);
	}
	
}

