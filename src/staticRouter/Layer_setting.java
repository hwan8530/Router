package staticRouter;

import java.util.ArrayList;

public class Layer_setting {
	
	private int nUpperLayerCount = 0;
	private String pLayerName = null;
	private Layer_setting p_UnderLayer = null;
	private ArrayList<Layer_setting> p_aUpperLayer = new ArrayList<Layer_setting>();

	public String GetLayerName() { return this.pLayerName; }
	public Layer_setting GetUnderLayer() {
		if (this.p_UnderLayer == null) return null;
		return this.p_UnderLayer;
	}
	public Layer_setting GetUpperLayer(int nindex) {
		if (nindex < 0 || nindex > this.nUpperLayerCount 
				|| this.nUpperLayerCount < 0) return null;
		return this.p_aUpperLayer.get(nindex);
	}
	public void SetUnderLayer(Layer_setting pUnderLayer) {
		if (this.p_UnderLayer == null)return;
		this.p_UnderLayer = pUnderLayer;
	}
	public void SetUpperLayer(Layer_setting pUpperLayer) {
		if (pUpperLayer == null)return;
		this.p_aUpperLayer.add(nUpperLayerCount++, pUpperLayer);
	}
	
	public void SetUpperUnderLayer(Layer_setting pUULayer) {
		this.SetUpperLayer(pUULayer);
		pUULayer.SetUnderLayer(this);
	}

}
