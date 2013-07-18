package edu.drexel.psal.anonymouth.gooie;

import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.drexel.psal.jstylo.generics.Logger;
import edu.drexel.psal.jstylo.generics.ProblemSet;

import net.miginfocom.swing.MigLayout;

/**
 * The new home for the preProces panel. It is now acting as a set-up wizard as opposed to a main component in GUIMain based on user
 * feedback. Much remains the same, it's just wrapped up in a JFrame with a few visual cue tweaks to help guide the user along.
 * @author Marc Barrowclift
 *
 */
public class PreProcessWindow extends JFrame {

	//Constants
	private final static String NAME = "( PreProcessWindow ) - ";
	protected final String DEFAULT_TRAIN_TREE_NAME = "Authors";
	
	//Variables
	private GUIMain main;
	public PreProcessWindowDriver driver;
	private static final long serialVersionUID = 1L;
	protected PreProcessAdvancedWindow advancedWindow;
	protected ProblemSet ps;
	
	//======Documents=====
	//problem set
	private JPanel preProcessPanel;
	private JPanel prepDocumentsPanel;
	protected JLabel prepDocLabel;
	private JLabel problemSetLabel;
	protected JButton saveProblemSetJButton;
	protected JButton loadProblemSetJButton;
		//doc to anonymize
		private JLabel mainLabel;
		protected JList<String> prepMainDocList;
		private JScrollPane prepMainDocScrollPane;
		protected JButton addTestDocJButton;
		protected JButton removeTestDocJButton;
		//other sample documents written by the author
		private JLabel sampleLabel;
		protected JList<String> prepSampleDocsList;
		private JScrollPane prepSampleDocsScrollPane;
		protected JButton addUserSampleDocJButton;
		protected JButton removeUserSampleDocJButton;
		//test documents by other authors
		private JLabel trainLabel;
		protected JTree trainCorpusJTree;
		private JScrollPane trainCorpusJTreeScrollPane;
		protected JButton addTrainDocsJButton;
		protected JButton removeTrainDocsJButton;
	
	/**
	 * Constructor
	 * @param main - Instance of GUIMain
	 */
	public PreProcessWindow(GUIMain main) {
		Logger.logln(NAME+"Preparing the Pre-process window for viewing");
		this.main = main;
		
		ps = new ProblemSet();
		ps.setTrainCorpusName(DEFAULT_TRAIN_TREE_NAME);
		
		advancedWindow = new PreProcessAdvancedWindow(this, main);
		driver = new PreProcessWindowDriver(this, advancedWindow, main);
		
		initComponents();
		initWindow();
		this.setVisible(false);
	}

	/**
	 * Displays the prepared window for viewing
	 */
	public void showWindow() {
		this.setVisible(true);
	}
	
	/**
	 * Initializes and prepares the Pre-process window for viewing
	 */
	private void initWindow() {
		//this.setResizable(false);
		this.setSize(500, 500);
		this.setTitle("Anonymouth Set-Up Wizard");
		this.setLocation((main.screensize.width/2)-(500/2), (main.screensize.height/2)-(500/2));
		this.driver.updateDocPrepColor();
	}
	
	/**
	 * Adds all the components necessary to the Pre-process window wizard
	 */
	private void initComponents() {
		preProcessPanel = new JPanel();
		preProcessPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		MigLayout settingsLayout = new MigLayout(
				"fill, wrap 1, ins 0",
				"fill, grow",
				"fill, grow");
		preProcessPanel.setLayout(settingsLayout);
		prepDocumentsPanel = new JPanel();
		MigLayout documentsLayout = new MigLayout(
				"wrap, ins 0, gap 0 5",
				"grow, fill, center",
				"[][grow, fill][]");
		prepDocumentsPanel.setLayout(documentsLayout);
		{
			//Documents Label
			prepDocLabel = new JLabel("Documents:");
			prepDocLabel.setFont(GUIMain.titleFont);
			prepDocLabel.setHorizontalAlignment(SwingConstants.CENTER);
			prepDocLabel.setBorder(GUIMain.rlborder);
			prepDocLabel.setOpaque(true);
			prepDocLabel.setBackground(main.notReady);
			prepDocLabel.setToolTipText("Click here to access advanced confirguration");

			//Problem Set
			problemSetLabel = new JLabel("Problem Set:");
			problemSetLabel.setHorizontalAlignment(SwingConstants.CENTER);

			saveProblemSetJButton = new JButton("Save");
			loadProblemSetJButton = new JButton("Load");

			//Document to Anonymize
			mainLabel = new JLabel("<html><center>Your Document<br>To Anonymize:</center></html>");
			mainLabel.setHorizontalAlignment(SwingConstants.CENTER);

			DefaultListModel<String> mainDocListModel = new DefaultListModel<String>();
			prepMainDocList = new JList<String>(mainDocListModel);
			prepMainDocList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			prepMainDocScrollPane = new JScrollPane(prepMainDocList);

			addTestDocJButton = new JButton("+");
			removeTestDocJButton = new JButton("-");
			
			//Other sample documents written by the author
			sampleLabel = new JLabel("<html><center>Your Other<br>Sample Documents:</center></html>");
			sampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			addUserSampleDocJButton = new JButton("+");
			removeUserSampleDocJButton = new JButton("-");

			DefaultListModel<String> sampleDocsListModel = new DefaultListModel<String>();
			prepSampleDocsList = new JList<String>(sampleDocsListModel);
			prepSampleDocsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			prepSampleDocsScrollPane = new JScrollPane(prepSampleDocsList);
			
			//Test documents by other authors
			trainLabel = new JLabel("<html><center>Documents You Didn't Write<br>(At Least 3 Authors):</center></html>");
			trainLabel.setHorizontalAlignment(SwingConstants.CENTER);

			DefaultMutableTreeNode top = new DefaultMutableTreeNode(ps.getTrainCorpusName(), true);
			trainCorpusJTree = new JTree(top, true);
			trainCorpusJTreeScrollPane = new JScrollPane(trainCorpusJTree);

			addTrainDocsJButton = new JButton("+");
			removeTrainDocsJButton = new JButton("-");

			//Adding everything to the panel
			prepDocumentsPanel.add(prepDocLabel, "h " + GUIMain.titleHeight + "!, wrap");
			prepDocumentsPanel.add(problemSetLabel, "alignx 50%, wrap");
			prepDocumentsPanel.add(saveProblemSetJButton, "span 4, split 2, w 20::");
			prepDocumentsPanel.add(loadProblemSetJButton, "w 20::");
			JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
			prepDocumentsPanel.add(separator, "span 4, wrap, h 13!");
			prepDocumentsPanel.add(mainLabel, "split, w 50%");
			prepDocumentsPanel.add(sampleLabel, "wrap, w 50%");
			prepDocumentsPanel.add(prepMainDocScrollPane, "split, h 40:100:180, w 30:60:150, w 50%");
			prepDocumentsPanel.add(prepSampleDocsScrollPane, "h 40:100:180, w 30:60:150, wrap, w 50%, wrap");

			prepDocumentsPanel.add(addTestDocJButton, "split 4, w 10::, gap 0");
			prepDocumentsPanel.add(removeTestDocJButton, "w 10::, gap 0");
			prepDocumentsPanel.add(addUserSampleDocJButton, "w 10::, gap 0");
			prepDocumentsPanel.add(removeUserSampleDocJButton, "wrap, w 10::, gap 0");

			prepDocumentsPanel.add(trainLabel, "span");
			prepDocumentsPanel.add(trainCorpusJTreeScrollPane, "span, h 10::345");
			prepDocumentsPanel.add(addTrainDocsJButton, "split 2, w 10::");
			prepDocumentsPanel.add(removeTrainDocsJButton, "w 10::");
		}

		preProcessPanel.add(prepDocumentsPanel, "growx");
		
		this.add(preProcessPanel);
	}
	
	protected boolean documentsAreReady() {
		boolean ready = true;
		try {
			if (!mainDocReady())
				ready = false;
			if (!sampleDocsReady())
				ready = false;
			if (!otherDocsReady())
				ready = false;
		} catch (Exception e) {
			return false;
		}

		return ready;
	}

	protected boolean mainDocReady() {
		if (ps.hasTestDocs())
			return true;
		else
			return false;
	}

	protected boolean sampleDocsReady() {
		try {
			if (!ps.getTrainDocs(ProblemSet.getDummyAuthor()).isEmpty())
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean otherDocsReady() {
		try {
			boolean result = true;
			if (ps.getAuthors().size() == 0)
				result = false;
			else {
				for (int i = 0; i < ps.getAuthors().size(); i++) {
					String author = (String)ps.getAuthors().toArray()[i];
					Set<String> authors = ps.getAuthors();
					for (String curAuthor : authors) {
						if (ps.getTrainDocs(curAuthor).isEmpty()) {
							result = false;
							break;
						}
					} if (!author.equals(ProblemSet.getDummyAuthor())) {
						if (ps.numTrainDocs(author) < 1) {
							result = false;
							break;
						}
					} else if (ps.getAuthors().size() == 1) {
						result = false;
						break;
					}
				}
			}

			return result;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected boolean hasAtLeastThreeOtherAuthors() {
		Set<String> trainAuthors = ps.getAuthors();
		
		if ((trainAuthors == null) || (trainAuthors.size() < 3))
			return false;
		else
			return true;
	}
}
