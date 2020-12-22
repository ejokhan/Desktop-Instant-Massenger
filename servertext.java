
//mother class you will be running this program for setting up server

import javax.swing.JFrame;

public class servertext
{
	public static void main(String[] args)
	{
		server sally= new server();
		sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sally.startRunning();
	}
}
