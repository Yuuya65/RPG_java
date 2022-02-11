import java.io.IOException;
import java.util.Random;

class skil {
	int[] player_status;
	int[][] player_skil;
	int total_damage;
	/* [スキル番号][0…スキル番号、1…クールタイム、2…最大クールタイム、3…威力、4…最大コンボ数、5…取得レベル]*/
	
	//[][0…スキル番号、1…クールタイム、2…最大クール―タイム、3…威力、4…最大コンボ数、5…熟練度]
	
	/***********************************************************************************
	 * 攻撃ターン時のプログラム
	 ***********************************************************************************/
	public skil skil_damage (int[] pl_status, int[][] pl_skil ,int use_skil) {
		skil sd_output = new skil();
		
		Random rand = new Random();
		
		/* total_damageを計算する */
		int total_damage;
		int combo;
		int ran = rand.nextInt(100) + 20;
		
		combo = (ran % pl_skil[use_skil][4]) + 1;
		total_damage = (pl_skil[use_skil][3] + pl_status[5] / 2) * combo;
		
		System.out.println(combo + "連撃　ヒット！");
		
		/* 熟練度を上げる */
		pl_skil[use_skil][5]++;
		if(pl_skil[use_skil][5] % 5 == 0) {
			pl_skil[use_skil][3] += 3;
		}
		
		/* クールタイムを減らす */
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][0] != -1) {		//スキル欄にスキルが入っているなら
				pl_skil[n][1] -= 5;
				if(pl_skil[n][1] <= 0) {
					pl_skil[n][1] = 0;
				}
			}
		}
		
		/* 使用したスキルのクールタイムを最大値に戻す */
		pl_skil[use_skil][1] = pl_skil[use_skil][2];
		
		sd_output.player_skil = pl_skil;
		sd_output.total_damage = total_damage;
		return sd_output;
	}
	/***********************************************************************************
	 * 通常攻撃時のプログラム
	 ***********************************************************************************/
	public skil normal_damage (int[][] pl_skil) {
		skil nd_output = new skil();
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][0] != -1) {		//スキル欄にスキルが入っているなら
				pl_skil[n][1] -= 5;
				if(pl_skil[n][1] <= 0) {
					pl_skil[n][1] = 0;
				}
			}
		}
		nd_output.player_skil = pl_skil;
		return nd_output;
	}
	/*******************************************************************************
	 * 移動時のプログラム
	 *******************************************************************************/
	public skil move (int[][] pl_skil, int hosei) {
		skil m_output = new skil();
		
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][0] != -1) {		//スキル欄にスキルが入っているなら
				pl_skil[n][1] -= 2 * hosei;
				if(pl_skil[n][1] <= 0) {
					pl_skil[n][1] = 0;
				}
			}
		}
		
		m_output.player_skil = pl_skil;
		return m_output;
	}
	/*******************************************************************************
	 * スキル修得のプログラム
	 *******************************************************************************/
	public skil Acquisition_skil (int[] pl_status, int[][] pl_skil) throws IOException {
		skil as_output = new skil();
		
		/* スキル一覧 */
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
				
				{12,0,20,700,11,0,0,999},
				{13,0,45,950,20,0,0,999},
		};
		/* [スキル番号][0…スキル番号、1…クールタイム、2…最大クールタイム、3…威力、
		 	4…最大コンボ数、5…取得レベル、6…強化回数、7…強化上限数] */
		
		int lv = pl_status[0];
		int count = 0;
		int[][] count_id = new int[14][1];
		int num = 0;		//修得するスキル番号
		int num2 = 0;		//入れ替え先のスキル番号
		boolean Acquisition = false;
		boolean redo = true;
		boolean redo2 = true;
		boolean skil_max = true;
		
		System.out.println("修得可能なスキル");
		
		for(int n = 0;n < all_skil_status.length;n++) {
			if(all_skil_status[n][5] <= lv) {
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
							+ "\t最大連撃数_" + all_skil_status[n][4] + "\t強化上限数_" + all_skil_status[n][7]);
					count_id[count][0] = all_skil_status[n][0];
				}
			}
		}
		if(count > 0) {
			while(redo) {
				String print = "修得したいスキルの番号を入力してください。《0入力でキャンセル》>>>";
				num = enter_number.method(print);
				if(num >= 0 && num <= count) {
					redo = false;
				}else {
					System.out.println("再入力");
				}
			}
			if(num != 0) {
				for(int m = 0;m < 4;m++) {
					if(pl_skil[m][3] == 0) {
						pl_skil[m] = all_skil_status[count_id[num][0]];
						pl_skil[m][5] = 1;
						skil_max = false;
						Acquisition = true;
						break;
					}
				}
				if(skil_max == true) {
					System.out.println("所持スキル枠がいっぱいです");
					count = 0;
					for(int m = 0;m < 4;m++) {
						count++;
						System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t威力_"
								+ pl_skil[m][3] + "\t最大クールタイム_" + pl_skil[m][2] 
								+ "\t最大連撃数_" + pl_skil[m][4] + "\t熟練度_" + pl_skil[m][5]);
					}
					while(redo2) {
						String print2 = "入れ替えるスキル番号を入力してください。《0入力でキャンセル》>>>";
						num2 = enter_number.method(print2);
						if(num2 >= 0 && num2 <= 4) {
							redo2 = false;
						}else {
							System.out.println("再入力");
						}
					}
					if(num2 != 0) {
						pl_skil[num2-1] = all_skil_status[count_id[num][0]];
						pl_skil[num2-1][5] = 1;
						Acquisition = true;
					}else {
						System.out.println("スキルの修得をキャンセルしました");
					}
				}
			}else {
				System.out.println("スキルの修得をキャンセルしました");
			}	
		}else {
			System.out.println("修得できるスキルはありません");
		}
		if(Acquisition == true) {
			pl_status[8] -= 1500;
			System.out.println("スキル『"+ all_skil_name[count_id[num][0]] +"』を修得しました\n"
					+ "1500Gold使用しました");
		}
		
		as_output.player_status = pl_status;
		as_output.player_skil = pl_skil;
		return as_output;
	}
	/*******************************************************************************
	 * 限定スキル修得のプログラム
	 *******************************************************************************/
	public skil Limited_skil (int[][] pl_skil) throws IOException {
		skil ls_output = new skil();
		
		/* スキル一覧 */
		String[] all_skil_name = {
				"スラント","カタラクト","バーチカル",
				"バーチカル・アーク","ソニックリーブ","シャープネイル",
				"トライアンギュラー","ニュートロン","クルーシフィクション",		
				"メテオブレイク","スター・スプラッシュ","ノヴァ・アセンション",
				"マザーズ・ロザリオ","スターバースト・ストリーム",
		};
		int[][] all_skil_status = {
				{0,0,10,60,1,1,0,4},
				{1,0,15,50,2,5,0,7},
				{2,0,20,100,1,15,0,12},
				
				{3,0,25,80,2,25,0,15},
				{4,0,25,180,1,35,0,9},
				{5,0,30,90,3,45,0,18},
				
				{6,0,40,150,3,55,0,24},
				{7,0,25,170,5,65,0,21},
				{8,0,55,200,6,75,0,30},
				
				{9,0,45,280,7,85,0,27},
				{10,0,35,360,8,95,0,30},
				{11,0,50,450,10,105,0,36},
				
				{12,0,20,700,11,0,0,999},
				{13,0,45,950,20,0,0,999},
		};
		String[] Limited_skil_name = {
				"マザーズ・ロザリオ","スターバースト・ストリーム",
		};
		int[][] Limited_skil_status = {
				{12,0,20,700,11,0,0,999},
				{13,0,45,950,20,0,0,999},
		};
		/* [スキル番号][0…スキル番号、1…クールタイム、2…最大クールタイム、3…威力、
		 	4…最大コンボ数、5…取得レベル、6…強化回数、7…強化上限数] */
		
		int count = 0;
		int[][] count_id = new int[14][1];
		int num = 0;		//修得するスキル番号
		int num2 = 0;		//入れ替え先のスキル番号
		boolean Acquisition = false;
		boolean redo = true;
		boolean redo2 = true;
		boolean skil_max = true;
		boolean[] Possible = {false,false};
		
		System.out.println("修得可能なスキル一覧");
		for(int n = 0;n < Limited_skil_status.length;n++) {
			String Unacquired = "修得可能";
			for(int m = 0;m < pl_skil.length;m++) {
				if(Limited_skil_status[n][0] == pl_skil[m][0]){
					Unacquired = "既に修得しています";
					Possible[n] = true;
				}
			}
			System.out.println("[" + (n+1) + "]\t" + Limited_skil_name[n] +"\n\t威力_"
					+ Limited_skil_status[n][3] + "\t最大クールタイム_" + Limited_skil_status[n][2] 
					+ "\t最大連撃数_" + Limited_skil_status[n][4] + "\t強化上限数_" 
					+ Limited_skil_status[n][7] + "\t" + Unacquired);
		}
		if(Possible[0] == false || Possible[1] == false) {
			while(redo) {
				String print = "修得したいスキルの番号を入力してください。《0入力でキャンセル》>>>";
				num = enter_number.method(print);
				if(num >= 0 && num <= 2) {
					if(Possible[num-1] == false) {
						redo = false;
					}else {
						System.out.println("すでに修得しています");
					}
				}else {
					System.out.println("再入力");
				}
			}
			if(num != 0) {
				num -= 1;
				for(int m = 0;m < 4;m++) {
					if(pl_skil[m][3] == 0) {
						pl_skil[m] = Limited_skil_status[num];
						pl_skil[m][5] = 1;
						skil_max = false;
						Acquisition = true;
						break;
					}
				}
				if(skil_max == true) {
					System.out.println("所持スキル枠がいっぱいです");
					count = 0;
					for(int m = 0;m < 4;m++) {
						count++;
						System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t威力_"
								+ pl_skil[m][3] + "\t最大クールタイム_" + pl_skil[m][2] 
								+ "\t最大連撃数_" + pl_skil[m][4] + "\t熟練度_" + pl_skil[m][5]);
					}
					while(redo2) {
						String print2 = "入れ替えるスキル番号を入力してください。《0入力でキャンセル》>>>";
						num2 = enter_number.method(print2);
						if(num2 >= 0 && num2 <= 4) {
							redo2 = false;
						}else {
							System.out.println("再入力");
						}
					}
					if(num2 != 0) {
						pl_skil[num2-1] = Limited_skil_status[num];
						pl_skil[num2-1][5] = 1;
						Acquisition = true;
					}else {
						System.out.println("スキルの修得をキャンセルしました");
					}
				}
			}else {
				System.out.println("スキルの修得をキャンセルしました");
			}
			if(Acquisition == true) {
				System.out.println("スキル『"+ Limited_skil_name[num] +"』を修得しました");
			}
		}else {
			System.out.println("修得可能なスキルがありません。所持しているスキルの熟練度を上げます");
			count = 0;
			for(int m = 0;m < 4;m++) {
				if(pl_skil[m][4] != 0) {
					count++;
					System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t威力_"
							+ pl_skil[m][3] + "\t最大クールタイム_" + pl_skil[m][2] 
							+ "\t最大連撃数_" + pl_skil[m][4] + "\t熟練度_" + pl_skil[m][5]);
				}	
			}
			while(redo) {
				String print = "スキルの番号を入力してください。>>>";
				num = enter_number.method(print);
				if(num > 0 && num <= count) {
					redo = false;
				}else {
					System.out.println("再入力");
				}
			}
			pl_skil[num-1][5] += 50;
			System.out.println(all_skil_name[pl_skil[num-1][0]] + "の熟練度を50上げました");
		}
		
		
		ls_output.player_skil = pl_skil;
		return ls_output;
	}
	
	/*******************************************************************************
	 * スキル強化のプログラム
	 *******************************************************************************/
	public skil skil_up (int[] pl_status, int[][] pl_skil) throws IOException {
		skil ssu_output = new skil();
		
		String[] all_skil_name = {
				"スラント","カタラクト","バーチカル",
				"バーチカル・アーク","ソニックリーブ","シャープネイル",
				"トライアンギュラー","ニュートロン","クルーシフィクション",		
				"メテオブレイク","スター・スプラッシュ","ノヴァ・アセンション",
				"マザーズ・ロザリオ","スターバースト・ストリーム",
		};
		boolean[] count_place = new boolean[4];
		int count = 0;
		int num_1 = 0;
		int num_2 = 0;
		boolean success = false;
		for(int n = 0;n < count_place.length;n++) {
			count_place[n] = false;
		}
		
		System.out.println("熟練度が20を超えるごとにスキルの強化を行なえます\n修得しているスキルを表示します");
		
		for(int m = 0;m < 4;m++) {
			if(pl_skil[m][5] / 20 > pl_skil[m][6] && pl_skil[m][6] != pl_skil[m][7] && pl_skil[m][3] != 0) {
				System.out.println("[" + (m + 1) + "]\t" + all_skil_name[pl_skil[m][0]] +"\n"
						+ "\t威力_" + pl_skil[m][3] + "\t最大クールタイム_" + pl_skil[m][2] 
						+ "\t最大連撃数_" + pl_skil[m][4] + "\t熟練度_" + pl_skil[m][5] + "\n"
						+ "\t強化回数_" + pl_skil[m][6] + "\t強化上限数_" + pl_skil[m][7] + "\t強化可能");
				count ++;
				count_place[m] = true;
			}else if(pl_skil[m][3] != 0){
				System.out.println("[" + (m + 1) + "]\t" + all_skil_name[pl_skil[m][0]] +"\n"
						+ "\t威力_" + pl_skil[m][3] + "\t最大クールタイム_" + pl_skil[m][2] 
						+ "\t最大連撃数_" + pl_skil[m][4] + "\t熟練度_" + pl_skil[m][5] + "\n"
						+ "\t強化回数_" + pl_skil[m][6] + "\t強化上限数_" + pl_skil[m][7] + "\t強化できません");
			}
		}
		if(count == 0) {
			System.out.println("強化できるスキルはありません");
		}else {
			while(true) {
				String print_1 = "強化するスキルを選んでください。《0入力でキャンセル》>>>";
				num_1 = enter_number.method(print_1);
				if(num_1 > 0 && num_1 <= 4 && count_place[num_1 - 1] == true || num_1 == 0) {
					break;
				}
			}
			if(num_1 == 0) {
				System.out.println("スキル強化をキャンセルしました");
			}else {
				num_1 -= 1;
				while(true) {
					String print_2 = "どれを強化しますか？《0入力でキャンセル》>>>\n"
							+ "[1]\tクールタイム減少\n[2]\t威力UP\n[3]\t連撃数UP";
					num_2 = enter_number.method(print_2);
					if(num_2 >= 0 && num_2 <= 3) {
						break;
					}
				}
				switch(num_2) {
				case 1:
					if(pl_skil[num_1][2] <= 0) {
						System.out.println("これ以上クールタイムを減少できません");
					}else {
						pl_skil[num_1][2] -= 5;
						if(pl_skil[num_1][2] <= 0) {
							pl_skil[num_1][2] = 0;
						}
						success = true;
					}
					break;
				case 2:
					pl_skil[num_1][3] += 30;
					success = true;
					break;
				case 3:
					pl_skil[num_1][4] += 1;
					success = true;
					break;
				}
				if(success == true) {
					pl_status[8] -= 1000;
					pl_skil[num_1][6] += 1;
					System.out.println("スキルの強化に成功しました。1000Gold支払いました");
				}
			}
		}
		ssu_output.player_status = pl_status;
		ssu_output.player_skil = pl_skil;
		return ssu_output;
	}
}

