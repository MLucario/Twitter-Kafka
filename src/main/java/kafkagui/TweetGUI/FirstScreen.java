package kafkagui.TweetGUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FirstScreen {

	private JFrame frame;

	public JTextField getTextConsumerKey() {
		return textConsumerKey;
	}

	public void setTextConsumerKey(JTextField textConsumerKey) {
		this.textConsumerKey = textConsumerKey;
	}

	public JTextField getTextConsumerSecret() {
		return textConsumerSecret;
	}

	public void setTextConsumerSecret(JTextField textConsumerSecret) {
		this.textConsumerSecret = textConsumerSecret;
	}

	public JTextField getTextToken() {
		return textToken;
	}

	public void setTextToken(JTextField textToken) {
		this.textToken = textToken;
	}

	public JTextField getTextSecret() {
		return textSecret;
	}

	public void setTextSecret(JTextField textSecret) {
		this.textSecret = textSecret;
	}

	private JTextField textConsumerKey;
	private JTextField textConsumerSecret;
	private JTextField textToken;
	private JTextField textSecret;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstScreen window = new FirstScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FirstScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String consumerKey = textConsumerKey.getText();
				String consumerSecret = textConsumerSecret.getText();
				String token = textToken.getText();
				String secret = textSecret.getText();
				//MyProducer producer = new MyProducer(consumerKey, consumerSecret, token, secret);
				//producer.run1();

				Second ob = new Second();
				ob.run();

			}
		});
		btnSubmit.setBounds(100, 229, 80, 23);
		frame.getContentPane().add(btnSubmit);

		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(267, 229, 80, 23);
		frame.getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Application is Closing!!");
				System.exit(0);

			}
		});

		textConsumerKey = new JTextField();
		textConsumerKey.setText("IiCSqSuOMTXPxPnGLKADD1XX3");
		textConsumerKey.setBounds(110, 43, 275, 20);
		frame.getContentPane().add(textConsumerKey);
		textConsumerKey.setColumns(10);

		JLabel lblNewLabel = new JLabel("consumerKey");
		lblNewLabel.setBounds(20, 46, 80, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("consumerSecret");
		lblNewLabel_1.setBounds(20, 85, 80, 20);
		frame.getContentPane().add(lblNewLabel_1);

		textConsumerSecret = new JTextField();
		textConsumerSecret.setText("NOIjrwCFC1qKuNDXv8FNLJJMnQTLBwvnGeTaiSJlSE9LmqTtAV");
		textConsumerSecret.setBounds(110, 85, 275, 20);
		frame.getContentPane().add(textConsumerSecret);
		textConsumerSecret.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("token");
		lblNewLabel_2.setBounds(20, 130, 49, 14);
		frame.getContentPane().add(lblNewLabel_2);

		textToken = new JTextField();
		textToken.setText("1042911030643326976-LEel2Go2h9Ad9ikucmfATXcpHluWvu");
		textToken.setBounds(110, 127, 275, 20);
		frame.getContentPane().add(textToken);
		textToken.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("secret");
		lblNewLabel_3.setBounds(20, 178, 49, 14);
		frame.getContentPane().add(lblNewLabel_3);

		textSecret = new JTextField();
		textSecret.setText("rzZADaxFztjD6UehRyUoQ2FYr9NbAImuvCVciDlKFgRbK");
		textSecret.setBounds(110, 175, 275, 20);
		frame.getContentPane().add(textSecret);
		textSecret.setColumns(10);
	}

}
