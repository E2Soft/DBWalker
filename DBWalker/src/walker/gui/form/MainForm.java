package walker.gui.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class MainForm extends JFrame {
	
	
	public MainForm() {
		initMainFrame();
		
		initMainLayout();
		
		positionAndShow();
	}
	
	private void initMainFrame(){
		setTitle("JWalker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screenSize.getWidth()*3/4;
		int height = (int)screenSize.getHeight()*3/4;
		setSize(width, height);
	}
	
	private void initMainLayout(){
		
		JPanel tree = new JPanel();
		tree.setOpaque(true);
		tree.setBackground(Color.white);

		JPanel parrentPAnel = new JPanel();
		//TablePanel tablePanel = new TablePanel();

		parrentPAnel.setOpaque(true);
		parrentPAnel.setBackground(Color.red);
		JPanel childsPanel = new JPanel();
		childsPanel.setOpaque(true);
		childsPanel.setBackground(Color.green);
		
		JScrollPane HscrollChild = new JScrollPane(childsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		HscrollChild.setMinimumSize(new Dimension(250, 250));
		JScrollPane HscrollParrent = new JScrollPane(parrentPAnel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JSplitPane Hspliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HscrollParrent, HscrollChild);
		Hspliter.setDividerLocation(400);
		Hspliter.setOneTouchExpandable(true);
		
		JScrollPane Vscroll = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setMinimumSize(new Dimension(250, 250));
		
		GridLayout grid = new GridLayout(3, 4);
		grid.setVgap(20);
		grid.setHgap(20);
		JPanel jostick = new JPanel(grid);
		jostick.setOpaque(true);
		jostick.setBackground(Color.YELLOW);
		jostick.setPreferredSize(new Dimension(150, 150));
		
		JButton W = new JButton("W");
		JButton A = new JButton("A");
		JButton S = new JButton("S");
		JButton D = new JButton("D");
		
		JPanel mid = new JPanel();
		mid.setOpaque(false);
		JPanel mid2 = new JPanel();
		mid2.setOpaque(false);
		JPanel mid3 = new JPanel();
		mid3.setOpaque(false);
		JPanel mid4 = new JPanel();
		mid4.setOpaque(false);
		JPanel mid5 = new JPanel();
		mid5.setOpaque(false);
		JPanel mid6 = new JPanel();
		mid6.setOpaque(false);
		
		jostick.add(mid);
		jostick.add(W);
		jostick.add(mid2);
		jostick.add(A);
		jostick.add(mid3);
		jostick.add(S);
		jostick.add(mid4);
		jostick.add(D);
		jostick.add(mid5);
		
		leftPanel.add(Vscroll, BorderLayout.CENTER);
		leftPanel.add(jostick, BorderLayout.SOUTH);
		
		JSplitPane Vspliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, Hspliter);
		Vspliter.setDividerLocation(250);
		Vspliter.setOneTouchExpandable(true);
		
		add(Vspliter);
	}
	
	private void positionAndShow(){
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
