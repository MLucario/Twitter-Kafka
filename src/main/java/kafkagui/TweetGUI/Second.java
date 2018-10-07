package kafkagui.TweetGUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class Second {

	private JFrame frame;
	private static JTextArea textArea = new JTextArea();;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Second window = new Second();
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
	public Second() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 832, 536);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		String consumerKey = "IiCSqSuOMTXPxPnGLKADD1XX3";
		String consumerSecret = "NOIjrwCFC1qKuNDXv8FNLJJMnQTLBwvnGeTaiSJlSE9LmqTtAV";
		String token = "1042911030643326976-LEel2Go2h9Ad9ikucmfATXcpHluWvu";
		String secret = "rzZADaxFztjD6UehRyUoQ2FYr9NbAImuvCVciDlKFgRbK";
		MyProducer producer = new MyProducer(consumerKey, consumerSecret, token, secret);

		//Quy (Kafka Streaming)
		JButton btnNewButton = new JButton("Streaming");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String aString = producer.run1();
				textArea.setText(aString);

			}
		});
		btnNewButton.setBounds(10, 11, 174, 23);
		frame.getContentPane().add(btnNewButton);

		textArea.setBounds(223, 11, 585, 477);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		frame.getContentPane().add(textArea);

		// API2: Phuc Post a Tweet
		JButton btnApi = new JButton("Post a Tweet");
		btnApi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Status tweet;
				try {

					tweet = producer.getTwitter().updateStatus("Test final!!!");
					textArea.setText("Successfully tweeted ----> " + tweet.getText());
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnApi.setBounds(10, 45, 174, 23);
		frame.getContentPane().add(btnApi);
		// API3: Phuc Get Favorites
		JButton btnNewButton_1 = new JButton("Get Favorites");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String twitterScreenName = null;
				try {
					twitterScreenName = producer.getTwitter().getScreenName();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TwitterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ResponseList<Status> status = null;
				try {
					status = producer.getTwitter().getFavorites(twitterScreenName);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textArea.setText("My Favorites list: " + status.toString());
			}
		});
		btnNewButton_1.setBounds(10, 79, 174, 23);
		frame.getContentPane().add(btnNewButton_1);
		// API4: Phuc "Get following
		JButton btnNewButton_2 = new JButton("Get following");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				long lCursor = -1;
				IDs friendsIDs = null;
				try {
					friendsIDs = producer.getTwitter().getFriendsIDs(producer.getTwitter().getId(), -1);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					System.out.println(producer.getTwitter().showUser(producer.getTwitter().getId()).getName());
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("==========================");
				do {
					for (long i : friendsIDs.getIDs()) {
						//System.out.println("follower ID #" + i);
						try {
							String temp = producer.getTwitter().showUser(i).getName();
							//System.out.println(producer.getTwitter().showUser(i).getName());
							textArea.setText(temp);

						} catch (TwitterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} while (friendsIDs.hasNext());

			}
		});
		btnNewButton_2.setBounds(10, 113, 174, 23);
		frame.getContentPane().add(btnNewButton_2);
		// API5: Kevin Send a message
		JButton btnNewButton_3 = new JButton("Send a message");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User userMinh = null;
				try {
					userMinh = producer.getTwitter().showUser("MinhKNgo0710");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long userId = userMinh.getId();
				try {
					DirectMessage directMessage = producer.getTwitter().sendDirectMessage(userId, "Hello Minh");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(userId);
				textArea.setText("Hello Minh was sent!!");

			}
		});
		btnNewButton_3.setBounds(10, 147, 174, 23);
		frame.getContentPane().add(btnNewButton_3);
		// API6: Kevin View Friend Description
		JButton btnNewButton_4 = new JButton("View Friend Description");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User userMinh = null;
				try {
					userMinh = producer.getTwitter().showUser("MinhKNgo0710");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long userId = userMinh.getId();
				try {
					DirectMessage directMessage = producer.getTwitter().sendDirectMessage(userId, "Hello Minh");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textArea.setText(userMinh.getDescription());
			}
		});
		btnNewButton_4.setBounds(10, 181, 174, 23);
		frame.getContentPane().add(btnNewButton_4);
		// API7: Kevin View Friend Location
		JButton btnNewButton_5 = new JButton("View Friend Location");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User userMinh = null;
				try {
					userMinh = producer.getTwitter().showUser("MinhKNgo0710");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				long userId = userMinh.getId();
				try {
					DirectMessage directMessage = producer.getTwitter().sendDirectMessage(userId, "Hello Minh");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textArea.setText(userMinh.getLocation());
			}
		});
		btnNewButton_5.setBounds(10, 215, 174, 23);
		frame.getContentPane().add(btnNewButton_5);
		//API 8 Quy Get tweets on the time line
		JButton btnNewButton_6 = new JButton("Get tweets on the time line");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Status> status2 = null;
				try {
					status2 = producer.getTwitter().getHomeTimeline();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String result = "";
				for (Status s : status2) {
					result += s.getUser().getScreenName() + "tweeted " + s.getText() + s.getId() + "\n";
				}

				textArea.setText(result);

			}
		});
		btnNewButton_6.setBounds(10, 249, 174, 23);
		frame.getContentPane().add(btnNewButton_6);

	}

	//Quy Nguyen (Kafka Streaming)
	public class MyProducer {
		Logger logger = LoggerFactory.getLogger(MyProducer.class.getName());
		private Twitter twitter;
		private TwitterFactory twitterFactory;
		private String textConsumerKey;
		private String textConsumerSecret;
		private String textToken;
		private String textSecret;
		private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		private KafkaProducer<String, String> producer = createKafkaProducer();
		private List<String> terms = Lists.newArrayList("Java");
		private Client client;

		public MyProducer(String textConsumerKey, String textConsumerSecret, String textToken, String textSecret) {
			this.textConsumerKey = textConsumerKey;
			this.textConsumerSecret = textConsumerSecret;
			this.textToken = textToken;
			this.textSecret = textSecret;
			client = createTwitterClient(msgQueue);
			System.out.println("Created Twitter Client");
			createTwitterClientXXX(msgQueue);

		}

		public Twitter getTwitter() {
			return twitter;
		}

		public MyProducer() {

		}

		public void createTwitterClientXXX(BlockingQueue<String> msgQueue) {

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(textConsumerKey).setOAuthConsumerSecret(textConsumerSecret)
					.setOAuthAccessToken(textToken).setOAuthAccessTokenSecret(textSecret).setJSONStoreEnabled(true)
					.setIncludeEntitiesEnabled(true);

			twitterFactory = new TwitterFactory(cb.build());
			this.twitter = twitterFactory.getInstance();

		}

		private KafkaProducer<String, String> createKafkaProducer() {
			String bootStrapServer = "127.0.0.1:9092";

			Properties properties = new Properties();
			properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
			properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

			// create safe producer
			properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
			properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
			properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
			properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // kakfa 2.0 >=1.1 just keep
																								// 5 to safe

			// Add high throughput producer ( at the expense of aa bit of latency and CPU
			// usage)
			properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
			properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
			properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024)); // 32 KB

			KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

			return producer;
		}

		public Client createTwitterClient(BlockingQueue<String> msgQueue) {

			/**
			 * Declare the host you want to connect to, the endpoint, and authentication
			 * (basic auth or oauth)
			 */
			Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
			StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
			// Optional: set up some followings and track terms

			//hosebirdEndpoint.followings(followings);

			hosebirdEndpoint.trackTerms(terms);

			// These secrets should be read from a config file
			Authentication hosebirdAuth = new OAuth1(textConsumerKey, textConsumerSecret, textToken, textSecret);

			// Creating a client:

			ClientBuilder builder = new ClientBuilder().name("CMPE172-Client-01") // optional: mainly for the logs
					.hosts(hosebirdHosts).authentication(hosebirdAuth).endpoint(hosebirdEndpoint)
					.processor(new StringDelimitedProcessor(msgQueue));
			// .eventMessageQueue(eventQueue); // optional: use this if you want to process
			// client events

			Client hosebirdClient = builder.build();
			// Attempts to establish a connection.
			// hosebirdClient.connect();

			return hosebirdClient;

		}

		public String run1() {
			/**
			 * Set up your blocking queues: Be sure to size these properly based on expected
			 * TPS of your stream
			 */
			String result = "No data";
			BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

			final Client client = createTwitterClient(msgQueue);

			// Attempts to establish a connection.
			client.connect();

			System.out.println("-------------" + client.getStatsTracker().getNumMessages());

			// Kafka Producer
			final KafkaProducer<String, String> producer = createKafkaProducer();

			// add a shutdown hook

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				logger.info("Stopping application....");
				logger.info("Shutting down client from twitter....");
				client.stop();
				logger.info("closing producer....");
				producer.close();

				logger.info("done");

			}));

			int i = 0;
			while (!client.isDone() && i < 20) {
				String msg = null;
				try {
					msg = msgQueue.poll(5, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
					client.stop();
				}

				if (msg != null) {
					logger.info(msg);
					String pattern1 = "text";

					String pattern2 = "source";
					Pattern p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));

					Matcher m = p.matcher(msg);
					while (m.find()) {
						result += m.group(1) + "\n";
					}

					i++;
					producer.send(new ProducerRecord<String, String>("twitter_tweet", null, msg), new Callback() {

						@Override
						public void onCompletion(RecordMetadata metadata, Exception e) {
							if (e != null) {
								logger.error("Something bad happened ", e.getMessage());
							}

						}

					});
				}

			}

			logger.info("End of application");
			return result;
		}
	}
}
