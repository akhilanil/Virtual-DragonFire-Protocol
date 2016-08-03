package support.utils;

public class PacketBuilder {
	
	static long idcounter = 0;
	
	
	public String generatePacket(PacketType type,String data, String groupid, String sender ){
		
		String packet = null;
		
		switch(type){
			
			case PING_PACKET:
				packet = idGenerator(sender)+"`934~"+"0"+"`934~"+"1"+"`934~"+null+"`934~"+null; // A non-acknowledge ping packet
				break;
			case DATA_PACKET:
				packet = idGenerator(sender)+"`934~"+"0"+"`934~"+"0"+"`934~"+groupid+"`934~"+data; // A non-acknowledge data packet
				break;
			case DATA_ACK_PACKET:
				packet = sender+"`934~"+"1"+"`934~"+"0"+"`934~"+null+"`934~"+data; 
				break;
				default:
		
		}
		
		
		return packet;
	}
	
	
	private String idGenerator(String sender){
		
		idcounter++;
		
		//return new String(PacketBuilder.idcounter);
		return String.valueOf(idcounter) + sender;
		
	}

}
