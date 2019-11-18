package staticRouter.dataFormat;

public class Addr_MAC {

	private byte[] addr_MAC = new byte[4];
	
	public Addr_MAC(byte[] setMAC) {
		this.addr_MAC = setMAC; 
	}
	public byte[] getter() { return this.addr_MAC; }

}
