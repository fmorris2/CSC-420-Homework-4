import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class FlagFrame extends JFrame
{
	private static final long serialVersionUID = 8755370338653071604L;
	
	private JPanel contentPane;
	private JProgressBar progressBar;
	private JList<Flag> countryList;
	private DefaultListModel<Flag> countryListModel;
	private JLabel imageLabel;
	private JTextField textField;
	private Map<Flag, ImageIcon> imageCache;
	private Map<String, Flag> flagMap;

	/**
	 * Create the frame.
	 */
	public FlagFrame()
	{
		imageCache = new HashMap<>();
		flagMap = new HashMap<>();
		countryListModel = new DefaultListModel<>();
		setResizable(false);
		setTitle("CSC 420: Homework 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Add splash screen
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		contentPane.add(progressBar);
		
		//Load flags with our separate swing worker
		new FlagLoader(this).execute();
	}
	
	public void displayMainGUI()
	{
		contentPane.setLayout(new MigLayout());
		
		//Remove splash screen
		contentPane.remove(progressBar);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "width min(50%)");
		
		countryList = new JList<Flag>();
		countryList.setDragEnabled(true);
		countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//countryList.addListSelectionListener((ListSelectionEvent e) -> changeImage(e));
		scrollPane.setViewportView(countryList);
		countryList.setModel(countryListModel);
		
		imageLabel = new JLabel("");
		contentPane.add(imageLabel, "wrap, width max(50%), spany 2");
		
		textField = new JTextField("");
		textField.getDocument().addDocumentListener(new TextFieldListener(this));
		contentPane.add(textField, "grow");
		
		setContentPane(contentPane);
	}
	
	public void setProgress(int i)
	{
		progressBar.setValue(i);
		progressBar.setString(i + "%");
	}
	
	public void changeImage(String s)
	{
		Flag f = flagMap.get(s);
		ImageIcon img = imageCache.get(f);
		
		if(f == null)
		{
			System.out.println("No flag found for entry \"" + s + "\"");
			return;
		}
		
		if(img == null)
		{
			System.out.println("Flag ImageIcon for " + f + " not found in cache... creating");
			Image scaledImage = f.getImage().getScaledInstance(getWidth() / 2, 107, Image.SCALE_SMOOTH);
			img = new ImageIcon(scaledImage);
			imageCache.put(f, img);
		}
		
		imageLabel.setIcon(img);
	}
	
	public void addFlag(String n, Flag f)
	{
		flagMap.put(n, f);
		countryListModel.addElement(f);
	}

}
