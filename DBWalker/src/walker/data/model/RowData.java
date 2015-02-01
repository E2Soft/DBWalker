package walker.data.model;

import java.util.HashMap;

public class RowData extends HashMap<String, Object>  
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ista imena kolona.
	 */
	public boolean equivalent(RowData rowData)
	{
		if(rowData == null)
		{
			return false;
		}
		
		return this.keySet().equals(rowData.keySet());
	}
}
