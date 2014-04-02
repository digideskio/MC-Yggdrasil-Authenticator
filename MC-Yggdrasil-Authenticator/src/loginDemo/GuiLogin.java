package loginDemo;

import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import authentication.AuthRequest;

/**
 * A GUI demo client interface for authentication. Logs a user in to obtain a token between 
 * the client and the Minecraft authentication server and then allows the user to log out; this invalidates the token.
 * 
 * @author wpenson
 *
 */
public class GuiLogin implements ActionListener, PropertyChangeListener {
	private Task task;
	private JFrame frame;
	
	private JTextField userTextField;
	private JPasswordField passwordField;
	
	private JProgressBar progressBar;
	
	private JButton bLogin;
	private JButton bTryAgain;
	
	private JButton bLogout;
	
	private AuthRequest auth;
	private Boolean authAccepted;
	
	public static void main(String args[]) {
		new GuiLogin();
	}
	
	public GuiLogin() {
	    frame = new JFrame("Minecraft Server Login");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setContentPane(createLoginPane());
	    frame.getRootPane().setDefaultButton(bLogin);
	    frame.pack();
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    frame.setLocation((int) (ge.getCenterPoint().getX() - frame.getWidth()/2), (int) (ge.getCenterPoint().getY() - frame.getHeight()/2));
	    userTextField.requestFocus();
	    frame.setVisible(true);
	}
	
	public JPanel createLoginPane() {		
		JPanel fieldsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel tempLabel;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		tempLabel = new JLabel("Username: ");
		fieldsPanel.add(tempLabel, c);
		
		c.gridx = 1;
		c.gridwidth = 2;
		userTextField = new JTextField(13);
		fieldsPanel.add(userTextField, c);
		
		c.insets = new Insets(0, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		tempLabel = new JLabel("Password: ");
		fieldsPanel.add(tempLabel, c);
		
		c.gridx = 1;
		c.gridwidth = 2;
		passwordField = new JPasswordField(13);
		fieldsPanel.add(passwordField, c);
		
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(0, 10, 10, 10);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		bLogin = new JButton("Login");
		bLogin.addActionListener(this);
		fieldsPanel.add(bLogin, c);
		
		return fieldsPanel;
	}
	
	public JPanel createChatPane() {
		JPanel labelPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel tempLabel;
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		tempLabel = new JLabel("Login Succeeded!");
		labelPanel.add(tempLabel, c);
		
		c.gridy = 1;
		tempLabel = new JLabel("Access Token: " + auth.getUser().getAccessToken());
		labelPanel.add(tempLabel, c);
		
		c.gridy = 2;
		bLogout = new JButton("Logout");
		bLogout.addActionListener(this);
		labelPanel.add(bLogout, c);
		
		return labelPanel;
	}
	
	public void failedLogin() {
		JPanel failedLoginPanel = new JPanel(new GridBagLayout());
        
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		JLabel lFailedLogin = new JLabel("Login Failed");
		failedLoginPanel.add(lFailedLogin, c);
		
		c.insets = new Insets(7, 0, 0, 0);
		c.gridy = 1;
     	bTryAgain = new JButton("Try Again");
     	bTryAgain.addActionListener(this);
     	failedLoginPanel.add(bTryAgain, c);
     	
     	frame.getContentPane().removeAll();
     	frame.add(failedLoginPanel);
     	frame.getRootPane().setDefaultButton(bTryAgain);
        frame.validate();
        frame.repaint();
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == bLogin) {
	        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	        
	        JPanel progressPanel = new JPanel(new GridBagLayout());
	        
	   	 	progressBar = new JProgressBar();
	     	progressBar.setStringPainted(false);
	     	progressBar.setIndeterminate(true);
	     	progressPanel.add(progressBar);
	     	frame.getContentPane().removeAll();
	     	frame.getContentPane().add(progressPanel);
	        frame.validate();
	        frame.repaint();
	        
	        task = new Task();
	        task.addPropertyChangeListener(this);
	        task.execute();
	        
		} else if (evt.getSource() == bTryAgain) {
			frame.getContentPane().removeAll();
	     	frame.getContentPane().add(createLoginPane());
	     	frame.getRootPane().setDefaultButton(bLogin);
	     	userTextField.requestFocus();
	        frame.validate();
	        frame.repaint();
	        
		} else if (evt.getSource() == bLogout) {
			if (auth.signout())
				System.exit(0);
		}
    }
	
	public void propertyChange(PropertyChangeEvent evt) {
		//progressBar.setIndeterminate(true);
	}
	
	class Task extends SwingWorker<Void, Void> {
	    @Override
	    public Void doInBackground() { 
	    	String user = userTextField.getText();
	    	char[] pass = passwordField.getPassword();
	    	auth = new AuthRequest(user, String.valueOf(pass));
	    	
	    	if (auth.authenticate())
	    		authAccepted = true;
	    	else
	    		authAccepted = false;
	    	
	        return null;
	    }

	    @Override
	    public void done() {
	        frame.setCursor(null);
	        
	    	if (authAccepted) {
	    		frame.dispose();
	    		createChatFrame();
	    	} else {
	    		Toolkit.getDefaultToolkit().beep();
	    		failedLogin();
	    	}
	    }
	}
	
	public void createChatFrame() {
        frame = new JFrame("MCTextualClient - Chat Window");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);
	    frame.setContentPane(createChatPane());
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
}
