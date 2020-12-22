
//mother class you will be running this program for setting up client

package projectclient;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class ClientTest 
{
	public static void main(String[] args)
	{
		String fn = JOptionPane.showInputDialog("Enter ip you want to connect");
		Client charlie;
		charlie = new Client(fn);
		charlie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		charlie.startRunning();
		
	}
}
