package staticRouter.dataFormat;

import java.util.ArrayList;

public class Table_Routing {

	private byte[] addr_dstIP;
	private byte[] addr_netmask;
	private byte[] addr_Gateway;
	private final String[] FLAG = new String[] {"","",""};
	
	private ArrayList<Table_ARP> Table_List;
	
	public void setDstIP(Addr_IP ip) {
		this.addr_dstIP = ip.getter();
	}
	public void setNetmask(Addr_MAC mac) {
		this.addr_netmask = mac.getter();
	}
	public void setGateway(Addr_IP ip) {
		this.addr_Gateway = ip.getter();
	}
	public byte[] getDstIP() {
		return this.addr_dstIP;
	}
	public byte[] getNetmask() {
		return this.addr_netmask;
	}
	public ArrayList<Table_ARP> getTable() {
		return this.Table_List;
	}
	public void addList(Table_ARP newObj) {
		this.Table_List.add(newObj);
	}
	public void popList(int idx) {
		this.Table_List.get(idx);
	}
	
}
