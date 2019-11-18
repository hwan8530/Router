package staticRouter.commonTools;

public class ByteParser<G> {
	
	private byte[] ByteFormat;
	private G objFormat;
	public ByteParser(G setObj) { this.objFormat = setObj; }
	public ByteParser(byte[] setByte) {this.ByteFormat = setByte;}
	public byte[] objToBytes() {
		
		// parse - use len
		return this.ByteFormat;
	}
	public G bytesToObj() {
		
		// parse - use len
		return this.objFormat;
	}
	
	
	
}
