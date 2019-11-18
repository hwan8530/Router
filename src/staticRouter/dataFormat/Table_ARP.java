package staticRouter.dataFormat;

import java.util.ArrayList;

public class Table_ARP {
	
	private String interfaceName;
	private byte[] addr_IP;
	private byte[] addr_MAC;
	private String status;
	private ArrayList<Table_ARP> Table_List;
	public void setInterfaceName(String ifName) {
		this.interfaceName = ifName;
	}
	public void setIP(Addr_IP ip) {
		this.addr_IP = ip.getter();
	}
	public void setMAC(Addr_MAC mac) {
		this.addr_MAC = mac.getter();
	}
	public ArrayList<Table_ARP> getTable(){
		return this.Table_List;
	}
	public void addList(Table_ARP newObj) {
		this.Table_List.add(newObj);
	}
	public void popList(int idx) {
		this.Table_List.get(idx);
	}
	
}
