import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

class data_save {
	String[] player_name;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	int[] player_arsenal;
	BufferedWriter bw = null;
	/*******************************************************************************
	 * データを保存するプログラム
	 *******************************************************************************/
	data_save data_save(String[] pl_name, int[] pl_status, int[][] pl_efficacy, int[][] pl_skil,
			String[] pl_belonging, int[] pl_arsenal, boolean Auto_save) throws IOException {
		final String STR_COMMA = ",";                           // 区切り文字
        boolean ret;                                            // メソッドの返却値
        String fname = "save_data.csv";                         // ファイル名
        String buf;
        String save = null;
        
        if(Auto_save == true) {
        	save = "y";
        }else {
        	System.out.print("status data を保存しますか？ 《y or n 入力》");
    		BufferedReader br =
    				new BufferedReader(new InputStreamReader(System.in));
    		save = br.readLine();
        }
		if(save.equals("y")){

	        ret = open(fname);
	        
	        /* データの保存 */
	        buf = pl_name[0] + STR_COMMA +
	        	pl_name[1]+ STR_COMMA +
	        	change_code(pl_status[0]) + STR_COMMA +
	        	change_code(pl_status[1]) + STR_COMMA +
	        	change_code(pl_status[2]) + STR_COMMA +
	        	change_code(pl_status[3]) + STR_COMMA +
	        	change_code(pl_status[4]) + STR_COMMA +
	        	change_code(pl_status[5]) + STR_COMMA +
	        	change_code(pl_status[6]) + STR_COMMA +
	        	change_code(pl_status[7]) + STR_COMMA +
	        	change_code(pl_status[8]) + STR_COMMA +
	        	change_code(pl_status[9]) + STR_COMMA +
	        	change_code(pl_efficacy[0][0])+ STR_COMMA +
	        	change_code(pl_efficacy[0][1])+ STR_COMMA +
	        	change_code(pl_efficacy[1][0])+ STR_COMMA +
	        	change_code(pl_efficacy[1][1])+ STR_COMMA +
	        	change_code(pl_efficacy[2][0])+ STR_COMMA +
	        	change_code(pl_efficacy[2][1])+ STR_COMMA +
	        	change_code(pl_efficacy[3][0])+ STR_COMMA +
	        	change_code(pl_efficacy[3][1])+ STR_COMMA +
	        	change_code(pl_efficacy[4][0])+ STR_COMMA +
	        	change_code(pl_efficacy[4][1])+ STR_COMMA +
	        	change_code(pl_efficacy[5][0])+ STR_COMMA +
	        	change_code(pl_efficacy[5][1])+ STR_COMMA +
	        	change_code(pl_efficacy[6][0])+ STR_COMMA +
	        	change_code(pl_efficacy[6][1])+ STR_COMMA +
	        	change_code(pl_efficacy[7][0])+ STR_COMMA +
	        	change_code(pl_efficacy[7][1])+ STR_COMMA +
	        	change_code(pl_efficacy[8][0])+ STR_COMMA +
	        	change_code(pl_efficacy[8][1])+ STR_COMMA +
	        	change_code(pl_skil[0][0])+ STR_COMMA +
	        	change_code(pl_skil[0][1])+ STR_COMMA +
	        	change_code(pl_skil[0][2])+ STR_COMMA +
	        	change_code(pl_skil[0][3])+ STR_COMMA +
	        	change_code(pl_skil[0][4])+ STR_COMMA +
	        	change_code(pl_skil[0][5])+ STR_COMMA +
	        	change_code(pl_skil[0][6])+ STR_COMMA +
	        	change_code(pl_skil[0][7])+ STR_COMMA +
	        	change_code(pl_skil[1][0])+ STR_COMMA +
	        	change_code(pl_skil[1][1])+ STR_COMMA +
	        	change_code(pl_skil[1][2])+ STR_COMMA +
	        	change_code(pl_skil[1][3])+ STR_COMMA +
	        	change_code(pl_skil[1][4])+ STR_COMMA +
	        	change_code(pl_skil[1][5])+ STR_COMMA +
	        	change_code(pl_skil[1][6])+ STR_COMMA +
	        	change_code(pl_skil[1][7])+ STR_COMMA +
	        	change_code(pl_skil[2][0])+ STR_COMMA +
	        	change_code(pl_skil[2][1])+ STR_COMMA +
	        	change_code(pl_skil[2][2])+ STR_COMMA +
	        	change_code(pl_skil[2][3])+ STR_COMMA +
	        	change_code(pl_skil[2][4])+ STR_COMMA +
	        	change_code(pl_skil[2][5])+ STR_COMMA +
	        	change_code(pl_skil[2][6])+ STR_COMMA +
	        	change_code(pl_skil[2][7])+ STR_COMMA +
	        	change_code(pl_skil[3][0])+ STR_COMMA +
	        	change_code(pl_skil[3][1])+ STR_COMMA +
	        	change_code(pl_skil[3][2])+ STR_COMMA +
	        	change_code(pl_skil[3][3])+ STR_COMMA +
	        	change_code(pl_skil[3][4])+ STR_COMMA +
	        	change_code(pl_skil[3][5])+ STR_COMMA +
	        	change_code(pl_skil[3][6])+ STR_COMMA +
	        	change_code(pl_skil[3][7])+ STR_COMMA +
	        	pl_belonging[0] + STR_COMMA +
	        	pl_belonging[1] + STR_COMMA +
	        	pl_belonging[2] + STR_COMMA +
	        	pl_belonging[3] + STR_COMMA +
	        	pl_belonging[4] + STR_COMMA +
	        	pl_belonging[5] + STR_COMMA +
	        	pl_belonging[6] + STR_COMMA +
	        	pl_belonging[7] + STR_COMMA +
	        	pl_belonging[8] + STR_COMMA +
	        	pl_belonging[9] + STR_COMMA +
	        	pl_belonging[10] + STR_COMMA +
	        	pl_belonging[11] + STR_COMMA +
	        	pl_belonging[12] + STR_COMMA +
	        	pl_belonging[13] + STR_COMMA +
	        	pl_belonging[14] + STR_COMMA +
	        	pl_belonging[15] + STR_COMMA +
	        	pl_belonging[16] + STR_COMMA +
	        	pl_belonging[17] + STR_COMMA +
	        	pl_belonging[18] + STR_COMMA +
	        	pl_belonging[19] + STR_COMMA +
	        	change_code(pl_arsenal[0])+ STR_COMMA +
	        	change_code(pl_arsenal[1])+ STR_COMMA +
	        	change_code(pl_arsenal[2])+ STR_COMMA +
	        	change_code(pl_arsenal[3])+ STR_COMMA +
	        	change_code(pl_arsenal[4])+ STR_COMMA +
	        	change_code(pl_arsenal[5]);
	        ret = writeln(buf); 
	        if (ret == false) {
	            close();
	            System.out.println("プログラムを異常終了します");
	            System.exit(1);
	        }
	        ret = close();
	        if (ret == false) {
	            System.out.println("プログラムを異常終了します");
	            System.exit(1);
	        }
	        if(Auto_save == false) {
	        	System.out.print("status data を保存しました。");
	        }
	        
		}
		return null;
	}
	/*******************************************************************************
	 * ファイルを開くプログラム
	 *******************************************************************************/
	public boolean open(String fname) {
        boolean sts = true;
        try {
            bw  = new BufferedWriter(new FileWriter(fname));
        } catch (IOException e) {
            System.out.println("ファイル名に誤りがあります\n" + e);
            sts = false;
        }
        return sts;
    }
    /*******************************************************************************
     * ファイルへのデータ書き込むプログラム
     *******************************************************************************/
	public boolean writeln(String str) {
        boolean sts = true;
        try {
            bw.write(str);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("書き込みエラー\n" + e);
            sts = false;
        }
        return sts;
    }
    /*******************************************************************************
     * ファイルを閉じるプログラム
     *******************************************************************************/
	public boolean close(){
        boolean sts = true;
        try {
            bw.close();
        } catch (IOException e) {
            System.out.println("ファイルクローズエラー\n" + e);
            sts = false;
        }
        return sts;
    }
    /*******************************************************************************
     * save data　を読み込むプログラム
     *******************************************************************************/
	data_save data_read (String name) throws IOException {
		data_save output = new data_save();
    	
    	String[] save_pl_name = {"no","no",};
		int[] save_pl_status = new int[10];
		int[][] save_pl_efficacy = new int[9][2];
		int[][] save_pl_skil = new int[4][8];
		String[] save_pl_belonging = new String[20];
		int[] save_pl_arsenal = new int[6];
		
		for(int n = 0;n < 12;n++) {
			save_pl_belonging[n] = "no";
		}
		
		BufferedReader br = null;
		
		String[] data = new String[100]; 
		//データを読み込む
		try {
			File file = new File("save_data.csv");
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				data = line.split(",");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				br.close();
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}if(name.equals(data[0])) {
			int enter = 0;
			do {
				System.out.print("前回のセーブデータを使用しますか？ 《y or n 入力》");
				BufferedReader bu =
						new BufferedReader(new InputStreamReader(System.in));
				String save = bu.readLine();
				
				if(save.equals("y")){
					int n = 0;
					/* プレイヤーの名前とクラス */
					for (int i = 0;i < save_pl_name.length;i++) {
						save_pl_name[i] = data[n];
						n++;
					}
					/* ステータス */
					for (int i = 0;i < save_pl_status.length;i++) {
						save_pl_status[i] = change_num(data[n]);
						n++;
					}
					/* 効果 */
					for (int i = 0;i < save_pl_efficacy.length;i++) {
						for (int j = 0;j < save_pl_efficacy[i].length;j++) {
							save_pl_efficacy[i][j] = change_num(data[n]);
							n++;
						}
					}
					/* スキル */
					for (int i = 0;i < save_pl_skil.length;i++) {
						for (int j = 0;j < save_pl_skil[i].length;j++) {
							save_pl_skil[i][j] = change_num(data[n]);
							n++;
						}
					}
					/* 持ち物 */
					for (int i = 0;i < save_pl_belonging.length;i++) {
						save_pl_belonging[i] = data[n];
						n++;
						
					}
					/* 素材 */
					for (int i = 0;i < save_pl_arsenal.length;i++) {
						save_pl_arsenal[i] = change_num(data[n]);
						n++;
					}
					
					enter = 1;
				}else if(save.equals("n")) {
					save_pl_name[0] = name;
					enter = 1;
				}else {
					System.out.println("適切でない文字を入力しています。\nヒント　半角、小文字で入力していますか？\n");
				}
			}while(enter == 0);
		}else {
			save_pl_name[0] = name;
		}
		output.player_name = save_pl_name;
		output.player_status = save_pl_status;
		output.player_efficacy = save_pl_efficacy;
		output.player_skil = save_pl_skil;
		output.player_belonging = save_pl_belonging;
		output.player_arsenal = save_pl_arsenal;
		return output;
	}
    /*******************************************************************************
     * 数字、英文字関連
     * 36進数の作成
     *******************************************************************************/
    static String[][] base_number = {
    		{"0","0",},{"1","1",},{"2","2",},{"3","3",},{"4","4",},{"5","5",},
    		{"6","6",},{"7","7",},{"8","8",},{"9","9",},{"10","A",},{"11","B",},
    		{"12","C",},{"13","D",},{"14","E",},{"15","F",},{"16","G",},{"17","H",},
    		{"18","I",},{"19","J",},{"20","K",},{"21","L",},{"22","M",},{"23","N",},
    		{"24","O",},{"25","P",},{"26","Q",},{"27","R",},{"28","S",},{"29","T",},
    		{"30","U",},{"31","V",},{"32","W",},{"33","X",},{"34","Y",},{"35","Z",},
    };
    
    /*******************************************************************************
     * 数字を英文字に変えるプログラム
     *******************************************************************************/
    public static String change_code(int num) {
    	String code = null;				//英文字
    	String[] conversion_code = {"0","0","0","0","0","0",};	//変換時に使う
    	
		int amari = 0;
		int count = 0;
		String rupe = null;
		if(num <= 0) {
			num = 0;
		}
    	num = 2147483647 - num;
    	
    	while(rupe == null) {
			amari = num%36;
			if(amari == 0) {
				conversion_code[count] = "0";
			}else {
				conversion_code[count] = Integer.toString(amari);
			}
			num = num/36;
			count++;
			if(num <= 0) {
				rupe = "okok";
			}
		}
    	for(int i = 0;i < conversion_code.length;i++) {
    		for(int j = 0;j < 36;j++) {
    			if(conversion_code[i].equals(base_number[j][0])) {
    				conversion_code[i] = base_number[j][1];
    			}
    		}
    	}
    	code = conversion_code[0]+conversion_code[1]+conversion_code[2]+
    			conversion_code[3]+conversion_code[4]+conversion_code[5];
    	
    	return code;
    }
    /*******************************************************************************
     * 英文字を数字に変えるプログラム
     *******************************************************************************/
    public static int change_num(String code) {
    	int num = 0;
    	String[] conversion_code = new String[6];
    	int [] jyousu = {1,36,1296,46656,1679616,60466176};
    	
    	for(int i = 0;i < 6;i++) {
    		conversion_code[i] = code.substring(i,(i+1));
    	}
    	
    	for(int i = 0;i < conversion_code.length;i++) {
    		for(int j = 0;j < 36;j++) {
    			if(code.substring(i,(i+1)).equals(base_number[j][1])) {
    				conversion_code[i] = base_number[j][0];
    			}
    		}
    	}
		for (int i = 0;i < 6;i++) {
			num+=Integer.parseInt(conversion_code[i])*jyousu[i];
		}
		num = 2147483647-num;
    	return num;
    }
}


