import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class enter_number {
	/*************************************************************************
	 * 数字を入力
	 *************************************************************************/
	public static int method (String print) throws IOException {
		String data = null;
		int num = 0;
		boolean error = true;
		
		while(error) {
			System.out.print(print);
			BufferedReader br =
					new BufferedReader(new InputStreamReader(System.in));
		    data = br.readLine();
		    try {
		    	num = Integer.parseInt(data);
		    	error = false;
		    }catch(NumberFormatException e) {
		    	System.out.println("error 正しい数字が入力されていません");
		    }
		}
	    return num;
	}
	/*************************************************************************
	 * 最初の一文字のみを出力
	 *************************************************************************/
	public static int cut_out (int num) {
		String buffer = null;
		
		buffer = String.valueOf(num);
		buffer = buffer.substring(0, 1);
		num = Integer.parseInt(buffer);
		
		return num;
	}
}
