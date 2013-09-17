package info.hexin.jmacs.mvc.fileupload;

import java.nio.ByteBuffer;

public class FileuploadTest {
	public static void main(String[] args) {
		byte[] data = ("------WebKitFormBoundaryijBFDNaOyDFxg1Tt"
				+ "Content-Disposition: form-data; name=\"fileforload1\"; filename=\"11.txt\""
				+ "Content-Type: text/plain\n\r\n\r" 
				+ "11111111111111111111\n\r\n\r" + "1111111111111111111111"
				// 文件分割
				+ "------WebKitFormBoundaryijBFDNaOyDFxg1Tt"
				+ "Content-Disposition: form-data; name=\"fileforload2\"; filename=\"22.txt\""
				+ "Content-Type: text/plain\n\r\n\r" 
				+ "22222222222222222222222222222222"
				//文件分割
				+ "------WebKitFormBoundaryijBFDNaOyDFxg1Tt" 
				+ "Content-Disposition: form-data; name=\"text\"\n\r\n\r" 
				+ "3123" 
				//文件分割
				+ "------WebKitFormBoundaryijBFDNaOyDFxg1Tt"
				+ "Content-Disposition: form-data; name=\"submit\"\n\r" + "\n\r" + "commit"
				+ "------WebKitFormBoundaryijBFDNaOyDFxg1Tt--").getBytes();

//		String contentType = "------WebKitFormBoundaryijBFDNaOyDFxg1Tt";
		
		byte[] fileStartFlag = "------WebKitFormBoundaryijBFDNaOyDFxg1Tt".getBytes();
		byte[] endNameFlag = "\n\r\n\r".getBytes();

		ByteBuffer fileNameBuffer = ByteBuffer.allocate(data.length);
		ByteBuffer fileContextBuffer = ByteBuffer.allocate(data.length);
		boolean inFileFlag = false;
		boolean inFileContextFlag = false;
		for (int i = 0, n = 0, k = 0; i < data.length; i++) {
			byte temp = data[i];
			// 第一次还没有进入file范围
			if (!inFileFlag) {
				// 查找file入口
				if (n < fileStartFlag.length && temp == fileStartFlag[n++]) {
					// 进入下一个阶段
					if (n + 1 == endNameFlag.length) {
						n = 0;
						inFileFlag = true;
					}
				} else {
					// 开始不需要回溯
					n = 0;
					inFileFlag = true;
				}
			}
			// 进入文件内部
			else if (!inFileContextFlag) {

				// 查找file入口
				byte nameByte = endNameFlag[k++];
				if (k <= endNameFlag.length && temp == nameByte) {
					// System.out.println("like >>> " + (char) temp + " : " +
					// (char) nameByte + " i >>>> " + i
					// + "   k >>>> " + k);
					//
					if (k == endNameFlag.length) {
						k = 0;
						// System.out.println("endNameFlag.length >>>> " +
						// endNameFlag.length + "  k >>>> " + k);
						byte[] fileNameByte = new byte[fileNameBuffer.position()];
						fileNameBuffer.rewind();
						fileNameBuffer.get(fileNameByte);
						fileNameBuffer.rewind();
						System.out.println(" file Name >>>> " + new String(fileNameByte));
						inFileContextFlag = true;
					}
				}
				// 是否需要平移比如 nameend 但是"name"的end分隔符为 end，也就是说name的出现一样的byte需要平移
				else if (temp == endNameFlag[0]) {
					k = k - 1;
					fileNameBuffer.put(temp);
				} else {
					// System.out.println("temp >>> " + (char) temp + " : " +
					// (char) nameByte + "  i >>>> " + i
					// + "   k >>>> " + k);
					// 开始不需要回溯
					for (; k > 0; k--) {
						byte temp2 = data[i - k + 1];
						fileNameBuffer.put(temp2);
					}
					// inFileFlag = true;
				}
			} else {
				// 需要准备下一个文件域了
				// 查找file入口
				byte startFileChar = fileStartFlag[n++];
				// System.out.println("wenjian ti 》》 " + " n >> " + n +
				// " "+(char) temp + " : " + (char) startFileChar);
				if (n <= fileStartFlag.length && temp == startFileChar) {
					// 进入下一个阶段
					if (n == fileStartFlag.length) {
						n = 0;
						inFileFlag = true;
						inFileContextFlag = false;

						byte[] fileContextByte = new byte[fileContextBuffer.position()];
						fileContextBuffer.rewind();
						fileContextBuffer.get(fileContextByte);
						fileContextBuffer.rewind();
						System.out.println(" file Context >>>> " + new String(fileContextByte));
					}
				} else {
					fileContextBuffer.put(temp);
					n = 0;
				}
			}
		}
	}
}
