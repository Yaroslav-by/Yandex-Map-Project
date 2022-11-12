package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import yandexAPI.MapObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;

public class YandexMapGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfSearch;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel labelMap;
	private ImageIcon image;
	
	private ArrayList<MapObject> results;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YandexMapGUI frame = new YandexMapGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	/**
	 * 
	 */
	public YandexMapGUI() {
		setTitle("Yandex Maps Project");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(Box.createRigidArea(new Dimension(30, 30)), BorderLayout.NORTH);
		contentPane.add(Box.createRigidArea(new Dimension(30, 30)), BorderLayout.EAST);
		
		JPanel panelForSearch = new JPanel();
		Box boxForSearch = new Box(BoxLayout.Y_AXIS);
		boxForSearch.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(panelForSearch, BorderLayout.WEST);
		panelForSearch.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Box boxForSearchButtons = new Box(BoxLayout.X_AXIS);
		boxForSearchButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
		boxForSearchButtons.add(Box.createRigidArea(new Dimension(30, 30)));
		
		tfSearch = new JTextField();
		tfSearch.setText("Лувр");
		tfSearch.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		tfSearch.setToolTipText("Введите, что вы хотите найти");
		tfSearch.setColumns(35);
		boxForSearchButtons.add(tfSearch);
		boxForSearchButtons.add(Box.createRigidArea(new Dimension(30, 30)));
		
		JList<MapObject> listForFoundObjects = new JList<MapObject>();
		DefaultListModel<MapObject> dlm = new DefaultListModel<MapObject>();
		listForFoundObjects.setFont(new Font("Times New Roman", Font.PLAIN, 16));		
		JScrollPane scrollPaneList = new JScrollPane(listForFoundObjects);
		scrollPaneList.setPreferredSize(new Dimension(100, 300));
		
		JButton searchButton = new JButton("Поиск");
		searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		searchButton.setPreferredSize(new Dimension(120, 30));
		searchButton.setFocusable(false);
		searchButton.addActionListener((e) -> {
			
			try {
				listForFoundObjects.setSelectedValue(null, false);
				results.clear();
				dlm.clear();
			} catch (NullPointerException e1) {
				System.out.println("oi");
			}
			
			String query = tfSearch.getText();
			
			results = MapObject.getMapObjects(MapObject.getResults(query, true), 
					                          MapObject.getResults(query, false));
			
			for (int i = 0; i < results.size(); i ++) {
				dlm.add(i, results.get(i));
			}
			
			listForFoundObjects.setModel(dlm);
			
		});
		
		JTextArea textAreaInformation = new JTextArea();
		JSlider slider = new JSlider();
		JRadioButton radioButtonScheme = new JRadioButton("Схема");
		JRadioButton radioButtonSatellite = new JRadioButton("Спутник");
		JCheckBox checkButtonTraffic = new JCheckBox("Пробки");
		textAreaInformation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textAreaInformation.setEditable(false);
		JScrollPane scrollPaneInformation = new JScrollPane(textAreaInformation);
		scrollPaneInformation.setPreferredSize(new Dimension(100, 200));
		
		listForFoundObjects.addListSelectionListener((e) -> {
			
			textAreaInformation.setText("");
			textAreaInformation.setText(((JList<MapObject>) e.getSource()).getSelectedValue().getDescription());
			
			image = new ImageIcon(
					MapObject.getImage(((JList<MapObject>) e.getSource()).getSelectedValue().getCoordinates(), 
					slider.getValue(), 
					radioButtonSatellite.isSelected(), 
					checkButtonTraffic.isSelected())); 
			labelMap.setIcon(image);
			repaint();
		});
		
		boxForSearchButtons.add(searchButton);
		boxForSearchButtons.add(Box.createRigidArea(new Dimension(30, 30)));
		
		boxForSearch.add(boxForSearchButtons);
		boxForSearch.add(Box.createRigidArea(new Dimension(30, 20)));
		
		Box boxForList = new Box(BoxLayout.X_AXIS);
		boxForList.add(Box.createRigidArea(new Dimension(30, 30)));
		
		JLabel labelResult = new JLabel("Результаты поиска");
		labelResult.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		boxForSearch.add(labelResult);
		
		boxForList.add(scrollPaneList);
		boxForList.add(Box.createRigidArea(new Dimension(30, 30)));
		
		boxForSearch.add(boxForList);
		boxForSearch.add(Box.createRigidArea(new Dimension(30, 20)));
		
		Box boxForTextArea = new Box(BoxLayout.X_AXIS);
		boxForTextArea.add(Box.createRigidArea(new Dimension(30, 30)));
		
		JLabel labelInformation = new JLabel("Информация");
		labelInformation.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		boxForSearch.add(labelInformation);
		
		boxForTextArea.add(scrollPaneInformation);
		boxForTextArea.add(Box.createRigidArea(new Dimension(30, 30)));
		
		boxForSearch.add(boxForTextArea);
		panelForSearch.add(boxForSearch);
		
		JPanel panelForMap = new JPanel();
		Box boxForMap = new Box(BoxLayout.Y_AXIS);
		contentPane.add(panelForMap, BorderLayout.CENTER);
		
		JLabel labelMapName = new JLabel("Карта");
		labelMapName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		boxForMap.add(labelMapName);
		
		Box boxForLabel = new Box(BoxLayout.X_AXIS);
		
		labelMap = new JLabel();
		labelMap.setHorizontalAlignment(SwingConstants.CENTER);
		image = new ImageIcon("111.png");
		labelMap.setIcon(image);
		labelMap.setBackground(Color.black);
		boxForLabel.add(labelMap);
		
		boxForMap.add(boxForLabel);
		boxForMap.add(Box.createRigidArea(new Dimension(30, 30)));
		
		Box boxForSlider = new Box(BoxLayout.X_AXIS);
		boxForSlider.add(Box.createRigidArea(new Dimension(20, 30)));
		
		slider.setMajorTickSpacing(3);
		slider.setMinorTickSpacing(1);
		slider.setValue(15);
		slider.setMaximum(18);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		slider.addChangeListener((e) -> {
			
			
			
		});
		boxForSlider.add(slider);
		boxForSlider.add(Box.createRigidArea(new Dimension(20, 30)));
		
		boxForMap.add(boxForSlider);
		boxForMap.add(Box.createRigidArea(new Dimension(30, 20)));
		
		Box boxForCheckButtons = new Box(BoxLayout.X_AXIS);
		
		radioButtonScheme.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radioButtonScheme.setSelected(true);;
		radioButtonScheme.setFocusable(false);
		buttonGroup.add(radioButtonScheme);
		
		radioButtonSatellite.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		radioButtonSatellite.setFocusable(false);
		buttonGroup.add(radioButtonSatellite);
		
		checkButtonTraffic.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		checkButtonTraffic.setFocusable(false);
		
		boxForCheckButtons.add(radioButtonScheme);
		boxForCheckButtons.add(Box.createRigidArea(new Dimension(70, 30)));
		boxForCheckButtons.add(radioButtonSatellite);
		boxForCheckButtons.add(Box.createRigidArea(new Dimension(70, 30)));
		boxForCheckButtons.add(checkButtonTraffic);
		
		boxForMap.add(boxForCheckButtons);
		panelForMap.add(boxForMap);
		
	}

}
