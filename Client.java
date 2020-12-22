////child class run, clienttest.java for setting up client 

package projectclient;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame 
{
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP ;
	private Socket connection;
	
	// constructor
	public Client (String host)
	{
		super("Munir!");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane (chatWindow), BorderLayout.CENTER);
		setSize(300,150);
		setVisible(true);
	}
	
	//connect to server
	public void startRunning()
	{
		try
		{
			connectToServer();
			setupStreams();
			whileChatting();
		}
		catch(EOFException eofExeption)
		{
			showMessage("\n Client Terminated connection");
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
		finally
		{
			closeCrap();
		}
	}
	
	//connecting to server
	private void connectToServer()throws IOException
	{
		showMessage("Attemting to connect.... \n");
		connection = new Socket(InetAddress.getByName(serverIP),6789);
		showMessage("connected to:" + connection.getInetAddress().getHostName() );           
	}
	
	//seting up streams for commmunication 
	private void setupStreams() throws IOException 
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n streams are good to go! \n");
	}
	
	// while chatting method 
	private void whileChatting() throws IOException
	{
		ableToType(true);
		do
		{
			try
			{
				message = (String) input.readObject();
				showMessage("\n" + message);
			} 
			catch (ClassNotFoundException classNotFoundExecption)
			{
				showMessage("\n i don't know that object type");
			}
		}
		while(!message.equals("SERVER - END"));
	}
	
	//close the socket
	private void closeCrap()
	{
		showMessage("\n closing crap down...");
		ableToType(false);
		try
		{
			output.close();
			input.close();
			connection.close();
			
		}
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	//sending message to server 
	private void sendMessage(String message)
	{
		try
		{
			output.writeObject("CLIENT- " + message);
			output.flush();
			showMessage("\n client -" + message);
		} 
		catch(IOException ioException)
		{
			chatWindow.append("\n something messed up while sending");
		}
	}
	//show message in GUI 
	private void showMessage(final String m)
	{
		SwingUtilities.invokeLater
		(
				new Runnable()
				{
					public void run()
					{
						chatWindow.append(m);
					} 
				}
		);
	}
	//permission of typing 
	private void ableToType(final boolean tof)
	{
		SwingUtilities.invokeLater
		(
				new Runnable()
				{
					public void run()
					{
						userText.setEditable(tof);
					} 
				}
		);
	}
}

