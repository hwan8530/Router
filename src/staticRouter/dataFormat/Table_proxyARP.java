package staticRouter.dataFormat;

import java.util.ArrayList;

public class Table_proxyARP {
	
	private byte[] addr_IP;
	private byte[] addr_MAC;
	private ArrayList<Table_ARP> Table_List;
	
	public void setIP(Addr_IP ip) {
		this.addr_IP = ip.getter();
	}
	public void setMAC(Addr_MAC mac) {
		this.addr_MAC = mac.getter();
	}
	public byte[] getIP() {
		return this.addr_IP;
	}
	public byte[] getMAC() {
		return this.addr_MAC;
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
