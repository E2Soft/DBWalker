package walker.data.model;

import java.util.HashMap;

public class KeyData extends HashMap<String, Object>  
{
	private static final long serialVersionUID = 1L;
	
	public boolean equalColumnNames(KeyData keyData)
	{
		if(keyData == null)
		{
			return false;
		}
		
		return this.keySet().equals(keyData.keySet());
	}
}
