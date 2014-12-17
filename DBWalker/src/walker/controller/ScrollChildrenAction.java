package walker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrollChildrenAction implements ActionListener
{
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String direction = e.getActionCommand();
		
		if(LEFT.equals(direction))
		{
			// TODO
		}
		else if(RIGHT.equals(direction))
		{
			// TODO
		}
		else
		{
			throw new RuntimeException("Unknown direction in ScrollChildrenAction");
		}
	}
}
