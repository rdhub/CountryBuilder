/**
 * @author Richard Du
 * @version 11/6/2014
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class EconGame extends JApplet
{
	private static final int MAP_WIDTH = 1180;
	private static final int MAP_HEIGHT = 720;
	private MenuPanel mPanel;
	private InstructionPanel iPanel;
	private GamePanel gPanel;
	private CityPanel cPanel;
	private EndPanel ePanel;
	private CardLayout cards;
	private Container c;
	private Image map, farmTile, houseTile, factoryTile, storeTile, field, farm, house, factory, store, bank;
	private Image farm2, house2, factory2, store2;
	private Image diamond, water;
	private Image pg1, pg2, pg3, pg4, pg5, pg6, pg7, pg8, pg9;
	private boolean losAngeles, newYorkCity, chicago, philadelphia;
	private boolean dallas, columbus, albany;
	private boolean gameStarted, supplySideUsed, bBonds, sBonds;
	private boolean negativeGovtFunds, rrFailure, citiesBankrupt;
	private double inflationRate, rr, dr, crowdingOutEffect;
	private int shiftAD, shiftAS, shiftLRAS, shiftMD, shiftMS, permShiftAD;
	
	public EconGame()
	{
		losAngeles = newYorkCity = chicago = philadelphia = false;
		dallas = columbus = albany = false;
		gameStarted = false;
		supplySideUsed = false;
		sBonds = bBonds = false;
		negativeGovtFunds = rrFailure = citiesBankrupt = false;
		inflationRate = 0.02;
		rr = 0.10;
		dr = 0.03;
		crowdingOutEffect = 1.0;
	}
	public void init()
	{
		map = getImage( getDocumentBase ( ), "MapOfUSA.png");
		WaitForImage ( this, map );
		
		field = getImage( getDocumentBase ( ), "Field.jpg");
		WaitForImage ( this, field );
		
		farmTile = getImage( getDocumentBase ( ), "FarmLand.jpg");
		WaitForImage ( this, farmTile );
		
		houseTile = getImage( getDocumentBase ( ), "ConstructionLand.jpg");
		WaitForImage ( this, houseTile );
		
		factoryTile = getImage( getDocumentBase ( ), "ConstructionLand2.jpg");
		WaitForImage ( this, factoryTile );
		
		storeTile = getImage( getDocumentBase ( ), "ConstructionLand3.jpg");
		WaitForImage ( this, storeTile );
		
		farm = getImage( getDocumentBase ( ), "Farm.jpg");
		WaitForImage ( this, farm );
		
		farm2 = getImage( getDocumentBase ( ), "Farm2.jpg");
		WaitForImage ( this, farm2 );
		
		house = getImage( getDocumentBase ( ), "House.jpg");
		WaitForImage ( this, house );
		
		house2 = getImage( getDocumentBase ( ), "House2.jpg");
		WaitForImage ( this, house2 );
		
		factory = getImage( getDocumentBase ( ), "Factory.jpg");
		WaitForImage ( this, factory );
		
		factory2 = getImage( getDocumentBase ( ), "Factory2.jpg");
		WaitForImage ( this, factory2 );
		
		store = getImage( getDocumentBase ( ), "Store.jpg");
		WaitForImage ( this, store );
		
		store2 = getImage( getDocumentBase ( ), "Store2.jpg");
		WaitForImage ( this, store2 );
		
		bank = getImage( getDocumentBase ( ), "Bank.jpg");
		WaitForImage ( this, bank );
		
		pg1 = getImage( getDocumentBase ( ), "Page 1.png");
		WaitForImage ( this, pg1 );
		
		pg2 = getImage( getDocumentBase ( ), "Page 2.png");
		WaitForImage ( this, pg2 );
		
		pg3 = getImage( getDocumentBase ( ), "Page 3.png");
		WaitForImage ( this, pg3 );
		
		pg4 = getImage( getDocumentBase ( ), "Page 4.png");
		WaitForImage ( this, pg4 );
		
		pg5 = getImage( getDocumentBase ( ), "Page 5.png");
		WaitForImage ( this, pg5 );
		
		pg6 = getImage( getDocumentBase ( ), "Page 6.png");
		WaitForImage ( this, pg6 );
		
		pg7 = getImage( getDocumentBase ( ), "Page 7.png");
		WaitForImage ( this, pg7 );
		
		pg8 = getImage( getDocumentBase ( ), "Page 8.png");
		WaitForImage ( this, pg8 );
		
		pg9 = getImage( getDocumentBase ( ), "Page 9.png");
		WaitForImage ( this, pg9 );
		
		diamond = getImage( getDocumentBase ( ), "Diamond.png");
		WaitForImage ( this, diamond );
		
		water = getImage( getDocumentBase ( ), "Water.png");
		WaitForImage ( this, water );	
		
		
		c = this.getContentPane();
		
		cards = new CardLayout();
		c.setLayout(cards);
		
		mPanel = new MenuPanel();
		c.add(mPanel, "MenuPanel");
		
		iPanel = new InstructionPanel();
		c.add(iPanel, "InstructionPanel");
		
		gPanel = new GamePanel();
		c.add(gPanel, "GamePanel");
		
		cPanel = new CityPanel();
		c.add(cPanel, "CityPanel");
		
		ePanel = new EndPanel();
		c.add(ePanel, "EndPanel");
	}
	
	public void WaitForImage ( JApplet component, Image image )
	{
		MediaTracker tracker = new MediaTracker ( component );
		try
		{
			tracker.addImage ( image, 0 );
			tracker.waitForID ( 0 );
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace ( );
		}
	}
	class MenuPanel extends JPanel implements ActionListener
	{
		private int diamondx, diamondy, waterx, watery, ring, ring2, ring3;
		private int[] t, t2, t3;
		private boolean ringOut;
		private Timer clock;
		
		public MenuPanel()
		{
			this.setLayout(null);
			this.setBackground(Color.white);
			JButton start = new JButton("Start");
			start.setBounds(550,570,120,50);
			start.addActionListener(this);
			this.add(start);
			JButton instructions = new JButton("Instructions");
			instructions.setBounds(710,570,120,50);
			instructions.addActionListener(this);
			this.add(instructions);
			
			diamondx = diamondy = 0;
			waterx = watery = 0;
			t = new int[3];
			t2 = new int[3];
			t3 = new int[3];
			for(int i = 0; i < 3; i++)
			{
				t[i] = 75 + i*150;	
				t3[i] = 75 + i*150;
				t2[i] = 150 + i*150;
			}
			ring = ring3 = 150;
			ring2 = 75;
			ringOut = false;
			clock = new Timer(10, this);
			clock.start();
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int green = 0;
			int red = 255;
			for(int i = 0; i < 255; i++)
			{
				g.setColor(new Color(red, green, 0));
				g.drawRect(0+i, 0+i, 1380 - i*2, 720 - i*2);
				if(green == 255)
					red--;
				if(green < 255)
					green++;
			}
			for(int i = 255; i < 361; i++)
			{
				g.setColor(new Color(red, green, 0));
				g.drawRect(0+i, 0+i, 1380 - i*2, 720 - i*2);
				green--;
			}
			g.setColor(Color.black);
			g.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50));
			g.drawString("Country Builder", 460,100);
			
			for(int i = 0; i < 3; i++)
			{
				diamondx = 620 + (int)(150 * Math.cos(0.005 * Math.PI * -t[i]));
				diamondy = 300 + (int)(150 * Math.sin(0.005 * Math.PI * -t[i]));
				waterx = 620 + (int)(150 * Math.cos(0.005 * Math.PI * -t2[i]));
				watery = 300 + (int)(150 * Math.sin(0.005 * Math.PI * -t2[i]));
				g.drawImage(diamond, diamondx, diamondy, 150, 100, this);
				g.drawImage(water, waterx + 40, watery, 71, 100, this);
				
				diamondx = 620 + (int)(ring * Math.cos(0.005 * Math.PI * t[i]));
				diamondy = 300 + (int)(ring * Math.sin(0.005 * Math.PI * t[i]));
				waterx = 620 + (int)(ring * Math.cos(0.005 * Math.PI * t2[i]));
				watery = 300 + (int)(ring * Math.sin(0.005 * Math.PI * t2[i]));
				g.drawImage(diamond, diamondx, diamondy, 150, 100, this);
				g.drawImage(water, waterx + 40, watery, 71, 100, this);
				
				diamondx = 620 + (int)(ring2 * Math.cos(0.005 * Math.PI * t[i]));
				diamondy = 300 + (int)(ring2 * Math.sin(0.005 * Math.PI * t[i]));
				waterx = 620 + (int)(ring2 * Math.cos(0.005 * Math.PI * t2[i]));
				watery = 300 + (int)(ring2 * Math.sin(0.005 * Math.PI * t2[i]));
				g.drawImage(diamond, diamondx, diamondy, 150, 100, this);
				g.drawImage(water, waterx + 40, watery, 71, 100, this);
			}
			g.setFont(new Font("Times New Roman", Font.BOLD, 100));
			g.drawString("$", 5, 80);
			g.drawString("$", 5, 705);
			g.drawString("$", 1325, 80);
			g.drawString("$", 1325, 705);
		}
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			for(int i = 0; i < 3; i++)
			{
				t[i]++;
				t2[i]++;
			}
			if(!ringOut)
				ring--;
			if(ringOut)
				ring++;
			if(ring == 0)
			{
				ringOut = true;
			}
			if(ring == 150)
				ringOut = false;
			if("Start".equals(command))
			{
				gameStarted = true;
				gPanel.startTimer();
				cPanel.startTimer();
				cards.show(c, "GamePanel");
			}
			if("Instructions".equals(command))
			{
				cards.show(c, "InstructionPanel");
			}
			this.repaint();
		}
	}
	class InstructionPanel extends JPanel implements ActionListener
	{
		private JButton close, left, right;
		private int index;
		private Image[] pages;
		
		public InstructionPanel()
		{
			this.setLayout(null);
			this.setBackground(Color.white);
			left = new JButton("Previous");
			left.setBounds(MAP_WIDTH,0,100,20);
			left.addActionListener(this);
			this.add(left);
			right = new JButton("Next");
			right.setBounds(MAP_WIDTH + 100,0,100,20);
			right.addActionListener(this);
			this.add(right);
			close = new JButton("Close");
			close.setBounds(MAP_WIDTH,20,200,20);
			close.addActionListener(this);
			this.add(close);
			
			index = 0;
			pages = new Image[9];
			pages[0] = pg1;
			pages[1] = pg2;
			pages[2] = pg3;
			pages[3] = pg4;
			pages[4] = pg5;
			pages[5] = pg6;
			pages[6] = pg7;
			pages[7] = pg8;
			pages[8] = pg9;
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(pages[index], 0, 0, 1380, 720, this);
			g.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g.setColor(Color.black);
			g.drawString("INSTRUCTIONS", 10, 60);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if(command.equals("Close"))
			{
				if(gameStarted)
				{
					cards.show(c, "GamePanel");
					gPanel.startTimer();
					cPanel.startTimer();
				}
				else
					cards.show(c, "MenuPanel");
			}
			if(command.equals("Previous"))
			{
				index--;
				if(index < 0)
					index = pages.length - 1;
				this.repaint();
			}
			if(command.equals("Next"))
			{
				index++;
				if(index == pages.length)
					index = 0;
				this.repaint();
			}
			
		}
	}
	class GamePanel extends JPanel implements MouseListener, ActionListener
	{
		private GraphsPanel graphsP;
		private GovernmentPanel govtPanel;
		private ArrayList<City> cities;
		private Timer time;
		private long startTime, currentTime;
		private double taxRate, MPC, MPS;
		private boolean graphsOpen, govtOpen, govtPolicyUsed;
		private int fedReserves, govtFunds, govtSpendingAmount, taxCut, taxRevenue, bondPrice;
		private int day, year, monthNum;
		private JButton graphs, government;
		private String[] months = {"January ", "February ", "March ", "April ", "May ", "June ",
									"July ", "August ", "September ", "October ", "November ", "December "};
		
		
		public GamePanel()
		{
			this.setLayout(null);
			this.setBackground(Color.white);
			this.addMouseListener(this);
			graphsP = new GraphsPanel();
			graphsP.setBounds((MAP_WIDTH-400)/2, (MAP_HEIGHT-400)/2, 400, 400);
			graphsP.setVisible(false);
			this.add(graphsP);
			
			govtPanel = new GovernmentPanel();
			govtPanel.setBounds((MAP_WIDTH-400)/2, (MAP_HEIGHT-230)/2, 400, 230);
			govtPanel.setVisible(false);
			this.add(govtPanel);
			
			graphs = new JButton("Graphs");
			graphs.setBounds(MAP_WIDTH + 5, MAP_HEIGHT-100, 195, 100);
			graphs.addActionListener(this);
			this.add(graphs);
			government = new JButton("Government");
			government.setBounds(MAP_WIDTH + 5, MAP_HEIGHT-200, 195, 100);
			government.addActionListener(this);
			this.add(government);
			JButton ingameInstructions = new JButton("Instructions");
			ingameInstructions.setBounds(MAP_WIDTH + 5, 0, 195, 20);
			ingameInstructions.addActionListener(this);
			this.add(ingameInstructions);
			time = new Timer(1000, this);
			year = monthNum = 1;
			day = 0;
			startTime = currentTime = 0;
			fedReserves = 500000;
			govtFunds = 100000;
			govtSpendingAmount = 10000;
			taxRate = 0.05;
			graphsOpen = govtOpen = govtPolicyUsed = false;
			MPC = 0.6;
			MPS = 0.4;
			taxCut = 0;
			taxRevenue = 0;
			bondPrice = 14000;
		}
		public void reset()
		{
			year = monthNum = 1;
			day = 0;
			startTime = currentTime = 0;
			fedReserves = 500000;
			govtFunds = 100000;
			govtSpendingAmount = 10000;
			taxCut = 0;
			taxRate = 0.05;
			graphsOpen = govtOpen = govtPolicyUsed = supplySideUsed = false;
			sBonds = bBonds = false;
			negativeGovtFunds = rrFailure = citiesBankrupt = false;
			MPC = 0.6;
			MPS = 0.4;
			rr = 0.10;
			dr = 0.03;
			taxRevenue = 0;
			bondPrice = 14000;
			crowdingOutEffect = 1.0;
			
			graphsP.reset();
			graphsP.setVisible(false);
			govtPanel.reset();
			govtPanel.setVisible(false);
		}
		public void startTimer()
		{
			time.start();
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			cities = cPanel.getCities();
			g.drawImage(map, 0, 0, MAP_WIDTH, MAP_HEIGHT, this);
			g.setColor(Color.black);
			g.fillRect(MAP_WIDTH, 0, 5, 720);
			
			if(cities.get(0).getCityFunds() < 0 || cities.get(0).getRequiredReserves() < (int)(cities.get(0).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("Los Angeles", 135, 425);
			if(cities.get(1).getCityFunds() < 0 || cities.get(1).getRequiredReserves() < (int)(cities.get(1).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("Dallas", 585, 515);
			
			if(cities.get(2).getCityFunds() < 0 || cities.get(2).getRequiredReserves() < (int)(cities.get(2).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("Columbus", 855, 315);
			
			if(cities.get(3).getCityFunds() < 0 || cities.get(3).getRequiredReserves() < (int)(cities.get(3).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("New York City", 1030, 255);
			
			if(cities.get(4).getCityFunds() < 0 || cities.get(4).getRequiredReserves() < (int)(cities.get(4).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("Chicago", 760, 270);
			
			if(cities.get(5).getCityFunds() < 0 || cities.get(5).getRequiredReserves() < (int)(cities.get(5).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("Philadelphia", 1005, 280);
			
			if(cities.get(6).getCityFunds() < 0 || cities.get(6).getRequiredReserves() < (int)(cities.get(6).getDemandDeposits() * rr))
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			g.drawString("Albany", 870, 515);
			
			g.setColor(Color.black);
			g.fillOval(110,410,20,20);	//LosAngeles
			g.fillOval(560,500,20,20);	//Dallas
			g.fillOval(830,300,20,20);	//Columbus
			g.fillOval(1005,240,20,20);	//New York City
			g.fillOval(735,255,20,20);	//Chicago
			g.fillOval(980,265,20,20);	//Philadelphia
			g.fillOval(845,500,20,20);	//Albany
			
			g.drawString("Year: " + year, MAP_WIDTH + 30, 85);
			g.drawString(months[monthNum-1] + day, MAP_WIDTH + 30, 100);
			g.drawString("Fed Reserves: $" + fedReserves, MAP_WIDTH + 30, 145);
			g.drawString("Bond Price: $" + bondPrice, MAP_WIDTH + 30, 160);
			g.drawString("Reserve Requirement: " + (rr*100) + "%", MAP_WIDTH + 30, 175);
			g.drawString("Gov't Funds: $" + govtFunds, MAP_WIDTH + 30, 205);
			g.drawString("Gov't Spending: $" + govtSpendingAmount, MAP_WIDTH + 30, 220);
			g.drawString(String.format("Tax Rate: %.2f" , (taxRate*100)) + "%", MAP_WIDTH + 30, 250);
			g.drawString("Tax Revenue: $ " + taxRevenue, MAP_WIDTH + 30, 265);
			g.drawString("Inflation: " + (inflationRate*100) + "%", MAP_WIDTH + 30, 295);
		}
		public void mouseClicked(MouseEvent e)
		{
			int y = e.getY();
			int x = e.getX();
			if(!graphsOpen && !govtOpen)
			{
				if(x >= 110 && x <= 130 && y >= 410 && y <= 430)
				{
					losAngeles = true;
					cards.show(c, "CityPanel");
				}
				if(x >= 560 && x <= 580 && y >= 500 && y <= 520)
				{
					dallas = true;
					cards.show(c, "CityPanel");
				}
				if(x >= 830 && x <= 850 && y >= 300 && y <= 320)
				{
					columbus = true;
					cards.show(c, "CityPanel");
				}
				if(x >= 1005 && x <= 1025 && y >= 240 && y <= 260)
				{
					newYorkCity = true;
					cards.show(c, "CityPanel");
				}
				if(x >= 735 && x <= 755 && y >= 255 && y <= 275)
				{
					chicago = true;
					cards.show(c, "CityPanel");
				}
				if(x >= 980 && x <= 1000 && y >= 265 && y <= 285)
				{
					philadelphia = true;
					cards.show(c, "CityPanel");
				}
				if(x >= 845 && x <= 865 && y >= 500 && y <= 520)
				{
					albany = true;
					cards.show(c, "CityPanel");
				}
			}
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			boolean monthEnd = false;
			if(command == null)
			{
				if(startTime == 0)
					startTime = e.getWhen();
				currentTime = e.getWhen();
				//~ day = (int)(currentTime - startTime)/1000 + 300;
				day = (int)(currentTime - startTime)/1000 + 1;
				switch(monthNum)
				{
					case 1: case 3: case 5: case 7: case 8: case 10: case 12:
						if(day > 31)
						{
							monthEnd = true;
						}
						break;
					case 4: case 6: case 9: case 11:
						if(day > 30)
						{
							monthEnd = true;
						}
						break;
					case 2:
						if(day > 28)
						{
							monthEnd = true;
						}
						break;
				}
				if(monthEnd)
				{
					//bonds
					if(monthNum % 2 == 0)
					{
						for(int i = 0; i < cities.size(); i++)
						{
							if(bBonds)
							{
								cities.get(i).changeCityFunds((int)(bondPrice * 1/rr)/7);
								fedReserves -= bondPrice;
							}
							else if(sBonds)
							{
								cities.get(i).changeCityFunds(-(int)(bondPrice * 1/rr)/7);
								fedReserves += bondPrice;
							}
						}
					}
					//taxes
					if(monthNum % 3 == 0)
					{
						taxRevenue = 0;
						for(int i = 0; i < cities.size(); i++)
						{
							if(cities.get(i).getCityFunds() > 0)
							{
								int taxAmount = (int)(cities.get(i).getCityFunds() * taxRate) - (int)(taxCut * MPC/MPS);
								if(taxAmount < 0)
									taxAmount = 0;
									
								taxRevenue += taxAmount;	
								cities.get(i).changeCityFunds(-taxAmount);
								
							}
						}
						govtFunds += taxRevenue;
					}
					//govt spending
					if(monthNum % 6 == 0)
					{
						for(int i = 0; i < cities.size(); i++)
						{
							cities.get(i).changeCityFunds((int)(govtSpendingAmount * 1/MPS));
						}
						govtFunds -= govtSpendingAmount * cities.size();
					}
					monthNum++;
					startTime = e.getWhen();
					day = (int)(currentTime - startTime)/1000 + 1;
					//yearEnd
					if(monthNum > 12)
					{
						sBonds = bBonds = false;
						shiftAD = permShiftAD;
						shiftAS = shiftLRAS;
						rr = 0.10;
						dr = 0.03;
						govtSpendingAmount = 10000;
						taxCut = 0;
						crowdingOutEffect = 1.0;
						bondPrice = 14000;
						
						government.setEnabled(true);
						govtPolicyUsed = false;
						
						year++;
						taxRate *= inflationRate + 1;
						monthNum = 1;
						
						rrFailure = true;
						citiesBankrupt = true;
						for(int i = 0; i < cities.size(); i++)
						{
							if(cities.get(i).getCityFunds() >= 0)
							{
								citiesBankrupt = false;
							}
							if(cities.get(i).getRequiredReserves() == (int)(cities.get(i).getDemandDeposits() * rr))
							{
								rrFailure = false;
							}
							
						}
						if(year == 4)
						{
							if(govtFunds < 0)
								negativeGovtFunds = true;
						}
						if(year == 4 || citiesBankrupt || rrFailure || negativeGovtFunds)
						{
							cPanel.stopTimer();
							time.stop();
							cards.show(c, "EndPanel");
						}
					}
				}
			}
			if("Instructions".equals(command))
			{
				cPanel.stopTimer();
				time.stop();
				cards.show(c, "InstructionPanel");
			}
			if("Graphs".equals(command))
			{
				graphsP.setVisible(true);
				government.setEnabled(false);
				graphsOpen = true;
				this.repaint();
			}
			
			if("Government".equals(command))
			{
				govtPanel.setVisible(true);
				graphs.setEnabled(false);
				govtOpen = true;
				this.repaint();
			}
			cPanel.changeTime(day, year, monthNum, monthEnd);
			cPanel.repaint();
			this.repaint();
		}
		class GraphsPanel extends JPanel implements ActionListener
		{
			private JButton exit, ADAS, MM;
			private boolean ADASGraph, moneyMarketGraph;
			//AD/AS graph, money market, exchange rate, phillips curve, production possibility curve, keynesian cross
			public GraphsPanel()
			{
				this.setLayout(null);
				this.setBackground(Color.white);
				exit = new JButton("x");
				exit.setBounds(345, 5, 50, 20);
				exit.addActionListener(this);
				this.add(exit);
				ADAS = new JButton("AD/AS");
				ADAS.setBounds(5, 5, 97, 40);
				ADAS.addActionListener(this);
				this.add(ADAS);
				MM = new JButton("Money Market");
				MM.setBounds(102, 5, 194, 40);
				MM.addActionListener(this);
				this.add(MM);
				
				ADASGraph = true;
				moneyMarketGraph = false;
				shiftAD = shiftAS = shiftLRAS = shiftMD = shiftMS = permShiftAD = 0;
			}
			public void reset()
			{
				ADASGraph = true;
				moneyMarketGraph = false;
				shiftAD = shiftAS = shiftLRAS = shiftMD = shiftMS = permShiftAD = 0;
				this.setVisible(false);
				government.setEnabled(true);
			}
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.black);
				g.fillRect(0,0, 400, 400);
				g.setColor(Color.white);
				g.fillRect(5,5, 390, 390);
				g.setColor(Color.black);
				
				g.fillRect(50,70,5,300);
				g.fillRect(50,370,300,5);
				if(ADASGraph)
				{
					g.setColor(Color.blue);
					g.drawArc(70, -150, 480, 480, 180, 90);
					g.drawArc(-170, -150, 480, 480, 270, 90);
					g.drawLine(190, 90, 190, 370);
					
					g.setColor(Color.black);
					g.drawString("Price Level", 25, 65);
					g.drawString("Real GDP", 325, 390);
					g.drawString("AS", 310 + shiftAS, 85);
					g.drawString("AD", 315 + shiftAD, 330);
					g.drawString("LRAS", 185 + shiftLRAS, 85);
					g.drawArc(70 + shiftAD, -150, 480, 480, 180, 90);
					g.drawArc(-170 + shiftAS, -150, 480, 480, 270, 90);
					g.drawLine(190 + shiftLRAS, 90, 190 + shiftLRAS, 370);
				}
				else if(moneyMarketGraph)
				{
					g.setColor(Color.blue);
					g.drawArc(70, -150, 480, 480, 180, 90);
					g.drawArc(-170, -150, 480, 480, 270, 90);
					
					g.setColor(Color.black);
					g.drawString("r", 25, 65);
					g.drawString("Quantity of Money", 295, 390);
					g.drawString("MS", 310, 85);
					g.drawString("MD", 315, 330);
					g.drawArc(70, -150, 480, 480, 180, 90);
					g.drawArc(-170, -150, 480, 480, 270, 90);
				}
				
			}
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				if("x".equals(command))
				{
					this.setVisible(false);
					government.setEnabled(true);
					graphsOpen = false;
					gPanel.repaint();
				}
				if("AD/AS".equals(command))
				{
					ADASGraph = true;
					moneyMarketGraph = false;
					this.repaint();
				}
				if("Money Market".equals(command))
				{
					moneyMarketGraph = true;
					ADASGraph = false;
					this.repaint();
				}
			}
		}
		class GovernmentPanel extends JPanel implements ActionListener
		{
			private JButton classical, fiscal, monetary, supply, expand, contract, back, ok, exit;
			private JButton govtSpending, taxes, reserveReq, discountRate, bonds;
			private boolean cPolicy, fPolicy, mPolicy, sPolicy, expandP, contractP;
			private String text, policy, result, resultLine2;
			
			public GovernmentPanel()
			{
				this.setBackground(Color.white);
				this.setLayout(null);
				
				classical = new JButton("Classical");
				classical.setBounds(5, 85, 195, 70);
				classical.addActionListener(this);
				this.add(classical);
				fiscal = new JButton("Fiscal");
				fiscal.setBounds(200, 85, 195, 70);
				fiscal.addActionListener(this);
				this.add(fiscal);
				monetary = new JButton("Monetary");
				monetary.setBounds(5, 155, 195, 70);
				monetary.addActionListener(this);
				this.add(monetary);
				supply = new JButton("Supply Side");
				supply.setBounds(200, 155, 195, 70);
				supply.addActionListener(this);
				this.add(supply);
				expand = new JButton("Expansionary");
				expand.setBounds(5, 85, 195, 120);
				expand.addActionListener(this);
				expand.setVisible(false);
				this.add(expand);
				contract = new JButton("Contractionary");
				contract.setBounds(200, 85, 195, 120);
				contract.addActionListener(this);
				contract.setVisible(false);
				this.add(contract);
				
				govtSpending = new JButton("");
				govtSpending.setBounds(5, 85, 390, 60);
				govtSpending.addActionListener(this);
				govtSpending.setVisible(false);
				this.add(govtSpending);
				taxes = new JButton("");
				taxes.setBounds(5,145,390,60);
				taxes.addActionListener(this);
				taxes.setVisible(false);
				this.add(taxes);
				reserveReq = new JButton("");
				reserveReq.setBounds(5,85,390,40);
				reserveReq.addActionListener(this);
				reserveReq.setVisible(false);
				this.add(reserveReq);
				discountRate = new JButton("");
				discountRate.setBounds(5,125,390,40);
				discountRate.addActionListener(this);
				discountRate.setVisible(false);
				this.add(discountRate);
				bonds = new JButton("");
				bonds.setBounds(5,165,390,40);
				bonds.addActionListener(this);
				bonds.setVisible(false);
				this.add(bonds);
				
				back = new JButton("Back");
				back.setBounds(5, 205, 65,20);
				back.addActionListener(this);
				back.setVisible(false);
				this.add(back);
				ok = new JButton("Okay");
				ok.setBounds(165, 205, 70, 20);
				ok.addActionListener(this);
				ok.setVisible(false);
				this.add(ok);
				exit = new JButton("x");
				exit.setBounds(345, 5, 50, 20);
				exit.addActionListener(this);
				this.add(exit);
				
				cPolicy = fPolicy = mPolicy = sPolicy = expandP = contractP = false;
				policy = "";
				text = "";
				result = resultLine2 = "";
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if(supplySideUsed)
					supply.setEnabled(false);
				g.setColor(Color.black);
				g.fillRect(0,0, 400, 230);
				g.setColor(Color.white);
				g.fillRect(5,5, 390, 220);
				g.setColor(Color.black);
				g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
				if(cPolicy || fPolicy || mPolicy || sPolicy)
				{
					g.drawString(policy, 10, 45);
					g.drawString(text, 10, 70);
				}
				else
				{
					g.drawString("How would you like to manage the", 25, 45);
					g.drawString("economy?", 160, 70);
				}
				if(result.length() > 0)
				{
					g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
					g.drawString(result, 10,100);
					g.drawString(resultLine2, 10,118);
				}
					
			}
			public void reset()
			{
				cPolicy = fPolicy = mPolicy = sPolicy = false;
				expandP = contractP = false;
				govtOpen = false;
				
				classical.setVisible(true);
				fiscal.setVisible(true);
				monetary.setVisible(true);
				supply.setVisible(true);
				back.setVisible(false);
				govtSpending.setVisible(false);
				taxes.setVisible(false);
				reserveReq.setVisible(false);
				discountRate.setVisible(false);
				bonds.setVisible(false);
				exit.setVisible(true);
				ok.setVisible(false);
				
				graphs.setEnabled(true);
				govtSpending.setEnabled(true);
				supply.setEnabled(true);
				this.setVisible(false);
			}
			public void changeVisible()
			{
				classical.setVisible(false);
				fiscal.setVisible(false);
				monetary.setVisible(false);
				supply.setVisible(false);
				if(expandP || contractP)
				{
					expand.setVisible(false);
					contract.setVisible(false);
					chooseAction();
				}
				else
				{
					if(!sPolicy && !cPolicy)
					{
						expand.setVisible(true);
						contract.setVisible(true);
						back.setVisible(true);
					}
				}
				
			}
			public void chooseAction()
			{
				if(fPolicy)
				{
					govtSpending.setVisible(true);
					taxes.setVisible(true);
					if(expandP)
					{
						govtSpending.setText("Increase Government Spending");
						taxes.setText("Cut Taxes");
					}
					else
					{
						govtSpending.setText("Decrease Government Spending");
						taxes.setText("Increase Taxes");
						if(govtSpendingAmount == 0)
							govtSpending.setEnabled(false);
					}
				}
				if(mPolicy)
				{
					reserveReq.setVisible(true);
					discountRate.setVisible(true);
					bonds.setVisible(true);
					if(expandP)
					{
						reserveReq.setText("Lower Reserve Requirement");
						discountRate.setText("Lower Discount Rate");
						bonds.setText("Fed Buys Bonds");
					}
					else
					{
						reserveReq.setText("Raise Reserve Requirement");
						discountRate.setText("Raise Discount Rate");
						bonds.setText("Fed Sells Bonds");
					}
				}
			}
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				result = "";
				if("x".equals(command))
				{
					this.setVisible(false);
					reset();
					gPanel.repaint();
				}
				if("Okay".equals(command))
				{
					this.setVisible(false);
					government.setEnabled(false);
					reset();
					gPanel.repaint();
				}
				if(command.equals("Classical"))
				{
					exit.setVisible(false);
					cPolicy = true;
					policy = "Classical";
					ok.setVisible(true);
					back.setVisible(false);
					changeVisible();
					result = "The government is feeling lazy this year.";
				}
				if(command.equals("Fiscal"))
				{
					fPolicy = true;
					policy = "Fiscal";
					changeVisible();
				}
				if(command.equals("Increase Government Spending"))
				{
					shiftAD += 20;
					shiftMD += 20;
					govtSpendingAmount += 5000;
					crowdingOutEffect = 0.2;
					bondPrice -= 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					govtSpending.setVisible(false);
					taxes.setVisible(false);
					govtPolicyUsed = true;
					result = "Government spending has increased by $5000.";
				}
				if(command.equals("Cut Taxes"))
				{
					shiftAD += 20;
					taxCut += 5000;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					govtSpending.setVisible(false);
					taxes.setVisible(false);
					govtPolicyUsed = true;
					result = "Taxes have been cut by $5000";
				}
				if(command.equals("Decrease Government Spending"))
				{
					shiftAD -= 20;
					shiftMD -= 20;
					govtSpendingAmount -= 5000;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					govtSpending.setVisible(false);
					taxes.setVisible(false);
					govtPolicyUsed = true;
					result = "Government spending has decreased by $5000.";
				}
				if(command.equals("Increase Taxes"))
				{
					shiftAD -= 20;
					taxCut -= 5000;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					govtSpending.setVisible(false);
					taxes.setVisible(false);
					govtPolicyUsed = true;
					result = "Taxes have been increased by $5000";
				}
				if(command.equals("Monetary"))
				{
					mPolicy = true;
					policy = "Monetary";
					changeVisible();
				}
				if(command.equals("Lower Reserve Requirement"))
				{
					shiftMS += 20;
					shiftAD += 20;
					rr = 0.05;
					bondPrice += 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					reserveReq.setVisible(false);
					discountRate.setVisible(false);
					bonds.setVisible(false);
					govtPolicyUsed = true;
					result = "The Reserve Requirement is lowered to 5%.";
				}
				if(command.equals("Lower Discount Rate"))
				{
					shiftMS += 20;
					shiftAD += 20;
					dr = 0.01;
					bondPrice += 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					reserveReq.setVisible(false);
					discountRate.setVisible(false);
					bonds.setVisible(false);
					govtPolicyUsed = true;
					result = "The Discount Rate is lowered to 1%.";
				}
				if(command.equals("Fed Buys Bonds"))
				{
					shiftMS += 20;
					shiftAD += 20;
					bondPrice += 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					reserveReq.setVisible(false);
					discountRate.setVisible(false);
					bonds.setVisible(false);
					govtPolicyUsed = true;
					bBonds = true;
					result = "The Fed will buy bonds for a year.";
				}
				if(command.equals("Raise Reserve Requirement"))
				{
					shiftMS -= 20;
					shiftAD -= 20;
					rr = 0.15;
					bondPrice -= 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					reserveReq.setVisible(false);
					discountRate.setVisible(false);
					bonds.setVisible(false);
					govtPolicyUsed = true;
					result = "The Reserve Requirement is raised to 15%.";
				}
				if(command.equals("Raise Discount Rate"))
				{
					shiftMS -= 20;
					shiftAD -= 20;
					dr = 0.05;
					bondPrice -= 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					reserveReq.setVisible(false);
					discountRate.setVisible(false);
					bonds.setVisible(false);
					govtPolicyUsed = true;
					result = "The Discount Rate is raised to 5%.";
				}
				if(command.equals("Fed Sells Bonds"))
				{
					shiftMS -= 20;
					shiftAD -= 20;
					bondPrice -= 3500;
					exit.setVisible(false);
					ok.setVisible(true);
					back.setVisible(false);
					reserveReq.setVisible(false);
					discountRate.setVisible(false);
					bonds.setVisible(false);
					govtPolicyUsed = true;
					sBonds = true;
					result = "The Fed will sell bonds for a year.";
				}
				if(command.equals("Supply Side"))
				{
					shiftLRAS += 20;
					shiftAS += 20;
					exit.setVisible(false);
					sPolicy = true;
					policy = "Supply Side";
					supplySideUsed = true;
					govtPolicyUsed = true;
					ok.setVisible(true);
					back.setVisible(false);
					changeVisible();
					result = "Businesses have been deregulated, doubling";
					resultLine2 = "production rate.";
				}
				if(command.equals("Expansionary"))
				{
					text = "Expansionary";
					expandP = true;
					changeVisible();
				}
				if(command.equals("Contractionary"))
				{
					text = "Contractionary";
					contractP = true;
					changeVisible();
				}
				if(command.equals("Back"))
				{
					if(expandP || contractP)
					{
						text = "";
						govtSpending.setVisible(false);
						taxes.setVisible(false);
						reserveReq.setVisible(false);
						discountRate.setVisible(false);
						bonds.setVisible(false);
						expandP = contractP = false;
						expand.setVisible(true);
						contract.setVisible(true);
					}
					else
					{
						cPolicy = fPolicy = mPolicy = sPolicy = false;
						classical.setVisible(true);
						fiscal.setVisible(true);
						monetary.setVisible(true);
						supply.setVisible(true);
						expand.setVisible(false);
						contract.setVisible(false);
						back.setVisible(false);
					}
				}
				this.repaint();
			}
		}
	}
	
	class CityPanel extends JPanel implements ActionListener, MouseListener
	{
		private PlotPanel pPanel;
		private ArrayList<City> cities;
		private City LosAngeles, NewYorkCity, Chicago, Philadelphia; //Population/Industry
		private City Dallas, Columbus, Albany; //Farms
		private City currentCity;
		private int farmCost, factoryCost, storeCost, houseCost, farm2Cost, factory2Cost, store2Cost, house2Cost;
		private int supplySideFactor;
		private double goodsPrice;
		private JButton mapReturn;
		private int[][] farmL, houseL, factoryL;
		private int[] storeL;
		private int day, year, monthNum;
		private boolean manageP, monthEnd;
		private boolean buying;
		private Timer time;
		private String[] months = {"January ", "February ", "March ", "April ", "May ", "June ",
									"July ", "August ", "September ", "October ", "November ", "December "};
		public CityPanel()
		{
			this.setLayout(null);
			this.setBackground(Color.white);
			this.addMouseListener(this);
			
			pPanel = new PlotPanel();
			pPanel.setBounds((MAP_WIDTH-300)/2,(MAP_HEIGHT-200)/2,300,200);
			pPanel.setVisible(false);
			this.add(pPanel);
			
			mapReturn = new JButton("Return to Map");
			mapReturn.setBounds(MAP_WIDTH + 5, 700, 195, 20);
			mapReturn.addActionListener(this);
			this.add(mapReturn);
			
			cities = new ArrayList<City>(12);
			//normal city: 12,8,8
			LosAngeles = new City("Los Angeles", 4, 12, 12, 4);
			NewYorkCity = new City("New York City", 4, 12, 12, 4);
			Chicago = new City("Chicago", 4, 12, 12, 4);
			Philadelphia = new City("Philadelphia", 4, 12, 12, 4);
			
			Dallas = new City("Dallas", 16, 6, 6, 2);
			Columbus = new City("Columbus", 16, 6, 6, 2);
			Albany = new City("Albany", 16, 6, 6, 2);
			
			cities.add(LosAngeles);
			cities.add(NewYorkCity);
			cities.add(Chicago);
			cities.add(Philadelphia);
			cities.add(Dallas);
			cities.add(Columbus);
			cities.add(Albany);
			
			time = new Timer(1000, this);
			manageP = buying = monthEnd = false;
			day = year = monthNum = 1;
			farmCost = 15000;
			factoryCost = 30000;
			storeCost = 30000;
			houseCost = 25000;
			
			farm2Cost = (int)(1.5*farmCost);
			factory2Cost = (int)(1.5*factoryCost);
			store2Cost = (int)(1.5*storeCost);
			house2Cost = (int)(1.5*houseCost);
			goodsPrice = 5;
			supplySideFactor = 1;
		}
		public void reset()
		{
			cities = new ArrayList<City>(12);
			LosAngeles = new City("Los Angeles", 4, 12, 12, 4);
			NewYorkCity = new City("New York City", 4, 12, 12, 4);
			Chicago = new City("Chicago", 4, 12, 12, 4);
			Philadelphia = new City("Philadelphia", 4, 12, 12, 4);
			
			Dallas = new City("Dallas", 16, 6, 6, 2);
			Columbus = new City("Columbus", 16, 6, 6, 2);
			Albany = new City("Albany", 16, 6, 6, 2);
			
			cities.add(LosAngeles);
			cities.add(NewYorkCity);
			cities.add(Chicago);
			cities.add(Philadelphia);
			cities.add(Dallas);
			cities.add(Columbus);
			cities.add(Albany);
			
			manageP = buying = monthEnd = false;
			day = year = monthNum = 1;
			farmCost = 15000;
			factoryCost = 30000;
			storeCost = 30000;
			houseCost = 25000;
			
			farm2Cost = (int)(1.5*farmCost);
			factory2Cost = (int)(1.5*factoryCost);
			store2Cost = (int)(1.5*storeCost);
			house2Cost = (int)(1.5*houseCost);
			goodsPrice = 5.00;
			supplySideFactor = 1;
			
			pPanel.reset();
			pPanel.setVisible(false);
		}
		public ArrayList<City> getCities()
		{
			return cities;
		}
		public void changeTime(int d, int y, int m, boolean end)
		{
			day = d;
			year = y;
			monthNum = m;
			monthEnd = end;
			if(monthEnd)
			{
				goodsPrice *= 1 + inflationRate;
				houseCost *= 1 + inflationRate;
				house2Cost *= 1 + inflationRate;
				factoryCost *= 1 + inflationRate;
				factory2Cost *= 1 + inflationRate;
				houseCost *= 1 + inflationRate;
				house2Cost *= 1 + inflationRate;
				storeCost *= 1 + inflationRate;
				store2Cost *= 1 + inflationRate;
			}
		}
		public void startTimer()
		{
			pPanel.startTimer();
			time.start();
		}
		public void stopTimer()
		{
			pPanel.stopTimer();
			time.stop();
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if(!manageP)
				mapReturn.setEnabled(true);
			g.setColor(Color.black);
			g.drawImage(field, 0, 0, MAP_WIDTH, MAP_HEIGHT, this);
			g.fillRect(MAP_WIDTH, 0, 5, 720);
			if(losAngeles)
				drawCity(LosAngeles, g);
			else if(dallas)
				drawCity(Dallas, g);
			else if(columbus)
				drawCity(Columbus, g);
			else if(newYorkCity)
				drawCity(NewYorkCity, g);
			else if(chicago)
				drawCity(Chicago, g);
			else if(philadelphia)
				drawCity(Philadelphia, g);
			else if(albany)
				drawCity(Albany, g);
			
			g.setColor(Color.black);
			g.drawString("City: " + currentCity, MAP_WIDTH + 30, 55);
			g.drawString("Year: " + year, MAP_WIDTH + 30, 85);
			g.drawString(months[monthNum-1] + day, MAP_WIDTH + 30, 100);
			
			g.drawString("City Value:  $" + currentCity.getCityValue(), MAP_WIDTH + 30, 150);
			g.drawString("City Funds: $" + currentCity.getCityFunds(), MAP_WIDTH + 30, 165);
			
			g.drawString("Population: " + currentCity.getPopulation(), MAP_WIDTH + 30, 195);
			g.drawString("Unemployed: " + currentCity.getUnemployed(), MAP_WIDTH + 30, 210);
			//~ g.drawString("Unemployment: " + currentCity.getUnemploymentRate() + "%", MAP_WIDTH + 30, 210);
			g.drawString("Farm Workers: " + currentCity.getFarmWorkerTotal(), MAP_WIDTH + 30, 240);
			g.drawString("Factory Workers: " + currentCity.getFactoryWorkerTotal(), MAP_WIDTH + 30, 255);
			g.drawString("Store Workers: " + currentCity.getStoreWorkerTotal(), MAP_WIDTH + 30, 270);
			g.drawString("Total Workers: " + currentCity.getWorkerTotal(), MAP_WIDTH + 30, 300);
			
			g.drawString("Resources: " + currentCity.getResources(), MAP_WIDTH + 30, 330);
			g.drawString("Goods: " + currentCity.getGoods(), MAP_WIDTH + 30, 345);
			
			g.drawString(String.format("Price of Goods: $%.2f" , goodsPrice), MAP_WIDTH + 30, 375);
			
		}
		
		public void drawCity(City city, Graphics g)
		{
			currentCity = city;
			farmL = city.getFarmPlot();
			houseL = city.getHousePlot();
			factoryL = city.getFactoryPlot();
			storeL = city.getStorePlot();
			for(int i = 0; i < farmL.length; i++)
			{
				for(int j = 0; j < farmL[i].length; j++)
				{
					g.setColor(Color.black);
					g.fillRect(70 + j*120, 205 + i*125, 115, 115);
					g.drawImage(farmTile, 72 + j*120, 207 + i*125, 111, 111, this);
					if(farmL[i][j] != 0)
					{
						switch(farmL[i][j])
						{
							case 1: g.drawImage(farm, 72 + j*120, 207 + i*125, 111, 111, this);
								break;
							case 2: g.drawImage(farm2, 72 + j*120, 207 + i*125, 111, 111, this);
								break;
						}
					}
				}
			}
			
			for (int i = 0; i < houseL.length; i++)
			{
				for(int j = 0; j < houseL[i].length; j++)
				{
					g.setColor(Color.black);
					g.fillRect(MAP_WIDTH - (100+120+115) - j*120, 460 + i*120, 115, 115);
					g.drawImage(houseTile, MAP_WIDTH - (98+120+115) - j*120, 462 + i*120, 111, 111, this);
					if(houseL[i][j] != 0)
					{
						switch(houseL[i][j])
						{
							case 1: g.drawImage(house, MAP_WIDTH - (98+120+115) - j*120, 462 + i*120, 111, 111, this);
								break;
							case 2: g.drawImage(house2, MAP_WIDTH - (98+120+115) - j*120, 462 + i*120, 111, 111, this);
								break;
						}
					}
				}
			}
			
			for (int i = 0; i < factoryL.length; i++)
			{
				for(int j = 0; j < factoryL[i].length; j++)
				{
					g.setColor(Color.black);
					g.fillRect(MAP_WIDTH - (100+120+115) - j*120, 205 + i*120, 115, 115);
					g.drawImage(factoryTile, MAP_WIDTH - (98+120+115) - j*120, 207 + i*120, 111, 111, this);
					if(factoryL[i][j] != 0)
					{
						switch(factoryL[i][j])
						{
							case 1: g.drawImage(factory, MAP_WIDTH - (98+120+115) - j*120, 207 + i*120, 111, 111, this);
								break;
							case 2: g.drawImage(factory2, MAP_WIDTH - (98+120+115) - j*120, 207 + i*120, 111, 111, this);
								break;
						}
					}
				}
			}
			
			for(int i = 0; i < storeL.length; i++)
			{
				g.setColor(Color.black);
				g.fillRect(MAP_WIDTH - (70+115), 205 + i*125, 115, 115);
				g.drawImage(storeTile, MAP_WIDTH - (68+115), 207 + i*125, 111, 111, this);
				if(storeL[i] != 0)
				{
					switch(storeL[i])
					{
						case 1: g.drawImage(store, MAP_WIDTH - (68+115), 207 + i*125, 111, 111, this);
							break;
						case 2: g.drawImage(store2, MAP_WIDTH - (68+115), 207 + i*125, 111, 111, this);
							break;
					}
				}
			}
			g.setColor(Color.black);
			if(currentCity.getRequiredReserves() < (int)(currentCity.getDemandDeposits() * rr))
				g.setColor(Color.red);
			g.fillRect(MAP_WIDTH - (70 + 115), 80, 115, 115);
			g.drawImage(bank, MAP_WIDTH - (68 + 115), 82, 111, 111, this);
		}
		
		public void managePlot(boolean b, boolean bought, int xIndex, int yIndex, String pName, int w)
		{
			pName = pName.toLowerCase();
			buying = b;
			if(pName.equals("farm"))
			{
				if(bought)
				{
					if(farmL[yIndex][xIndex] == 1)
					{
						currentCity.changeFarm2Count(1);
						currentCity.changeCityFunds(-farm2Cost);
					}
					else
					{
						currentCity.changeFarmCount(1);
						currentCity.changeCityFunds(-farmCost);
					}
					currentCity.changeFarmPlotElement(1, xIndex, yIndex);
					
				}
				currentCity.changeFarmWorkerCount(w);
			}
			if(pName.equals("neighborhood"))
			{
				if(bought)
				{
					if(houseL[yIndex][xIndex] == 1)
					{
						currentCity.changeHouse2Count(1);
						currentCity.changeCityFunds(-house2Cost);
					}
					else
					{
						currentCity.changeHouseCount(1);
						currentCity.changeCityFunds(-houseCost);
					}
					currentCity.changeHousePlotElement(1, xIndex, yIndex);
				}
			}
			if(pName.equals("factory"))
			{
				if(bought)
				{
					if(factoryL[yIndex][xIndex] == 1)
					{
						shiftAS += 10;
						shiftLRAS += 10;
						currentCity.changeFactory2Count(1);
						currentCity.changeCityFunds(-factory2Cost);
					}
					else
					{
						shiftAS += 10;
						shiftLRAS += 10;
						currentCity.changeFactoryCount(1);
						currentCity.changeCityFunds(-factoryCost);
					}
					currentCity.changeFactoryPlotElement(1, xIndex, yIndex);
				}
				currentCity.changeFactoryWorkerCount(w);
			}
			if(pName.equals("store"))
			{
				if(bought)
				{
					if(storeL[yIndex] == 1)
					{
						permShiftAD += 10;
						shiftAD += 10;
						currentCity.changeStore2Count(1);
						currentCity.changeCityFunds(-store2Cost);
					}
					else
					{
						permShiftAD += 10;
						shiftAD += 10;
						currentCity.changeStoreCount(1);
						currentCity.changeCityFunds(-storeCost);
					}
					currentCity.changeStorePlotElement(1, yIndex);
				}
				currentCity.changeStoreWorkerCount(w);
			}
		}
		public void mouseClicked(MouseEvent e)
		{
			int y = e.getY();
			int x = e.getX();
			int[][] blank = new int[0][0];
			double farmX = (double)(x-70)/120;
			double farmY = (double)(y-205)/125;
			double houseX = (double)((MAP_WIDTH - (100+120)) - x)/120;
			double houseY = (double)(y - 455)/120;
			double factoryX = (double)((MAP_WIDTH - (100+120)) - x)/120;
			double factoryY = (double)(y-205)/120;
			double storeX = (double)((MAP_WIDTH - 70) - x)/120;
			double storeY = (double)(y-205)/125;
			String name = "";
			Image img = null;
			
			if(!manageP && farmX > 0 && farmX < farmL[0].length && farmX - (int)farmX < 0.96 && farmY > 0 && farmY < farmL.length && farmY - (int)farmY < 0.928)
			{
				manageP = true;
				if(farmL[(int)farmY][(int)farmX] == 0)
				{
					buying = true;
					pPanel.setValues("farm", farmCost, farm, (int)farmX, (int)farmY, buying, blank, currentCity.getWorkerTotal());
				}
				else
				{
					switch(farmL[(int)farmY][(int)farmX])
					{
						case 1: name = "Farm"; img = farm; break;
						case 2: name = "Plantation"; img = farm2; break;
					}
					pPanel.setValues(name, farmCost, img, (int)farmX, (int)farmY, buying, currentCity.getFarmWorkers(), currentCity.getWorkerTotal());
				}
			}
			
			if(!manageP && houseX > 0 && houseX < houseL[0].length && houseX - (int)houseX < 0.96 && houseY > 0 && houseY < houseL.length && houseY - (int)houseY < 0.96)
			{
				manageP = true;
				if(houseL[(int)houseY][(int)houseX] == 0)
				{
					buying = true;
					pPanel.setValues("neighborhood", houseCost, house, (int)houseX, (int)houseY, buying, blank, currentCity.getWorkerTotal());
				}
				else
				{
					switch(houseL[(int)houseY][(int)houseX])
					{
						case 1: name = "Neighborhood"; img = house; break;
						case 2: name = "City Block"; img = house2; break;
					}
					pPanel.setValues(name, houseCost, img, (int)houseX, (int)houseY, buying, blank, currentCity.getWorkerTotal());
				}
			}
			
			if(!manageP && factoryX > 0 && factoryX < factoryL[0].length && factoryX - (int)factoryX < 0.96 && factoryY > 0 && factoryY < factoryL.length && factoryY - (int)factoryY < 0.96)
			{
				manageP = true;
				if(factoryL[(int)factoryY][(int)factoryX] == 0)
				{
					buying = true;
					pPanel.setValues("factory", factoryCost, factory, (int)factoryX, (int)factoryY, buying, blank, currentCity.getWorkerTotal());
				}
				else
				{
					switch(factoryL[(int)factoryY][(int)factoryX])
					{
						case 1: img = factory; break;
						case 2: img = factory2; break;
					}
					pPanel.setValues("Factory", factoryCost, img, (int)factoryX, (int)factoryY, buying, currentCity.getFactoryWorkers(), currentCity.getWorkerTotal());
				}
			}
			if(!manageP && storeX > 0 && storeX < 1 && storeX - (int)storeX < 0.96 && storeY > 0 && storeY < storeL.length && storeY - (int)storeY < 0.928)
			{
				manageP = true;
				if(storeL[(int)storeY] == 0)
				{
					buying = true;
					pPanel.setValues("store", storeCost, store, 0, (int)storeY, buying, blank, currentCity.getWorkerTotal());
				}
				else
				{
					switch(storeL[(int)storeY])
					{
						case 1: name = "Store"; img = store; break;
						case 2: name = "Warehouse"; img = store2; break;
					}
					pPanel.setValues(name, storeCost, img, 0, (int)storeY, buying, currentCity.getStoreWorkers(), currentCity.getWorkerTotal());
				}
			}
			if(!manageP && x >= 995 && x <= 1110 && y >= 80 && y <= 195)
			{
				manageP = true;
				pPanel.setBankValues("Bank");
			}
			if(manageP)
			{
				pPanel.setVisible(true);
				mapReturn.setEnabled(false);
				this.repaint();
			}
		}
		
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if("Return to Map".equals(command))
			{
				losAngeles = dallas = columbus = newYorkCity = chicago = philadelphia = albany = false;
				cards.show(c, "GamePanel");
			}
			else
			{
				for (int i = 0; i < cities.size(); i++)
				{
					int cValue = cities.get(i).getCityValue();
					int houseT = cities.get(i).getHouseTotal();
					int house2T = cities.get(i).getHouse2Total();
					int storeT = cities.get(i).getStoreTotal();
					int farmT = cities.get(i).getFarmTotal();
					int factoryT = cities.get(i).getFactoryTotal();
					int pop = cities.get(i).getPopulation();
					int farmW = cities.get(i).getFarmWorkerTotal();
					int factoryW = cities.get(i).getFactoryWorkerTotal();
					int storeW = cities.get(i).getStoreWorkerTotal();
					int[][] farmL2 = cities.get(i).getFarmPlot();
					int[][] factoryL2 = cities.get(i).getFactoryPlot();
					int[] storeL2 = cities.get(i).getStorePlot();
					
					cities.get(i).changePopulation(cValue/50000*(houseT + house2T));
					cities.get(i).calcCityMaintenanceValue(farmCost, farm2Cost, factoryCost, factory2Cost, houseCost, house2Cost, storeCost, store2Cost);
					for(int j = 0; j < storeL2.length; j++)
					{
						if(storeL2[j] != 0)
						{
							int goodsSold = (int)(cities.get(i).getStoreWorkers()[j][0] * pop/10 * (1 - dr) * crowdingOutEffect);
							if(goodsSold > cities.get(i).getGoods())
								goodsSold = cities.get(i).getGoods();
							cities.get(i).changeCityFunds((int)(goodsSold * goodsPrice));
							cities.get(i).changeGoods(-goodsSold);
						}
					}
					
					if(monthEnd)
					{
						
						cities.get(i).changeCityFunds(-cities.get(i).getCityMaintenance());
						for (int j = 0; j < farmL2.length; j++)
						{
							for(int k = 0; k < farmL2[j].length; k++)
							{
								if(farmL2[j][k] != 0)
								{
									cities.get(i).changeResources(cities.get(i).getFarmWorkers()[j][k] * farmL2[j][k] * 100);
								}
							}
						}
					
						for (int j = 0; j < factoryL2.length; j++)
						{
							for(int k = 0; k < factoryL2[j].length; k++)
							{
								if(factoryL2[j][k] != 0)
								{
									if(supplySideUsed)
										supplySideFactor = 2;
									else
										supplySideFactor = 1;
									int goodsMade = (int)(cities.get(i).getFactoryWorkers()[j][k] * factoryL2[j][k] * 10 * supplySideFactor * crowdingOutEffect);
									if(goodsMade * 5 > cities.get(i).getResources())
										goodsMade = cities.get(i).getResources();
									cities.get(i).changeGoods(goodsMade);
									cities.get(i).changeResources(-goodsMade * 5);
								}
							}
						}
					}
				}
			}
		}
		class PlotPanel extends JPanel implements ActionListener
		{
			private String plotName;
			private int cost, xIndex, yIndex, errorCounter, workerCount, totalWorkers, moneyAmount;
			private JButton yes, no, upgrade, hire, fire, withdraw, deposit, exitButton;
			private JTextField input;
			private Image picture;
			private boolean noMoney, buying, bankOpen, invalidInput;
			private Timer timer;
			private int[][] workers;
			
			public PlotPanel()
			{
				this.setLayout(null);
				this.setBackground(Color.white);
				yes = new JButton("Yes");
				yes.setBounds(80,160,60,20);
				yes.addActionListener(this);
				yes.setVisible(false);
				this.add(yes);
				no = new JButton("No");
				no.setBounds(160,160,60,20);
				no.addActionListener(this);
				no.setVisible(false);
				this.add(no);
				upgrade = new JButton("Upgrade");
				upgrade.setBounds(25,160,100,20);
				upgrade.addActionListener(this);
				upgrade.setVisible(false);
				this.add(upgrade);
				hire = new JButton("Hire");
				hire.setBounds(145,125,60,20);
				hire.addActionListener(this);
				hire.setVisible(false);
				this.add(hire);
				fire = new JButton("Fire");
				fire.setBounds(215,125,60,20);
				fire.addActionListener(this);
				fire.setVisible(false);
				this.add(fire);
				deposit = new JButton("Deposit");
				deposit.setBounds(120,125,80,20);
				deposit.addActionListener(this);
				deposit.setVisible(false);
				this.add(deposit);
				withdraw = new JButton("Withdraw");
				withdraw.setBounds(200,125,90,20);
				withdraw.addActionListener(this);
				withdraw.setVisible(false);
				this.add(withdraw);
				exitButton = new JButton("x");
				exitButton.setBounds(245,5,50,20);
				exitButton.addActionListener(this);
				this.add(exitButton);
				
				input = new JTextField("0", 9);
				input.setBounds(175, 100, 70, 20);
				input.addActionListener(this);
				input.setVisible(false);
				input.setHorizontalAlignment(JTextField.RIGHT);
				this.add(input);
				
				timer = new Timer(500, this);
				plotName = "";
				cost = 0;
				picture = null;
				noMoney = buying = invalidInput = bankOpen = false;
				errorCounter = 0;
				workerCount = 0;
				totalWorkers = 0;
				moneyAmount = 0;
			}
			
			public void reset()
			{
				plotName = "";
				cost = 0;
				picture = null;
				noMoney = buying = invalidInput = bankOpen = false;
				errorCounter = 0;
				workerCount = 0;
				totalWorkers = 0;
				moneyAmount = 0;
			}
			public void startTimer()
			{
				timer.start();
			}
			public void stopTimer()
			{
				timer.stop();
			}
			public void setValues(String n, int c, Image i, int plotX, int plotY, boolean b, int[][] w, int wT)
			{
				plotName = n;
				cost = c;
				picture = i;
				xIndex = plotX;
				yIndex = plotY;
				buying = b;
				workers = w;
				totalWorkers = wT;
				bankOpen = false;
				deposit.setVisible(false);
				withdraw.setVisible(false);
				if(buying)
				{
					yes.setVisible(true);
					no.setVisible(true);
					upgrade.setVisible(false);
					hire.setVisible(false);
					fire.setVisible(false);
					input.setVisible(false);
					exitButton.setVisible(false);
				}
				else
				{
					input.setBounds(175, 100, 70, 20);
					yes.setVisible(false);
					no.setVisible(false);
					upgrade.setVisible(true);
					hire.setVisible(true);
					fire.setVisible(true);
					input.setVisible(true);
					exitButton.setVisible(true);
					input.setText("0");
					if(n.equals("Store") || n.equals("Warehouse"))
					{
						switch(storeL[plotY])
						{
							case 1: cost = store2Cost; break;
							case 2: cost = 0; upgrade.setVisible(false); break;
						}
					}
					if(n.equals("Neighborhood") || n.equals("City Block"))
					{
						switch(houseL[plotY][plotX])
						{
							case 1: cost = house2Cost; break;
							case 2: cost = 0; upgrade.setVisible(false); break;
						}
					}
					if(n.equals("Factory"))
					{
						switch(factoryL[plotY][plotX])
						{
							case 1: cost = factory2Cost; break;
							case 2: cost = 0; upgrade.setVisible(false); break;
						}
					}
					if(n.equals("Farm") || n.equals("Plantation"))
					{
						switch(farmL[plotY][plotX])
						{
							case 1: cost = farm2Cost; break;
							case 2: cost = 0; upgrade.setVisible(false); break;
						}
					}
				}
			}
			public void setBankValues(String n)
			{
				input.setText("0");
				input.setBounds(170, 100, 70, 20);
				exitButton.setVisible(true);
				input.setVisible(true);
				withdraw.setVisible(true);
				deposit.setVisible(true);
				yes.setVisible(false);
				no.setVisible(false);
				hire.setVisible(false);
				fire.setVisible(false);
				upgrade.setVisible(false);
				plotName = n;
				bankOpen = true;
			}
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.black);
				g.fillRect(0,0, 300, 200);
				g.setColor(Color.white);
				g.fillRect(5,5, 290, 190);
				g.setColor(Color.black);
				if(buying)
				{
					g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
					g.drawString("Build a " + plotName + "?", 80,30);
					g.drawImage(picture, 105, 40, 90, 90, this);
					g.drawString("Cost: $" + cost, 80,150);
					
				}
				else if(bankOpen)
				{
					g.drawString("Demand Deposits: $" + currentCity.getDemandDeposits(), 120, 55);
					g.drawString("Required Reserves: $" + currentCity.getRequiredReserves(), 120, 70);
					g.drawString("Excess Reserves: $" + currentCity.getExcessReserves(), 120, 85);
					g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
					g.drawString(plotName, 20, 30);
					g.drawImage(bank, 20, 55, 90, 90, this);
					if(invalidInput)
					{
						g.setColor(Color.red);
						g.drawString("Invalid Input!", 145, 180);
					}
				}
				else
				{
					if(plotName.equals("Neighborhood") || plotName.equals("City Block"))
					{
						upgrade.setBounds(100,160,100,20);
						hire.setVisible(false);
						fire.setVisible(false);
						input.setVisible(false);
						g.drawString("Cost: $" + cost, 100, 155);
						g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
						g.drawString(plotName, 30, 30);
						g.drawImage(picture, 105, 45, 90, 90, this);
					}
					else
					{
						upgrade.setBounds(25,160,100,20);
						g.drawString("Workers: " + workers[yIndex][xIndex], 145, 65);
						g.drawString("Cost: $" + cost, 25, 155);
						g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
						g.drawString(plotName, 30, 30);
						g.drawImage(picture, 30, 45, 90, 90, this);
						if(invalidInput)
						{
							g.setColor(Color.red);
							g.drawString("Invalid Input!", 145, 180);
						}
					}
				}
				if(noMoney)
				{
					g.setColor(Color.black);
					g.fillRect(75, 65, 150, 25);
					g.setColor(Color.red);
					g.drawString("Not enough funds!", 80, 85);
				}
			}
			public void actionPerformed(ActionEvent e)
			{
				String command = e.getActionCommand();
				if(buying)
				{
					if("Yes".equals(command))
					{
						if(currentCity.getCityFunds() <= cost)
						{
							errorCounter = 0;
							noMoney = true;
							this.repaint();
						}
						else
						{
							cPanel.managePlot(false, true, xIndex, yIndex, plotName, 0);
							manageP = false;
							this.setVisible(false);
							cPanel.repaint();
						}
					}
					if("No".equals(command))
					{
						cPanel.managePlot(false, false, xIndex, yIndex, plotName, 0);
						manageP = false;
						buying = false;
						this.setVisible(false);
						cPanel.repaint();
					}
				}
				else
				{
					if("x".equals(command))
					{
						cPanel.managePlot(false, false, xIndex, yIndex, plotName, 0);
						this.setVisible(false);
						bankOpen = false;
						manageP = false;
						cPanel.repaint();
					}
					if(bankOpen)
					{
						if("Deposit".equals(command))
						{
							if(input.getText().length() > 9 || !input.getText().matches("\\d+"))
							{
								invalidInput = true;
								errorCounter = 0;
								this.repaint();
							}
							else
							{
								moneyAmount = Integer.parseInt(input.getText());
								if(moneyAmount > currentCity.getCityFunds())
								{
									errorCounter = 0;
									invalidInput = true;
									this.repaint();
								}
								else
								{
									input.setText("0");
									currentCity.changeCityFunds(-moneyAmount);
									currentCity.changeDemandDeposits(moneyAmount);
									int difference = 0;
									int rReserves = (int)(currentCity.getDemandDeposits() * rr);
									if(currentCity.getRequiredReserves() < rReserves)
									{
										difference = rReserves - currentCity.getRequiredReserves();
										if(difference > moneyAmount)
										{
											currentCity.changeRequiredReserves(moneyAmount);
										}
										else
										{
											currentCity.changeRequiredReserves(difference);
											moneyAmount -= difference;
											currentCity.changeExcessReserves(moneyAmount);
										}
									}
									else
									{
										currentCity.changeRequiredReserves((int)(moneyAmount*rr));
										currentCity.changeExcessReserves((int)(moneyAmount*(1-rr)));
									}
									cPanel.repaint();
									this.repaint();
								}
							}
						}
						if("Withdraw".equals(command))
						{
							if(input.getText().length() > 9 || !input.getText().matches("\\d+"))
							{
								invalidInput = true;
								errorCounter = 0;
								this.repaint();
							}
							else
							{
								moneyAmount = Integer.parseInt(input.getText());
								if(moneyAmount > currentCity.getRequiredReserves() + currentCity.getExcessReserves())
								{
									errorCounter = 0;
									invalidInput = true;
									this.repaint();
								}
								else
								{
									input.setText("0");
									currentCity.changeCityFunds(moneyAmount);
									currentCity.changeDemandDeposits(-moneyAmount);
									int difference = 0;
									int rReserves = (int)(currentCity.getDemandDeposits() * rr);
									difference = currentCity.getRequiredReserves() - rReserves;
									
									if(moneyAmount - difference > currentCity.getExcessReserves())
									{
										moneyAmount -= currentCity.getExcessReserves();
										currentCity.changeExcessReserves(-currentCity.getExcessReserves());
										currentCity.changeRequiredReserves(-moneyAmount);
									}
									else
									{
										moneyAmount -= difference;
										currentCity.changeRequiredReserves(-difference);
										currentCity.changeExcessReserves(-moneyAmount);
									}
									cPanel.repaint();
									this.repaint();
								}
							}
						}
					}
					else
					{
						if("Upgrade".equals(command))
						{
							if(currentCity.getCityFunds() <= cost)
							{
								errorCounter = 0;
								noMoney = true;
								this.repaint();
							}
							else
							{
								cPanel.managePlot(false, true, xIndex, yIndex, plotName, 0);
								manageP = false;
								this.setVisible(false);
								cPanel.repaint();
							}
						}
						if("Hire".equals(command))
						{
							
							if(input.getText().length() > 9 || !input.getText().matches("\\d+"))
							{
								invalidInput = true;
								errorCounter = 0;
								this.repaint();
							}
							else
							{
								workerCount = Integer.parseInt(input.getText());
								if(workerCount > currentCity.getPopulation() - totalWorkers)
								{
									errorCounter = 0;
									invalidInput = true;
									this.repaint();
								}
								else
								{
									input.setText("0");
									workers[yIndex][xIndex] += workerCount;
									cPanel.managePlot(false, false, xIndex, yIndex, plotName, workerCount);
									cPanel.repaint();
									this.repaint();
								}
							}
						}
						else if("Fire".equals(command))
						{
							
							if(input.getText().length() > 9 || !input.getText().matches("\\d+"))
							{
								invalidInput = true;
								errorCounter = 0;
								this.repaint();
							}
							else
							{
								workerCount = Integer.parseInt(input.getText());
								if(workerCount > workers[yIndex][xIndex])
								{
									errorCounter = 0;
									invalidInput = true;
									this.repaint();
								}
								else
								{
									input.setText("0");
									workers[yIndex][xIndex] -= workerCount;
									cPanel.managePlot(false, false, xIndex, yIndex, plotName, -workerCount);
									cPanel.repaint();
									this.repaint();
								}
							}
						}
					}
				}
				errorCounter++;
				if(errorCounter == 4)
				{
					noMoney = false;
					invalidInput = false;
					this.repaint();
				}
			}
		}
	}
	class EndPanel extends JPanel implements ActionListener
	{
		private JButton menuReturn;
		
		public EndPanel()
		{
			this.setLayout(null);
			this.setBackground(Color.white);
			
			menuReturn = new JButton("Return to Main Menu");
			menuReturn.setBounds(600,600,200,50);
			menuReturn.addActionListener(this);
			this.add(menuReturn);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int green = 0;
			int blue = 255;
			for(int i = 0; i < 255; i++)
			{
				//~ g.setColor(new Color(green, green, blue));
				g.setColor(new Color(0, green, blue));
				g.drawRect(0+i, 0+i, 1380 - i*2, 720 - i*2);
				if(green == 255)
					blue--;
				if(green < 255)
					green++;
			}
			for(int i = 255; i < 361; i++)
			{
				//~ g.setColor(new Color(green, green, blue));
				g.setColor(new Color(0, green, blue));
				g.drawRect(0+i, 0+i, 1380 - i*2, 720 - i*2);
				green--;
			}
			g.setColor(Color.black);
			g.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50));
			g.drawString("Game Over!", 525,100);
			g.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 25));
			String message = "";
			if(citiesBankrupt)
			{
				message = "Oh no! All of your cities went bankrupt!";
			}
			else if(negativeGovtFunds)
			{
				message = "You ran your country into a deficit over the course of your term. Shame on you!";
			}
			else if(rrFailure)
			{
				message = "One (or more) of your banks did not meet the reserve requirement at the end of the year.";
			}
			else
			{
				message = "Congratulations! You successfully completed your term without ruining the country.";
			}
			g.drawString(message, 100, 150);
			
		}
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			
			if(command.equals("Return to Main Menu"))
			{
				cards.show(c, "MenuPanel");
				gameStarted = false;
				gPanel.reset();
				cPanel.reset();
			}
		}
	}
}
