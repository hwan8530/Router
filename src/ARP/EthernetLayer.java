package ARP;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class EthernetLayer implements BaseLayer {
	public String pLayerName = null;
	public int nUpperLayerCount = 0;
	public BaseLayer p_UnderLayer = null;
	public ArrayList<BaseLayer> p_aUpperLayer = new ArrayList<BaseLayer>();
	public EthernetLayer(String pName) {pLayerName = pName;} // super(pName);}
	@Override
	public String GetLayerName() {return pLayerName;}
	@Override
	public BaseLayer GetUnderLayer() {
		if (p_UnderLayer == null)return null;
		return p_UnderLayer;
	}
	@Override
	public BaseLayer GetUpperLayer(int nindex) {
		if (nindex < 0 || nindex > nUpperLayerCount || nUpperLayerCount < 0)
			return null;
		return p_aUpperLayer.get(nindex);
	}
	@Override
	public void SetUnderLayer(BaseLayer pUnderLayer) {
		if (pUnderLayer == null) return;
		p_UnderLayer = pUnderLayer;
	}
	@Override
	public void SetUpperLayer(BaseLayer pUpperLayer) {
		if (pUpperLayer == null)return;
		this.p_aUpperLayer.add(nUpperLayerCount++, pUpperLayer);
	}
	@Override
	public void SetUpperUnderLayer(BaseLayer pUULayer) {
		this.SetUpperLayer(pUULayer);
		pUULayer.SetUnderLayer(this);
	}
	public void SetEnetSrcAddress(byte[] srcAddress) {
		ethernetHeader.enet_srcaddr.addr = srcAddress;
	}
	public void SetEnetDstAddress(byte[] dstAddress) {
		// TODO Auto-generated method stub
		ethernetHeader.enet_dstaddr.addr = dstAddress;
	}

	private class _ETHERNET_ADDR {
		private byte[] addr = new byte[6];

		public _ETHERNET_ADDR() {
			this.addr[0] = (byte) 0x00;
			this.addr[1] = (byte) 0x00;
			this.addr[2] = (byte) 0x00;
			this.addr[3] = (byte) 0x00;
			this.addr[4] = (byte) 0x00;
			this.addr[5] = (byte) 0x00;
		}
	}

	private class _ETHERNET_Frame {
		_ETHERNET_ADDR enet_dstaddr;
		_ETHERNET_ADDR enet_srcaddr;
		byte[] enet_type;
		byte[] enet_data;

		public _ETHERNET_Frame() {
			this.enet_dstaddr = new _ETHERNET_ADDR();
			this.enet_srcaddr = new _ETHERNET_ADDR();
			this.enet_type = new byte[2];
			this.enet_data = null;
		}
	}

	_ETHERNET_Frame ethernetHeader = new _ETHERNET_Frame();

	public byte[] ObjToByte(_ETHERNET_Frame Header, byte[] input, int length) {
		byte[] buf = new byte[length + 14];
		byte[] srctemp = Header.enet_srcaddr.addr;
		byte[] dsttemp = Header.enet_dstaddr.addr;

		buf[0] = dsttemp[0];
		buf[1] = dsttemp[1];
		buf[2] = dsttemp[2];
		buf[3] = dsttemp[3];
		buf[4] = dsttemp[4];
		buf[5] = dsttemp[5];
		buf[6] = srctemp[0];
		buf[7] = srctemp[1];
		buf[8] = srctemp[2];
		buf[9] = srctemp[3];
		buf[10] = srctemp[4];
		buf[11] = srctemp[5];
		buf[12] = (byte)0x20;
		buf[13] = (byte)0x80;

		for (int i = 0; i < length; i++)
			buf[14 + i] = input[i];

		return buf;
	}
	
	public byte[] ObjToByte_ARP(_ETHERNET_Frame Header, byte[] input, int length) {
		byte[] buf = new byte[length + 14];
		byte[] srctemp = Header.enet_srcaddr.addr;
		byte[] dsttemp = Header.enet_dstaddr.addr;

		buf[0] = dsttemp[0];
		buf[1] = dsttemp[1];
		buf[2] = dsttemp[2];
		buf[3] = dsttemp[3];
		buf[4] = dsttemp[4];
		buf[5] = dsttemp[5];
		buf[6] = srctemp[0];
		buf[7] = srctemp[1];
		buf[8] = srctemp[2];
		buf[9] = srctemp[3];
		buf[10] = srctemp[4];
		buf[11] = srctemp[5];
		buf[12] = (byte)0x08;
		buf[13] = (byte)0x06;

		for (int i = 0; i < length; i++)
			buf[14 + i] = input[i];

		return buf;
	}
	
	public byte[] ObjToByte_File(_ETHERNET_Frame Header, byte[] input, int length) {
		byte[] buf = new byte[length + 14];
		byte[] srctemp = Header.enet_srcaddr.addr;
		byte[] dsttemp = Header.enet_dstaddr.addr;

		buf[0] = dsttemp[0];
		buf[1] = dsttemp[1];
		buf[2] = dsttemp[2];
		buf[3] = dsttemp[3];
		buf[4] = dsttemp[4];
		buf[5] = dsttemp[5];
		buf[6] = srctemp[0];
		buf[7] = srctemp[1];
		buf[8] = srctemp[2];
		buf[9] = srctemp[3];
		buf[10] = srctemp[4];
		buf[11] = srctemp[5];
		buf[12] = (byte)0x20;
		buf[13] = (byte)0x90;

		for (int i = 0; i < length; i++)
			buf[14 + i] = input[i];

		return buf;
	}

	public boolean Send(byte[] input, int length) {
		byte[] bytes = ObjToByte(ethernetHeader, input, length);
		this.GetUnderLayer().Send(bytes, length + 14);
		return true;
	}
	
	public boolean SendFile(byte[] input, int length) {
		byte[] bytes = ObjToByte_File(ethernetHeader, input, length);
		this.GetUnderLayer().Send(bytes, length + 14);
		return true;
	}
	
	public boolean SendARP(byte[] input, int length) {
		byte[] bytes = ObjToByte_ARP(ethernetHeader, input, length);
		this.GetUnderLayer().Send(bytes, length + 14);
		return true;
	}

	public boolean Receive(byte[] input) {
//		if ((input[12] != (byte) 0x20) || (input[13] != (byte) 0x80)) {
//			if ((input[12]) != (byte)0x20 || (input[13]) != (byte)0x90)
//				return false;
//		}
		
		byte[] data;
		byte[] temp_src = this.ethernetHeader.enet_srcaddr.addr;
		byte[] temp_dst = this.ethernetHeader.enet_dstaddr.addr;
		int broadCastCount = 0;
		byte[] temp;	
		temp = hexStringToByteArray("FF");
		
		for (int i = 0; i < 6; i++) {
			if (input[i] != temp_src[i] || input[i + 6] != temp_dst[i]) {
				if (input[i] == temp[0]) { // broadcast
 
					broadCastCount++;
					if (broadCastCount == i + 1)
						continue;
				}

				return false;
			}
		}
		// ����
//		if ((input[12]) == (byte)0x20 && (input[13]) == (byte)0x90) {
//			data = RemoveEtherHeader(input, input.length);
//			this.GetUpperLayer(1).Receive(data);
//			return true;
//		}
		if (this.isItMyPacket(input))
			return false;
		
		// ������ ��� Ÿ���� ARP�� ARPLayer�� �ø�
		if (this.isItARP(input)) {
			data = RemoveEtherHeader(input, input.length);
			this.GetUpperLayer(0).Receive(data);
			return true;
		}
		
		// ������ ��� Ÿ���� IP�� IPLayer�� �ø�
		if (this.isItIP(input)) {
			data = RemoveEtherHeader(input, input.length);
			this.GetUpperLayer(1).Receive(data);
			return true;
		}
		
//		data = RemoveEtherHeader(input, input.length);
//		this.GetUpperLayer(1).Receive(data);
		return false;
	}
	
	// ������ ��� Ÿ�԰˻��Ͽ� ARP���� Ȯ��
	public boolean isItARP(byte[] head) {
		if ((head[12]) == (byte)0x08 && (head[13]) == (byte)0x06)
			return true;
		else
			return false;
	}
	
	// ������ ��� Ÿ�԰˻��Ͽ� IP���� Ȯ��
	public boolean isItIP(byte[] head) {
		if ((head[12]) == (byte)0x08 && (head[13]) == (byte)0x00)
			return true;
		else
			return false;
	}
	
	// dst�� src�� ������ ���� ��Ŷ
	public boolean isItMyPacket(byte[] head) {
		for (int i = 0; i <= 5; i++) {
			if (head[i] != head[i + 6])
				return false;
		}
		
		return true;
	}
	
	public byte[] RemoveEtherHeader(byte[] input, int length) {
		byte[] data = new byte[length - 14];
		for (int i = 0; i < length - 14; i++) 
			data[i] = input[14 + i];
		return data;
	}



	public static String byteArrayToHexString(byte[] bytes) {

		StringBuilder sb = new StringBuilder();

		for (byte b : bytes) {
			sb.append(String.format("%02X", b & 0xff));
		}

		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}