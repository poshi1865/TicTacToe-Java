package dev.tic.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dev.tic.main.Game;

public class Display extends JFrame {
		
	private static final long serialVersionUID = 1L;
	
	private static int width;
	private static int height;
	private String title;
	
	public static JButton b[] = new JButton[9];;
	private static JLabel gameOverLabel;
	public static JButton playagain;
	
	private JPanel gamePanel;
	public ImageIcon zero = new ImageIcon("res/zero.png");
	public ImageIcon cross = new ImageIcon("res/cross.png");
	
	public static int turn = 0;
	
	public Display(String title, int width, int height)
	{
		this.title = title;
		Display.width= width;
		Display.height = height;
		
		initGui();
		addListeners(b);
	}
	
	private void initGui()
	{
		//Base Panel
		JPanel basePanel = new JPanel();
		basePanel.setPreferredSize(new Dimension(width, height));
		basePanel.setBackground(new Color(0x90ee90));
		basePanel.setLayout(null);
		basePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//Game name Label
		String t = "Tic Tac Toe";
		JLabel gamename = new JLabel(t, SwingConstants.CENTER);
		gamename.setFont(new Font("Courier", Font.BOLD, 25));
		gamename.setBounds(width/2 - t.length()*9, 5, 200, 120);
		
		//Game Over Label
		gameOverLabel = new JLabel();
		gameOverLabel.setFont(new Font("Courier", Font.BOLD, 25));
		
		//Play again Button
		playagain = new JButton("New Game");
		playagain.setFont(new Font("Courier", Font.BOLD, 25));
		playagain.setBounds(width/2-100, height-70, width, height);
		playagain.setBackground(new Color(0xf6e4dd));
		playagain.setSize(new Dimension(200,40));
		
		//Main Game Panel for playing
		gamePanel = new JPanel();
		gamePanel.setPreferredSize(new Dimension(width/2, height/2));
		gamePanel.setBackground(Color.black);
		gamePanel.setLayout(new GridLayout(3,3,1,1));
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		gamePanel.setBounds(123,100,width/2, height/2);
		addButtons();
		
		basePanel.add(gamePanel);
		basePanel.add(gamename);
		basePanel.add(gameOverLabel);
		basePanel.add(playagain);
		
		setTitle(title);
		setSize(width, height);
		getContentPane().add(basePanel);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addButtons()
	{
		for(int i = 0; i < 9; i++)
		{
			b[i] = new JButton();
			b[i].setBackground(Color.white);
			gamePanel.add(b[i]);
						
		}
		
	}
	
	public void addListeners(JButton[] bt)
	{
		for(int i = 0; i < 9; i++)
		{
			final int j = i;
			bt[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(turn == 0)
					{
						bt[j].setIcon(zero);		
						bt[j].setEnabled(false);
						Game.arr[giveX(j)][giveY(j)] = turn;
						//System.out.println(giveX(j)+" "+giveY(j));
						turn = 1;
					}
					else if(turn == 1)
					{
						bt[j].setIcon(cross);
						bt[j].setEnabled(false);
						Game.arr[giveX(j)][giveY(j)] = turn;
						//System.out.println(giveX(j)+" "+giveY(j));
						turn = 0;
					}
					
				}
			});
		}
		

	}
	
	public static void refresh()
	{
		for(int i = 0; i < 9; i++)
		{
			b[i].setIcon(null);
			b[i].setEnabled(true);
		}
		System.out.println("Buttons are enabled");
		turn = 0;
		
		setGameOverLabel("");
	}
	
	public static void setGameOverLabel(String s)
	{
		gameOverLabel.setText(s);
		gameOverLabel.setBounds(width/2-s.length()*8, 150, width, height);
		
	}
	
	private static int giveX(int i)
	{
		if(i == 0 || i == 1 || i == 2) return 0;
		else if(i == 3 || i == 4 || i == 5) return 1;
		else if(i == 6 || i == 7|| i == 8) return 2;
		
		else return 0;
	}
	
	private static int giveY(int i)
	{
		if(i == 0 || i == 3 || i == 6) return 0;
		else if(i == 1 || i == 4 || i == 7) return 1;
		else if(i == 2 || i == 5 || i == 8) return 2;
		
		else return 0;
	}

	public static void setButtonsNotEnabled() {
		for(int i = 0; i < 9; i++)
		{
			b[i].setEnabled(false);
		}
	}		
}
