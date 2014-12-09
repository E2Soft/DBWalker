package walker.gui.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

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
		int width = (int)screenSize.getWidth() / 2;
		int height = (int)screenSize.getHeight() / 2;
		setSize(width, height);
	}
	
	private void initMainLayout(){
		
		JPanel tree = new JPanel();
		tree.setOpaque(true);
		tree.setBackground(Color.white);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel parrentPAnel = new JPanel();
		parrentPAnel.setOpaque(true);
		parrentPAnel.setBackground(Color.red);
		
		JPanel childsPanel = new JPanel();
		childsPanel.setOpaque(true);
		childsPanel.setBackground(Color.green);
		
		JScrollPane HscrollChild = new JScrollPane(childsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane HscrollParrent = new JScrollPane(parrentPAnel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JSplitPane Hspliter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, HscrollParrent, HscrollChild);
		Hspliter.setMinimumSize(new Dimension(2000, 2000));
		
		Hspliter.setDividerLocation(10000);
		Hspliter.setOneTouchExpandable(true);
		
		panel.add(Hspliter, BorderLayout.CENTER);
		
		JScrollPane Vscroll = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JSplitPane Vspliter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, Vscroll, panel);
		Vspliter.setMinimumSize(new Dimension(250, 250));
		
		Vspliter.setDividerLocation(250);
		Vspliter.setOneTouchExpandable(true);
		
		add(Vspliter);
	}
	
	private void positionAndShow(){
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
