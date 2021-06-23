package dev.tic.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dev.tic.display.Display;

public class Game implements Runnable{
	
	Thread thread;
	
	public static int arr[][] = new int[3][3];
	
	public volatile boolean running = false;
	
	public Game(String title, int width, int height)
	{
		new Display(title, width, height);
		initarr();
		initButton();
		start();
	}
	public void check()
	{
		
		if(((arr[0][0] == arr[1][1]) && (arr[1][1] == arr[2][2])) || ((arr[0][2] == arr[1][1]) && (arr[1][1] == arr[2][0])))
		{
			if(arr[1][1] == 0) Display.setGameOverLabel("0 Won!");
			else if(arr[1][1] == 1) Display.setGameOverLabel("X Won!");
			
			stop();
		}
		for(int i = 0; i < 3; i++)
		{
				
				if((arr[i][0] == arr[i][1]) && (arr[i][0] == arr[i][2])) 
				{
					if(arr[i][0] == 0) Display.setGameOverLabel("0 Won!");
					else Display.setGameOverLabel("X Won!");
					stop();
				}
				
				else if((arr[0][i] == arr[1][i]) && (arr[0][i] == arr[2][i]))
				{
					if(arr[0][i] == 0) Display.setGameOverLabel("0 Won!");
					else Display.setGameOverLabel("X Won!");
					stop();
				}
				if(isFull()) 
				{
					Display.setGameOverLabel("It's a Draw!");
					stop();
				}
		}
	}
	
	private void initarr()
	{
		int x = 100;
		for(int i = 0 ; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				arr[i][j] = i + j + (x++);
			}
		}
		System.out.println("Array Initilized");
	}
	
	public synchronized void start()
	{
		if(running) return;
		
		System.out.println("Game thread started");
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running) return;
		
		running = false;
		System.out.println("Game thread stopped" + " "+ isFull());
		Display.setButtonsNotEnabled();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(running)
		{
			check();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void refreshGame()
	{
		if(running) stop();
		
		Display.refresh();
		initarr();
		
		thread.stop();
		start();
		
	}
	
	private void initButton()
	{
		Display.playagain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				refreshGame();
				
			}
		});
	}
	private boolean isFull()
	{
		int c = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(arr[i][j] == 0 || arr[i][j] == 1) c++;
			}
		}
		if(c==9) return true;
		else return false;
	}
	
	
}
