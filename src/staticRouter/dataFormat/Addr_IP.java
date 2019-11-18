package staticRouter.dataFormat;

public class Addr_IP {
	
	private byte[] addr_IP = new byte[4];
	
	public Addr_IP(byte[] setIP) { 
		this.addr_IP = setIP; 
	}
	public byte[] getter() { return this.addr_IP; }

}
