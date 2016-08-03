package org.main;

import org.model.ConnectionDetails;

public class Instance {
	
	
	static ConnectionDetails cDetails;
	
	public void  setInstanceofConnectionDetails(ConnectionDetails cDetails){
		
		Instance.cDetails = cDetails;
	}
	
	public ConnectionDetails  getInstanceofConnectionDetails(){
		
		return Instance.cDetails;
	}

}
