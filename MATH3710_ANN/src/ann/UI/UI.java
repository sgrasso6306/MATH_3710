package ann.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Controller _controller;
	private JPanel _mainPanel;
	private OutputPanel _outputPanel;
	private JMenuBar _menuBar;
	private JMenu _networkMenu, _dataSetMenu;
	private JMenuItem _newNetwork, _openNetwork, _saveNetworkAs, _saveNetwork, _setLearningConstant, _buildDataSet, _loadDataSet, _saveDataSet, _trainNetwork, _computeOutput;
	
	public UI(Controller c) {
		super("Neural Net Designer");
		_controller = c;
	}
	
	public void createAndDisplayGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    	try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.println("Unable to set LookAndFeel");
        }
    	
    	_mainPanel = getPanel();
    	_mainPanel.setOpaque(true);
    	
    
        //Create and set up the content pane.
        JPanel newContentPane = new JPanel();
        newContentPane.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        newContentPane.setOpaque(true); //content panes must be opaque
		gc.fill = GridBagConstraints.HORIZONTAL;
		
		gc.gridx = 0;
		gc.gridy = 0;
        newContentPane.add(_mainPanel, gc);
        
        setContentPane(newContentPane);
        setJMenuBar(buildMenuBar());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    	
	}
	
	public void outputPrint(String s) {
		_outputPanel.print(s);
	}
	public void outputPrintln(String s) {
		_outputPanel.println(s);
	}
	
    public void actionPerformed(ActionEvent e) {
        if ("NEW_NETWORK".equals(e.getActionCommand())) {
        	if(_controller.newNetwork()) {
        		setEnabled(_saveNetworkAs, true);
        		setEnabled(_saveNetwork, false);
        		setEnabled(_setLearningConstant, true);
        	}
        }
        else if ("OPEN_NETWORK".equals(e.getActionCommand())) {
        	if(_controller.openNetwork()) {
        		setEnabled(_saveNetworkAs, true);
        		setEnabled(_saveNetwork, true);
        		setEnabled(_setLearningConstant, true);
        	}
        }
        else if ("SAVE_NETWORK_AS".equals(e.getActionCommand())) {
        	if (_controller.saveNetworkAs()) {
        		setEnabled(_saveNetwork, true);
        	}
        }
        else if ("SAVE_NETWORK".equals(e.getActionCommand())) {
        	_controller.saveNetwork();
        }
        else if ("SET_LEARNING_CONSTANT".equals(e.getActionCommand())) {
        	_controller.setLearningConstant();
        }        
        else if ("BUILD_DATASET".equals(e.getActionCommand())) {
        	if (_controller.buildDataSet()) {
        		setEnabled(_saveDataSet,true);
        	}  
        }
        else if ("LOAD_DATASET".equals(e.getActionCommand())) {
        	if (_controller.loadDataSet()) {
        		setEnabled(_saveDataSet,true);
        	}
        }
        else if ("SAVE_DATASET".equals(e.getActionCommand())) {
        	_controller.saveDataSet();
        }
        else if ("TRAIN_NETWORK".equals(e.getActionCommand())) {
        	_controller.trainNetwork();
        }
        else if ("COMPUTE_SINGLE_OUTPUT".equals(e.getActionCommand())) {
        	_controller.computeSingleOutput();
        }
        else {
        	System.out.println(e.getActionCommand());
        	System.exit(0);
        }
        pack();
        setVisible(true);
    }
    

    public JMenuBar buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu networkMenu = new JMenu("Network");
		_newNetwork = new JMenuItem("New network...");
		_openNetwork = new JMenuItem("Open network...");
		_saveNetworkAs = new JMenuItem("Save network as...");
		_saveNetwork = new JMenuItem("Save network");
		_setLearningConstant = new JMenuItem("Set learning constant...");
		_newNetwork.addActionListener(this);
		_newNetwork.setActionCommand("NEW_NETWORK");
		_openNetwork.addActionListener(this);
		_openNetwork.setActionCommand("OPEN_NETWORK");
		_saveNetworkAs.addActionListener(this);
		_saveNetworkAs.setActionCommand("SAVE_NETWORK_AS");
		_saveNetwork.addActionListener(this);
		_saveNetwork.setActionCommand("SAVE_NETWORK");
		_setLearningConstant.addActionListener(this);
		_setLearningConstant.setActionCommand("SET_LEARNING_CONSTANT");
		networkMenu.add(_newNetwork);
		networkMenu.add(_openNetwork);
		networkMenu.add(_saveNetworkAs);
		networkMenu.add(_saveNetwork);
		networkMenu.add(_setLearningConstant);
		_saveNetworkAs.setEnabled(false);
		_saveNetwork.setEnabled(false);
		_setLearningConstant.setEnabled(false);
		
		JMenu dataSetMenu = new JMenu("Data Set");
		_buildDataSet = new JMenuItem("Build data set...");
		_loadDataSet = new JMenuItem("Load data set...");
		_saveDataSet = new JMenuItem("Save data set as...");
		_buildDataSet.addActionListener(this);
		_buildDataSet.setActionCommand("BUILD_DATASET");
		_loadDataSet.addActionListener(this);
		_loadDataSet.setActionCommand("LOAD_DATASET");
		_saveDataSet.addActionListener(this);
		_saveDataSet.setActionCommand("SAVE_DATASET");
		dataSetMenu.add(_buildDataSet);
		dataSetMenu.add(_loadDataSet);
		dataSetMenu.add(_saveDataSet);
		_saveDataSet.setEnabled(false);
		
		
		JMenu runMenu = new JMenu("Run");
		_trainNetwork = new JMenuItem("Train network...");
		_computeOutput = new JMenuItem("Compute output...");
		_trainNetwork.addActionListener(this);
		_trainNetwork.setActionCommand("TRAIN_NETWORK");
		_computeOutput.addActionListener(this);
		_computeOutput.setActionCommand("COMPUTE_SINGLE_OUTPUT");
		runMenu.add(_trainNetwork);
		runMenu.add(_computeOutput);
		
		menuBar.add(networkMenu);
		menuBar.add(dataSetMenu);
		menuBar.add(runMenu);
		
		return menuBar;
    }
    
	public JPanel getPanel() {
		JPanel bottomPanel = new JPanel();
		
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();		

		//	Bottom panel components
		_outputPanel = new OutputPanel();
		
		
		//gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		bottomPanel.add(_outputPanel,gc);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());	
		gc.gridx = 0;
		gc.gridy = 0;
		panel.add(bottomPanel,gc);
		
		return panel;
	}

	public void setEnabled(Component c, boolean enabled) {
		c.setEnabled(enabled);
	}

	public class OutputPanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private JTextArea outputText;
		private JScrollPane scrollPane;
		private JButton clearButton;
		
		//JTextField tf = new JTextField(12);
        //outputFrame.add(tf, BorderLayout.NORTH);
		
		
				
		public OutputPanel() {
			this.setLayout(new GridBagLayout());
	        GridBagConstraints gc = new GridBagConstraints();
	        
			outputText = new JTextArea(20,100);
			scrollPane = new JScrollPane(outputText);
			//clearButton = new JButton("Clear Output Display");
			//clearButton.addActionListener(this);
			
			outputText.setEditable(false);
			
			
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridx = 0;
			gc.gridy = 0;
			//add(clearButton, gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridx = 0;
			//gc.gridy = 1;
			add(scrollPane, gc);
		}
		
		public void print(String s) {
			outputText.append(s);
			JScrollBar vertical = scrollPane.getVerticalScrollBar();
			vertical.setValue( vertical.getMaximum() );
		}
		public void println(String s) {
			outputText.append(s);
			outputText.append("\n");
			JScrollBar vertical = scrollPane.getVerticalScrollBar();
			vertical.setValue( vertical.getMaximum() );
		}
		
		public void clearText() {
			outputText.setText("");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// only clear button, so laziness.
			clearText();
		}
        
        
	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}

	public OutputPanel getOutput() {
		return _outputPanel;
	}
}
