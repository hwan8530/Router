package ARP;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapIf;
import org.jnetpcap.PcapSockAddr;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;


public class NILayer implements BaseLayer {
	

	public int nUpperLayerCount = 0;
	public String pLayerName = null;
	public BaseLayer p_UnderLayer = null;
	public ArrayList<BaseLayer> p_aUpperLayer = new ArrayList<BaseLayer>();

	int m_iNumAdapter;
	public Pcap m_AdapterObject;
	public PcapIf device;
	public List<PcapIf> m_pAdapterList;
	StringBuilder errbuf = new StringBuilder();
	
	private int ipAddr;
	private byte[] macAddr;

	public NILayer(String pName) {
		
		pLayerName = pName;
		
		m_pAdapterList = new ArrayList<PcapIf>();
		m_iNumAdapter = 0;
		
		SetAdapterList();
	}

	public void SetAdapterList() {
		
		int r = Pcap.findAllDevs(m_pAdapterList, errbuf);
		if (r == Pcap.NOT_OK || m_pAdapterList.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
			return;
		}
	}

//	public void SetAdapterNumber() throws IOException {
//	      InetAddress address = InetAddress.getLocalHost();
//	      String localIp = address.getHostAddress();
//	      String hostName = address.getHostName();
//	      NetworkInterface ni = NetworkInterface.getByInetAddress(address);
//	      byte[] localmac = ni.getHardwareAddress();
//	      String temp = new String(localmac);
//	      for(int i = 0; i < m_pAdapterList.size(); i++) {
//	         if(m_pAdapterList.get(i).getName().equals(temp))
//	            m_iNumAdapter = i;
//	      }
//	      PacketStarDriver();
//	      Receive();
//	   }
	
	
//	public void SetAdapterNumber(int iNum) {
////		m_iNumAdapter = iNum;
////		PacketStarDriver();
////		Receive();
//	}

	// GUI에서 호출, AdapterIndex를 넘겨받는다.
	public void SetAdapterNumber(int iNum) throws IOException {
		
		m_iNumAdapter = iNum;
		
		device = this.m_pAdapterList.get(m_iNumAdapter);
		
		ipAddr = GetDeviceIpAddress(device);
		macAddr = device.getHardwareAddress();
		
		
		/*
		// 로컬 IP취득
		InetAddress ip = InetAddress.getLocalHost();
		byte[] mac = null;
				
		// 네트워크 인터페이스 취득
		NetworkInterface netif = NetworkInterface.getByInetAddress(ip);
		System.out.println(netif);

		// 네트워크 인터페이스가 NULL이 아니면
		if (netif != null) {
			
			// 네트워크 인터페이스 표시명 출력
			System.out.print(netif.getDisplayName() + " : ");
					
			// 맥어드레스 취득
			mac = netif.getHardwareAddress();
			
			for (int i = 0; i < this.m_pAdapterList.size(); i++) {
				PcapIf temp = this.m_pAdapterList.get(i);
				byte[] adapterMac = temp.getHardwareAddress();
				
				for (int j = 0; j < mac.length; j++) {
					if (mac[j] == adapterMac[j])
						this.m_iNumAdapter = i;
					else {
						this.m_iNumAdapter = 0;
					}
				}
			}
		}*/
		
		PacketStarDriver();
	    Receive();
	   }
	
	
	//IP Address를 반환한다.
	private int GetDeviceIpAddress(PcapIf dev) {
		List<PcapAddr> addrs = dev.getAddresses();
	    ListIterator<PcapAddr> it = addrs.listIterator();
	    
	    PcapAddr ipv4 = null;
	    
	    while (it.hasNext()) {
	        PcapAddr temp = it.next();
	        if (temp.getAddr().getFamily() == PcapSockAddr.AF_INET) {
	            ipv4 = temp;
	        }
	    }
	    
	    if (ipv4 == null) {
	        return -1;
	    } else {
	        byte[] ipv4byte = ipv4.getAddr().getData();
	        assert (ipv4byte.length == 4);
	        int retv = 0;
	        retv = ((ipv4byte[0] << 24) & 0xff000000) | ((ipv4byte[1] << 16) & 0x00ff0000) | ((ipv4byte[2] << 8) & 0x0000ff00) | (ipv4byte[3] & 0x000000ff);
	        return retv;
	    }
	}

	public void PacketStarDriver() {
		// TODO Auto-generated method stub
		int snaplen = 64 * 1024;
		int flags = Pcap.MODE_PROMISCUOUS;
		int timeout = 10 * 1000;
		m_AdapterObject = Pcap.openLive(m_pAdapterList.get(m_iNumAdapter).getName(), snaplen, flags, timeout, errbuf);
	}
	
	public boolean Receive() {

		// TODO Auto-generated method stub
		Receive_Thread thread = new Receive_Thread(m_AdapterObject, this.GetUpperLayer(0));
		Thread obj = new Thread(thread);
		obj.start();
		System.out.println("NI");
		return false;
	}

	public boolean Send(byte[] input, int length) {
		ByteBuffer buf = ByteBuffer.wrap(input);
		if (m_AdapterObject.sendPacket(buf) != Pcap.OK) {
			System.err.println(m_AdapterObject.getErr());
			return false;
		}
		return true;
	}
	
	public int GetIpAddress(){
		return this.ipAddr;
	}
	
	public byte GetMacAddress(){
		return this.macAddr;
	}
	

	@Override
	public String GetLayerName() {
		// TODO Auto-generated method stub
		return pLayerName;
	}

	@Override
	public BaseLayer GetUnderLayer() {
		// TODO Auto-generated method stub
		if (p_UnderLayer == null)
			return null;
		return p_UnderLayer;
	}

	@Override
	public BaseLayer GetUpperLayer(int nindex) {
		// TODO Auto-generated method stub
		if (nindex < 0 || nindex > nUpperLayerCount || nUpperLayerCount < 0)
			return null;
		return p_aUpperLayer.get(nindex);
	}

	@Override
	public void SetUnderLayer(BaseLayer pUnderLayer) {
		// TODO Auto-generated method stub
		if (pUnderLayer == null)
			return;
		p_UnderLayer = pUnderLayer;

	}

	@Override
	public void SetUpperLayer(BaseLayer pUpperLayer) {
		// TODO Auto-generated method stub
		if (pUpperLayer == null)
			return;
		this.p_aUpperLayer.add(nUpperLayerCount++, pUpperLayer);
		// nUpperLayerCount++;

	}

	@Override
	public void SetUpperUnderLayer(BaseLayer pUULayer) {
		// TODO Auto-generated method stub
		this.SetUpperLayer(pUULayer);
		pUULayer.SetUnderLayer(this);

	}

}

class Receive_Thread implements Runnable {
	
	byte[] data;
	Pcap AdapterObject;
	BaseLayer UpperLayer;

	public Receive_Thread(Pcap m_AdapterObject, BaseLayer m_UpperLayer) {
		AdapterObject = m_AdapterObject;
		UpperLayer = m_UpperLayer;
	}

	public void run() {
		while (true) {
			PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
				public void nextPacket(PcapPacket packet, String user) {
					data = packet.getByteArray(0, packet.size());
					UpperLayer.Receive(data);
				}
			};
			
			AdapterObject.loop(100000, jpacketHandler, "");
		
		}
	}
}