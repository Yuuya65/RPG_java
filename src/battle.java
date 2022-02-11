import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

class battle {
	int[] pl_status;
	int[] te_status;
	int[][] pl_skil;
	int total_damage;
	String result;
	String game_over;
	/**********************************************************************************************
	 * バトルのプログラム
	 **********************************************************************************************/
	public battle method(int[] pl_status, int[][] pl_skil, String[] pl_name, int[] te_status, String te_name) throws IOException {
		damage damage_num = new damage();
		battle output = new battle();
		
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		
		String game_over = "no";		//負けた場合"yes"になる
		String result = "no";			//バトルが続いてる間は"no"、それ以外は結果を表示"win" "lose"
		int pl_agi = pl_status[6];		//プレイヤーの行動ゲージを設定
		int te_agi = te_status[6];		//相手の行動ゲージを設定
		
		while(result.equals("no")) {
			on_display_battle(pl_status,pl_name[0]);
			on_display_battle(te_status,te_name);
			
			if(pl_agi > te_agi) {		//プレイヤーのターン
				/* 行動ゲージの更新 */
				pl_agi -= te_agi;
				te_agi = te_status[6];
				if(pl_agi < te_agi) {
					System.out.println("\n" + pl_name[0] + "のターン\t次→" + te_name);
				}else {
					System.out.println("\n" + pl_name[0] + "のターン\t次→" + pl_name[0]);
				}
				/* プレイヤーの攻撃 */
				damage p_damage = damage_num.player_attack(pl_status, pl_skil, pl_name, te_status, te_name);
				te_status = p_damage.te_status;
				pl_skil = p_damage.pl_skil;
				result = p_damage.result;
			}else if(pl_agi < te_agi) {	//敵のターン
				/* 行動ゲージの更新 */
				te_agi -= pl_agi;
				pl_agi = pl_status[6];
				if(pl_agi < te_agi) {
					System.out.println("\n" + te_name + "のターン\t次→" + te_name);
				}else {
					System.out.println("\n" + te_name + "のターン\t次→" + pl_name[0]);
				}
				/* 敵の攻撃 */
				damage t_damage = damage_num.teki_attack(pl_status, pl_name, te_status, te_name);
				pl_status = t_damage.pl_status;
				result = t_damage.result;
			}else {						//プレイヤーと敵の行動ゲージが一緒のとき、プレイヤーのターン　→　敵のターン
				/* 行動ゲージの設定 */
				pl_agi = pl_status[6];
				te_agi = te_status[6];
				System.out.println("\n" + pl_name[0] + "のターン\t次→" + te_name);
				/* プレイヤーの攻撃 */
				damage p_damage = damage_num.player_attack(pl_status, pl_skil, pl_name, te_status, te_name);
				te_status = p_damage.te_status;
				pl_skil = p_damage.pl_skil;
				result = p_damage.result;
				/* 敵が生きていたら実行 */
				if(result.equals("no")) {	//敵のターン
					if(pl_agi < te_agi) {
						System.out.println("\n" + te_name + "のターン\t次→" + te_name);
					}else {
						System.out.println("\n" + te_name + "のターン\t次→" + pl_name[0]);
					}
					/* 敵の攻撃 */
					damage t_damage = damage_num.teki_attack(pl_status, pl_name, te_status, te_name);
					pl_status = t_damage.pl_status;
					result = t_damage.result;
				}
			}
			
			if(result.equals("win")) {
				System.out.println(te_name+"を倒した");
				int value = te_status[8];
				pl_status = win(pl_status,pl_name,value);
				game_over = "no";
			}else if(result.equals("lose")) {
				System.out.println(te_name+"に敗れた");
				int value = te_status[1];
				pl_status = lose(pl_status, pl_name, te_name);
				game_over = "yes";
			}
		}
		
		/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…所持金、]*/
	
		
		output.pl_status = pl_status;
		output.pl_skil = pl_skil;
		output.game_over = game_over;
		return output;
	}
	
	/************************************************************************************************
	 * ステータスを表示するプログラム
	 ************************************************************************************************/
	public static void on_display_battle(int[] status, String name) {
		System.out.println(name+"\tLv_"+status[0]+"\tHP_"+status[3]+"/"+status[4]
				+"\tSTR_"+status[5]+"\tAGI_"+status[6]+"\tVIT_"+status[7]);
	}
	/********************************************************************************************************
	 * 勝った時
	 ********************************************************************************************************/
	public int[] win(int[] pl_status, String[] pl_name, int value) {
		
		Random ran = new Random();
		
		int Ex_point = 0;
		//経験値をもらう
		if(value != 0) {
			Ex_point =  1 + pl_status[0] / 5;		
			pl_status[1] += (Ex_point + value) * 10;
		}
		
		// Lvアップの判定
		int lv_count = 0; //上がったレベル数をカウントする
		
		while(true) {
			if(pl_status[1] >= pl_status[2]) {
				pl_status[0]++;
				lv_count++;
				pl_status[1] = pl_status[1] - pl_status[2];
				pl_status[2] = pl_status[0] * 25;
			}else {
				break;
			}
		}
		
		// レベルアップ時、プレイヤーのステータスアップをする
		
		
		int[] status_point = new int[20];		//１～20までの数字を格納する
		for(int a = 0;a < status_point.length;a++) {
			status_point[a] = a + 1;
		}
		int[] status_record = new int [10];
		
		// プレイヤーの職業別ボーナス
		int[] hosei = {1,1,1,1,1,1,1,1,1};
		/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…所持金、9…コロシアム連勝記録]*/
		
		switch(pl_name[1]) {
			case "剣士":			//STRとAGIが二倍
				hosei[5] = 2;
				hosei[6] = 2;
				break;
			case "フェンサー":		//AGIが三倍
				hosei[6] = 3;
				break;
			case "ディフェンダー":	//VITが三倍
				hosei[7] = 3;
				break;
			case "トラベラー":		//HPが三倍
				hosei[3] = 3;
				hosei[4] = 3;
				break;
			case "貴族":			//所持金が四倍
				hosei[8] = 2;
				break;
		}
		
		for(int a = 0;a < lv_count;a++) {
			status_record[4] += status_point[ran.nextInt(hosei[4] * 3)] + hosei[4] * 10;
			status_record[5] += status_point[ran.nextInt(hosei[5] * 3)] + hosei[5] * 2;
			status_record[6] += status_point[ran.nextInt(hosei[6] * 3)] + hosei[6] * 2;
			status_record[7] += status_point[ran.nextInt(hosei[7] * 3)] + hosei[7] * 2;
			
			/*
			for(int b = 4;b < status_record.length;b++) {
				System.out.print("b>" + b + " record>" + status_record[b] + " ");
			}
			System.out.println();
			*/
		}
		status_record[3] = status_record[4]; 
		status_record[8] = hosei[8] * value * 100 / (pl_status[0] / 20 + 1);
		
		/* レベルアップ後のステータスを反映 */
		for(int a = 0;a < pl_status.length;a++) {
			pl_status[a] += status_record[a];
		}
		/* HPの不具合修正 */
		if(pl_status[3] > pl_status[4]) {
			pl_status[3] = pl_status[4];
		}
		
		/* 表示 */
		System.out.println("経験値を"+ ((Ex_point + value) * 10 )+"手に入れた!");
		if(lv_count > 0) {
			System.out.println("レベルが"+lv_count+"上がった。\t最大HPが"+status_record[4]+"増えた。"
				+ "\tSTRが"+status_record[5]+"上がった。\nAGIが"+status_record[6]+"上がった。"
				+ "\tVITが"+status_record[7]+"上がった。");
		}
		System.out.println("Goldを"+ status_record[8] + "手に入れた!");
		
		/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…所持金、]*/
		
		return pl_status;
	}
	/***************************************************************************************
	 * 負けた時
	 ***************************************************************************************/
	public int[] lose(int[] pl_status, String[] pl_name, String te_name) {
		int lost;
		if(pl_name[1].equals("貴族")) {
			pl_status[8] += 1000;
			System.out.println(pl_name[0] + "は、" + te_name + "に敗れた。\n"
					+ "『貴族の特権』　所持金が1000増える。");
		}else {
			lost = pl_status[0] / 10 * 100;
			pl_status[8] -= lost;
			if(pl_status[8] <= 0) {
				pl_status[8] = 0;
				lost = 0 - pl_status[8];
			}
			if(pl_status[8] == 0) {
				System.out.println(pl_name[0] + "は、" + te_name + "に敗れた。\n"
						+ "所持金がなくなった。");
			}else {
				System.out.println(pl_name[0] + "は、" + te_name + "に敗れた。\n"
						+ "所持金" + lost + "を失った。");
			}
			
		}
		pl_status[3] = pl_status[4];
		return pl_status;
	}
}

/******************************************************************************************
 * ダメージを計算するプログラム
 ******************************************************************************************/
class damage{
	int[] pl_status;
	int[] te_status;
	int[][] pl_skil;
	String result;
	
	/***********************************************************************************
	 * プレイヤーの攻撃
	 ***********************************************************************************/
	public damage player_attack(int[] pl_status, int[][] pl_skil, String[] pl_name, int[] te_status, String te_name) throws IOException {
		damage pl_attack = new damage();
		skil ss = new skil();
		
		String[] all_skil_name = {
				"スラント","カタラクト","バーチカル",
				"バーチカル・アーク","ソニックリーブ","シャープネイル",
				"トライアンギュラー","ニュートロン","クルーシフィクション",		
				"メテオブレイク","スター・スプラッシュ","ノヴァ・アセンション",
				"マザーズ・ロザリオ","スターバースト・ストリーム",
		};
		int total_damage = 0;
		String result = "no";
		
		/* 攻撃方法を表示 */
		System.out.println("[1]\t通常攻撃\n\t威力_" + pl_status[5]);
		int count = 1;
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][3] != 0) {
				count++;
				System.out.print("[" + count + "]\t" + all_skil_name[pl_skil[n][0]] +"\n"
						+ "\t威力_" + pl_skil[n][3] + "\tクールタイム_" + pl_skil[n][1] + "\t");
				if(pl_skil[n][1] == 0) {
					System.out.println("使用可");
				}else {
					System.out.println("使用不可");
				}
				
			}
		}
		
		boolean redo = true;
		while(redo) {
			String print = "攻撃手段を選んでください>>>";
			int num = enter_number.method(print);
			num = enter_number.cut_out(num);
			
			if(num == 1) {
				System.out.println("通常攻撃");
				skil n_damage = ss.normal_damage(pl_skil);
				pl_skil = n_damage.player_skil;
				total_damage = pl_status[5];
				redo = false;
			}else if(num > count || num <= 0) {
				System.out.println("再入力");
			}else {
				num -= 2;
				if(pl_skil[num][1] == 0) {
					System.out.print("スキル攻撃\n" + all_skil_name[pl_skil[num][0]] + "\t");
					skil s_damage = ss.skil_damage(pl_status, pl_skil, num);
					pl_skil = s_damage.player_skil;
					total_damage = s_damage.total_damage;
					redo = false;
				}else {
					System.out.println("そのスキルは使用不可です");
				}
			}
		}
		total_damage -= te_status[7];
		if(total_damage <= 0) {
			total_damage = 0;
		}
		te_status[3] -= total_damage;
		System.out.println(te_name + "は" + total_damage + "ダメージ受けた！");
		
		/* 相手のHPが0以下になったとき */
		if(te_status[3] <= 0) {
			te_status[3] = 0;
			result = "win";
		}
		
		/*	[0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
			5…攻撃力、6…素早さ、7…防御力、8…所持金、]*/
		
		pl_attack.pl_skil = pl_skil;
		pl_attack.te_status = te_status;
		pl_attack.result = result;
		return pl_attack;
	}
	/***********************************************************************************
	 * 敵の攻撃
	 ***********************************************************************************/
	public damage teki_attack(int[] pl_status, String[] pl_name, int[] te_status, String te_name) throws IOException {
		damage te_attack = new damage();
		
		/* 敵の攻撃手段は通常攻撃のみ
		 * 
		 *  将来的には敵もスキルを使用できるようにする*/
		int total_damage = 0;
		String result = "no";
		
		total_damage = te_status[5] - pl_status[7];
		if(total_damage <= 0) {
			total_damage = 0;
		}
		pl_status[3] -= total_damage;
		System.out.println(pl_name[0] + "は" + total_damage + "ダメージ受けた！");
		
		/* プレイヤーのHPが0以下になったとき */
		if(pl_status[3] <= 0) {
			pl_status[3] = 0;
			result = "lose";
		}
		
		te_attack.pl_status = pl_status;
		te_attack.result = result;
		return te_attack;
	}
}


