import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class admin {
	String[] player_name;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	int[] player_arsenal;
	String[][][] main_map;
	
	public admin Authority(String[] pl_name, int[] pl_status, int[][] pl_efficacy,
			int[][] pl_skil, String[] pl_belonging, int[] pl_arsenal) throws IOException {
		admin output = new admin();
		to_edit te = new to_edit();
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String code = null;
		String repeat = "y";
		
		code = br.readLine();
		
		if(code.equals("admin")) {
			while(repeat.equals("y")) {
				System.out.println("Please enter the location to edit.(First 3 letters)>>>");
				code = br.readLine();
				
				switch(code) {
				case "nam":
					to_edit nam = te.name(pl_name);
					pl_name = nam.player_name;
					break;
				case "sta":
					to_edit sta = te.status(pl_status, pl_name);
					pl_status = sta.player_status;
					break;
				case "ski":
					to_edit ski = te.skil(pl_skil);
					pl_skil = ski.player_skil;
					break;
				case "bel":
					to_edit bel = te.belonging(pl_belonging);
					pl_belonging = bel.player_belonging;
					break;
				case "ars":
					to_edit ars = te.arsenal(pl_arsenal);
					pl_arsenal = ars.player_arsenal;
					break;
				case "map":
					to_edit map = te.map(main_map);
					main_map = map.main_map;
				}
				
				System.out.print("Do you want to continue using administrator privileges? (y or n) >>>");
				repeat = br.readLine();
			}
		}
		
		output.player_name = pl_name;
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_skil = pl_skil;
		output.player_belonging = pl_belonging;
		output.player_arsenal = pl_arsenal;
		return output;
	}
}
class to_edit{
	String[] player_name;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	int[] player_arsenal;
	String[][][] main_map;
	/********************************************************************************
	 * name
	 ********************************************************************************/
	public to_edit name(String[] pl_name) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String[] select_name = {
				"剣士","フェンサー","ディフェンダー","トラベラー","貴族",
		};
		String code = null;
		String repeat = "y";
		int num = 0;
		
		while(repeat.equals("y")) {
			main.on_display_name(pl_name);
			
			System.out.print("Please enter the item to edit. "
					+ "name or class >>>");
			code = br.readLine();
			
			switch(code) {
			case "name":
				System.out.print("Please enter the letter. >>>");
				pl_name[0] = br.readLine();
				break;
			case "class":
				while(true) {
					String print = "Please enter the item to edit.\n"
							+ "[1] 剣士  [2] フェンサー  [3] ディフェンダー  [4] トラベラー  [5] 貴族 >>>";
					num = enter_number.method(print);
					if(num <= 5 && num > 0) {
						break;
					}
				}
				num -= 1;
				pl_name[1] = select_name[num];
				break;
			}
			
			System.out.print("Do you want to continue editing the name? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_name = pl_name;
		return sta_output;
	}
	/********************************************************************************
	 * status
	 ********************************************************************************/
	public to_edit status(int[] pl_status, String[] pl_name) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String code = null;
		String repeat = "y";
		int value;
		boolean edit = true;
		
		while(repeat.equals("y")) {
			main.on_display_status(pl_status, pl_name[0]);
			int place = -1;
			System.out.print("Please enter the item to edit.\n"
					+ "Lv or HP or STR or AGI or VIT or Gold or record>>>");
			
			code = br.readLine();
			
			switch(code) {
			case "Lv":
				place = 0;
				break;
			case "HP":
				place = 3;
				break;
			case "STR":
				place = 5;
				break;
			case "AGI":
				place = 6;
				break;
			case "VIT":
				place = 7;
				break;
			case "Gold":
				place = 8;
				break;
			case "record":
				place = 9;
				break;
			default:
				edit = false;
			}
			
			if(edit == true) {
				String print = "Please enter the number.>>>";
				value = enter_number.method(print);
				pl_status[place] = value;
				if(place == 3) {
					pl_status[4] = value;
				}else if(place == 9){
					if(value >= 49){
						pl_status[place] = 49;
					}
				}
			}
			System.out.print("Do you want to continue editing the status? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_status = pl_status;
		return sta_output;
	}
	/********************************************************************************
	 * skil
	 ********************************************************************************/
	public to_edit skil(int[][] pl_skil) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String[] all_skil_name = {
				"スラント","カタラクト","バーチカル",
				"バーチカル・アーク","ソニックリーブ","シャープネイル",
				"トライアンギュラー","ニュートロン","クルーシフィクション",		
				"メテオブレイク","スター・スプラッシュ","ノヴァ・アセンション",
				"マザーズ・ロザリオ","スターバースト・ストリーム",
		};
		int[][] all_skil_status = {
				{0,0,10,60,1,1,0,6},
				{1,0,15,50,2,5,0,12},
				{2,0,20,100,1,15,0,12},
				
				{3,0,25,80,2,25,0,15},
				{4,0,25,180,1,35,0,9},
				{5,0,30,90,3,45,0,15},
				
				{6,0,40,150,3,55,0,18},
				{7,0,25,170,5,65,0,21},
				{8,0,55,200,6,75,0,24},
				
				{9,0,45,280,7,85,0,27},
				{10,0,35,360,8,95,0,30},
				{11,0,50,450,10,105,0,30},
				
				{12,0,55,600,11,150,0,999},
				{13,0,60,650,16,150,0,999},
		};
		
		String repeat = "y";
		
		while(repeat.equals("y")) {
			int count = 0;
			int[][] count_id = new int[14][1];
			int num = 0;		//修得するスキル番号
			int num2 = 0;		//入れ替え先のスキル番号
			boolean redo = true;
			boolean redo2 = true;
			boolean skil_max = true;
			
			main.on_display_skil(pl_skil);
			System.out.println("all_skil_name");
			for(int n = 0;n < all_skil_name.length;n++) {
				boolean have = false;
				for(int m = 0;m < 4;m++) {
					if(all_skil_status[n][0] == pl_skil[m][0]) {		//表示スキルがすでに所持している場合
						have = true;
					}
				}
				if(have == false) {
					count++;
					System.out.println("[" + count + "]\t" + all_skil_name[n] +"\n\t威力_"
							+ all_skil_status[n][3] + "\t最大クールタイム_" + all_skil_status[n][2] 
							+ "\t最大連撃数_" + all_skil_status[n][4]);
					count_id[count][0] = all_skil_status[n][0];
				}
			}
			while(true) {
				String print = "Enter the number of the skil you want to acquire.《0=Cancel》>>>";
				num = enter_number.method(print);
				if(num >= 0 && num <= count) {
					break;
				}else {
					System.out.println("Re-enter");
				}
			}
			if(num != 0) {
				for(int m = 0;m < 4;m++) {
					if(pl_skil[m][3] == 0) {
						pl_skil[m] = all_skil_status[count_id[num][0]];
						pl_skil[m][5] = 1;
						skil_max = false;
						break;
					}
				}
				if(skil_max == true) {
					System.out.println("The frame is full");
					count = 0;
					for(int m = 0;m < 4;m++) {
						count++;
						System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t威力_"
								+ pl_skil[m][3] + "\t最大クールタイム_" + pl_skil[m][2] 
								+ "\t最大連撃数_" + pl_skil[m][4] + "\t熟練度_" + pl_skil[m][5]);
					}
					while(redo2) {
						String print2 = "Please enter the my skil number to be exchanged.《0=Cancel》>>>";
						num2 = enter_number.method(print2);
						if(num2 >= 0 && num2 <= 4) {
							redo2 = false;
						}else {
							System.out.println("Re-enter");
						}
					}
					if(num2 != 0) {
						pl_skil[num2-1] = all_skil_status[count_id[num][0]];
						pl_skil[num2-1][5] = 1;
					}
				}
			}
			
			System.out.print("Do you want to continue editing the skil? (y or n)>>>");
			repeat = br.readLine();
		}
		
		sta_output.player_skil = pl_skil;
		return sta_output;
	}
	/********************************************************************************
	 * belonging
	 ********************************************************************************/
	public to_edit belonging(String[] pl_belonging) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String[][] item_all_name = {
				{"00","ポーション\t","HPを30回復",},
				{"01","ハイポーション","HPを50回復",},
				{"02","エストポーション","HPを100回復",},
				{"03","グランポーション","HPを250回復",},
				{"04","STRポーション","一定時間の間STRを50増加",},
				{"05","AGIポーション","一定時間の間AGIを30増加",},
				{"06","VITポーション","一定時間の間VITを30増加",},
				{"07","治癒結晶\t","最大HPの30％回復",},
				{"08","回復結晶\t","最大HPの60％回復",},
				{"09","全快結晶\t","HPを100％回復",},
				{"10","剣撃結晶\t","一定時間の間STRが2倍",},
				{"11","新速結晶\t","一定時間の間AGIが2倍",},
				{"12","絶守結晶\t","一定時間の間VITが2倍",},
				{"13","鉄鉱石\t","鉄鉱石1個分",},
				{"14","鉄鉱石\t","鉄鉱石3個分",},
				{"15","鉄鉱石\t","鉄鉱石5個分",},
				{"16","白銀鉱石\t","白銀鉱石1個分",},
				{"17","白銀鉱石\t","白銀鉱石3個分",},
				{"18","白銀鉱石\t","白銀鉱石5個分",},
				{"19","プラチム鉱石","プラチム鉱石1個分",},
				{"20","プラチム鉱石","プラチム鉱石3個分",},
				{"21","プラチム鉱石","プラチム鉱石5個分",},
				{"22","黄金石\t","黄金石1個分",},
				{"23","黄金石\t","黄金石3個分",},
				{"24","黄金石\t","黄金石5個分",},
				{"25","HPポーション","一定時間の間HPを80増加",},
				{"26","強堅結晶\t","一定時間の間HPが2倍",},
				{"27","剣撃結晶ー改","一定時間の間STRが3倍",},
				{"28","新速結晶ー改","一定時間の間AGIが3倍",},
				{"29","絶守結晶ー改","一定時間の間VITが3倍",},
				{"30","強堅結晶ー改","一定時間の間HPが3倍",},
		};
		
		String code = null;
		String repeat = "y";
		int value;
		boolean edit = true;
		int item_num;
		boolean belonging_max = true;
		
		while(repeat.equals("y")) {
			
			System.out.println("Show your belongings.");
			main.on_display_item(pl_belonging);
			
			System.out.println("Show all_item belongings.");
			for(int a = 0;a < item_all_name.length;a++) {
				System.out.println("["+(a+1)+"]\t"+item_all_name[a][1]+"\t: "+item_all_name[a][2]);
			}
			
			while(true) {
				String print = "Please enter the item number.《0=Cancel》>>>";
				item_num = enter_number.method(print);
				if(item_num >= 0 && item_num <= 31) {
					break;
				}
			}
			item_num -= 1;
			
			if(item_num != -1) {
				for(int n = 0;n < pl_belonging.length;n++) {
					if(pl_belonging[n].equals("no")) {
						pl_belonging[n] = item_all_name[item_num][0];
						belonging_max = false;
						break;
					}
				}
				if(belonging_max == true) {
					System.out.println("Item could not be obtained.");
				}
			}
			
			System.out.print("Do you want to continue editing the belonging? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_belonging = pl_belonging;
		return sta_output;
	}
	/********************************************************************************
	 * arsenal
	 ********************************************************************************/
	public to_edit arsenal(int[] pl_arsenal) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String repeat = "y";
		
		int num = 0;
		boolean edit = true;
		
		while(repeat.equals("y")) {
			main.on_display_arsenal(pl_arsenal);
			while(true) {
				String print = "Please enter the item to edit.\n"
						+ "[1] 鉄鉱石  [2] 白銀鉱石  [3] プラチム鉱石  [4] 黄金石 >>>";
				num = enter_number.method(print);
				if(num <= 4 && num > 0) {
					break;
				}
			}
			String print = "Please enter the number you want to change. >>>";
			int value = enter_number.method(print);
			
			switch (num) {
			case 1:
				pl_arsenal[1] = value;
				break;
			case 2:
				pl_arsenal[2] = value;
				break;
			case 3:
				pl_arsenal[3] = value;
				break;
			case 4:
				pl_arsenal[4] = value;
				break;
			}
			
			System.out.print("Do you want to continue editing the arsenal? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_arsenal = pl_arsenal;
		return sta_output;
	}
	/*************************************************************************************
	 * map
	 *************************************************************************************/
	public to_edit map(String[][][] map) throws IOException {
		to_edit map_output = new to_edit();
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String repeat = "y";
		String choose = "n";
		
		while(repeat.equals("y")) {
			if(map[0][6][2].equals(" ")) {
				System.out.print("Do you want to display the administrator function of the main map? (y or n) >>>");
				choose = br.readLine();
				if(choose.equals("y")) {
					map[0][6][2] = "!";
				}
			}else {
				System.out.print("Do you want to hide the admin function of the main map? (y or n) >>>");
				choose = br.readLine();
				if(choose.equals("y")) {
					map[0][6][2] = " ";
				}
			}
			
			System.out.print("Do you want to continue editing the map? (y or n) >>>");
			repeat = br.readLine();
		}
		map_output.main_map = map;
		return map_output;
	}
}
