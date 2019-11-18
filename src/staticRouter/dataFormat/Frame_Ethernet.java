package staticRouter.dataFormat;

public class Frame_Ethernet {

	private byte[] enet_dstaddr;
	private byte[] enet_srcaddr;
	private byte[] enet_type;
	
	public Frame_Ethernet(Addr_MAC srcAddr,
			Addr_MAC dstAddr,byte[] type) {
	
		this.enet_srcaddr = srcAddr.getter();
		this.enet_dstaddr= dstAddr.getter();
		this.enet_type = type;
	
	}
	
}
