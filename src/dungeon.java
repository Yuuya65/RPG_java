import java.io.IOException;
import java.util.Random;

class dungeon {
	int[] player_point;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	String game_over;
	/******************************************************************************
	 * ダンジョン構造のプログラム
	 ******************************************************************************/
	public dungeon RPG_dungeon(int[] pl_point, int[] pl_status, int[][] pl_efficacy,
			int[][] pl_skil, String[] pl_name, String[] pl_belonging, int[] pl_arsenal, String event) throws IOException {
		dungeon output = new dungeon();
		
		main Game_1_main = new main();
		map pm = new map();
		location pl = new location();
		all_event pae = new all_event();
		
		//フロア数をランダムで決める
		Random rand = new Random();
		int sum_floor = 2+(rand.nextInt(10)+1)%4;
		int now_floor = 1;
		if(pl_status[0] == 1) {
			sum_floor = 4;
		}
		String game_over = "no";	//"no"プレイ中、 "yes"探索失敗、 "clear"探索成功、
		
		while(game_over == "no") {
			event = "no";
			map map = pm.method();
			String[][][] main_map = map.map;
			while(!(event.equals("G")) && game_over == "no") {
				event ="no";
				System.out.print("\n" + now_floor + "/" + sum_floor + "階層");
				Game_1_main.on_display_map(main_map[pl_point[0]], pl_point);
				Game_1_main.on_display_status(pl_status, pl_name[0]);
				
				location pl_location = pl.method(main_map, pl_point, pl_belonging, pl_status, pl_efficacy, pl_skil, pl_name, pl_arsenal, event);
				pl_point = pl_location.player_point;
				pl_status = pl_location.player_status;
				pl_efficacy = pl_location.player_efficacy;
				pl_skil = pl_location.player_skil;
				pl_belonging = pl_location.player_belonging;
				pl_arsenal = pl_location.player_arsenal;
				event = pl_location.event;
				
				all_event ae = pae.move(event, pl_name, pl_point, pl_status, pl_efficacy, pl_skil, pl_belonging, pl_arsenal);
				pl_status = ae.player_status;
				pl_efficacy = ae.player_efficacy;
				pl_skil = ae.player_skil;
				pl_belonging =ae.player_belonging;
				game_over = ae.game_over;
			}
			now_floor++;
			//ダンジョン内の初期座標
			pl_point[0] = 2;
			pl_point[1] = 1;
			pl_point[2] = 1;
			if(now_floor == sum_floor) {	//次の階層が最終層の場合
				pl_point[0] = 3;
				pl_point[1] = 7;
				pl_point[2] = 4;
			}
		}
		if(game_over == "win") {
			System.out.println("\n探索成功");
		}else {
			System.out.println("\n探索失敗");
		}
		//ダンジョン探索終了
		pl_point[0] = 0;
		pl_point[1] = 5;
		pl_point[2] = 6;
		
		output.player_belonging = pl_belonging;
		output.player_point = pl_point;
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_skil = pl_skil;
		return output;
	}
	/******************************************************************************
	 * アイテムのプログラム
	 ******************************************************************************/
	public dungeon item(int[] pl_status, String[] pl_belonging) {
		dungeon output = new dungeon();
		String[][] item_all_name = {						//名前、表示
				{"00","ポーション","HPを30回復",},
				{"07","治癒結晶","最大HPの30％回復",},
				{"13","鉄鉱石","鉄鉱石1個分",},
				{"16","白銀鉱石","白銀鉱石1個分",},
				{"19","プラチム鉱石","プラチム鉱石1個分",},
				
				{"01","ハイポーション","HPを50回復",},
				{"08","回復結晶","最大HPの60％回復",},
				{"14","鉄鉱石","鉄鉱石3個分",},
				{"17","白銀鉱石","白銀鉱石3個分",},
				{"20","プラチム鉱石","プラチム鉱石3個分",},
				{"22","黄金石","黄金石1個分",},

				{"02","エストポーション","HPを100回復",},
				{"04","STRポーション","一定時間の間STRを50増加",},
				{"05","AGIポーション","一定時間の間AGIを30増加",},
				{"06","VITポーション","一定時間の間VITを30増加",},
				{"25","HPポーション","一定時間の間HPを80増加",},
				{"15","鉄鉱石","鉄鉱石5個分",},
				{"18","白銀鉱石","白銀鉱石5個分",},
				{"21","プラチム鉱石","プラチム鉱石5個分",},
				{"23","黄金石","黄金石3個分",},
				
				{"09","全快結晶","HPを100％回復",},
				{"10","剣撃結晶","一定時間の間STRが2倍",},
				{"11","新速結晶","一定時間の間AGIが2倍",},
				{"12","絶守結晶","一定時間の間VITが2倍",},
				{"26","強堅結晶","一定時間の間HPが2倍",},
				{"24","黄金石","黄金石5個分",},
		};
		/* [種類][0…アイテム番号、1…アイテム名、2…説明] */
		
		int [] random_point= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
		Random ran = new Random();
		int item_num = 0;
		boolean belonging_max = true;
		
		/* プレイヤーのレベルの応じて出現するアイテムを決める */
		if(pl_status[0] <=  3) {
			item_num = random_point[ran.nextInt(5)] + 20;
		}else if(pl_status[0] < 15){
			item_num = random_point[ran.nextInt(11)];
		}else if(pl_status[0] < 30){
			item_num = random_point[ran.nextInt(20)];
		}else {
			item_num = random_point[ran.nextInt(26)];
		}
		
		// 所持アイテムの並び替え
		int cou = 0;
		int amount;
		for(int n = 0;n < pl_belonging.length;n++) {
			if(!(pl_belonging[n].equals("no"))) {
				pl_belonging[cou] = pl_belonging[n];
				cou++;
			}
		}
		amount = cou;
		if(cou != 0) {
			for(;cou < pl_belonging.length;cou++) {
				pl_belonging[cou] = "no";
			}
		}
		
		/* アイテムを獲得する */
		for(int n = 0;n < pl_belonging.length;n++) {
			if(pl_belonging[n].equals("no")) {
				pl_belonging[n] = item_all_name[item_num][0];
				System.out.println(item_all_name[item_num][1] + "を拾いました。\n"
						+ "使用方法>>>" + item_all_name[item_num][2]);
				belonging_max = false;
				break;
			}
		}
		if(belonging_max == true) {
			System.out.println("所持アイテムがいっぱいでアイテムを拾えませんでした。\n");
		}
		
		output.player_belonging = pl_belonging;
		output.player_status = pl_status;
		return output;
	}
	/******************************************************************************
	 * 雑魚敵のプログラム
	 * Bose以外の敵
	 ******************************************************************************/
	public dungeon teki(int[] pl_status, int[][] pl_skil, String[] pl_name) throws IOException {
		dungeon output = new dungeon();
		battle input = new battle();
		
		String game_over = "no";
		
		/* 敵の名前 */
		String[]te_all_name = {
				"スケルトン","スライム","デーモン","オーガ","ゴブリン","マンティコア",
				"マスターデーモン","ネクロマンサー","アイアンゴーレム","ドラゴンゾンビ"
		};
		/* 敵のステータス */
		int[][] te_all_status = {
				{1,0,0,60,60,25,30,0,1,0},
				{1,0,0,60,60,30,20,15,1,0},
				{1,0,0,90,90,40,30,10,2,0},
				{1,0,0,110,110,40,25,20,2,0},
				{1,0,0,110,110,35,25,25,2,0},
				{1,0,0,130,130,45,30,30,2,0},
				
				{1,0,0,200,200,80,45,40,3,0},
				{1,0,0,220,220,100,50,15,3,0},
				{1,0,0,250,250,60,45,60,3,0},
				{1,0,0,320,320,120,90,70,3,0},
		};
		/*	[順番][0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
			5…攻撃力、6…素早さ、7…防御力、8…ランク、9…null]  */
		
		int[] teki_status = new int[10];
		int [] random_point= {0,1,2,3,4,5,6,7,8};
		Random ran = new Random();
		int teki_num = 0;
		
		/*　レベルに応じて敵の出現を変える		 */
		if(pl_status[0] < 5) {						//レベルが3未満のときは強い敵を表示しない
			teki_num = random_point[ran.nextInt(2)];
		}else if(pl_status[0] < 25){				//ゴブリンまでを表示
			teki_num = random_point[ran.nextInt(4)];
		}else if(pl_status[0] < 50){				//高レベル時
			teki_num = random_point[ran.nextInt(6)];
		}else {										//全て
			teki_num = random_point[ran.nextInt(9)];
		}
		
		int teki_level = 1;
		
		//teki_level += (pl_status[0] / 5) * (5 - te_all_status[teki_num][8]); 
		teki_level += pl_status[0] * 3 / te_all_status[teki_num][8];
		
		/*
		System.out.println("△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼"
				+ "\nteki_Lv >>> pl_status:" + pl_status[0] + " ÷ teki_Rank:"
				+ te_all_status[teki_num][8] + " × 3 = " + teki_level + "\n"
				+ "△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼△▼");
		*/
		
		/* 修正必要 */
		//バトル時の敵のステータス
		teki_status[0] += teki_level;	//レベル
		teki_status[3] += te_all_status[teki_num][3]/10*teki_level;	//HP
		teki_status[4] += te_all_status[teki_num][4]/10*teki_level;	//HP
		teki_status[5] += te_all_status[teki_num][5]/10*teki_level;	//攻撃力
		teki_status[6] += te_all_status[teki_num][6]/10*teki_level;	//素早さ
		teki_status[7] += te_all_status[teki_num][7]/10*teki_level;	//防御力
		teki_status[8] += teki_level;	//ランク
		/*
		teki_status[0] = te_all_status[teki_num][0];
		teki_status[3] = te_all_status[teki_num][3];
		teki_status[4] = te_all_status[teki_num][4];
		teki_status[5] = te_all_status[teki_num][5];
		teki_status[6] = te_all_status[teki_num][6];
		teki_status[7] = te_all_status[teki_num][7];
		teki_status[8] = te_all_status[teki_num][8];
		if(pl_status[0] >= 3) {
			teki_status[0] += teki_level;	//レベル
			teki_status[3] += te_all_status[teki_num][3]/10*teki_level;	//HP
			teki_status[4] += te_all_status[teki_num][4]/10*teki_level;	//HP
			teki_status[5] += te_all_status[teki_num][5]/10*teki_level;	//攻撃力
			teki_status[6] += te_all_status[teki_num][6]/10*teki_level;	//素早さ
			teki_status[7] += te_all_status[teki_num][7]/10*teki_level;	//防御力
			teki_status[8] += teki_level;	//ランク
		}
		//System.out.println("te_sta= " + teki_status[0]);
		
		/* バトルプログラム */
		battle Game_1_battle = input.method(pl_status, pl_skil, pl_name, teki_status, te_all_name[teki_num]);
		pl_status = Game_1_battle.pl_status;
		pl_skil = Game_1_battle.pl_skil;
		game_over = Game_1_battle.game_over;
		
		if(game_over.equals("lose")) {
			System.out.println("探索失敗");
		}
		
		output.player_status = pl_status;
		output.player_skil = pl_skil;
		output.game_over = game_over;
		return output;
	}
	/******************************************************************************
	 * ボスのプログラム
	 ******************************************************************************/
	public dungeon bose(int[] pl_status, int[][] pl_skil, String[] pl_name) throws IOException {
		dungeon output = new dungeon();
		
		battle input = new battle();
		
		String game_over = "no";
		
		/* 名前 */
		String[] te_all_name = {"魔王","竜王","大魔王","理滅神",};
		/* ステータス */
		int[][] te_all_status = {
				{1,0,0,150,150,80,70,25,7,},
				{1,0,0,160,160,75,40,35,7,},
				{1,0,0,170,170,50,120,30,8,},
				{1,0,0,350,350,200,60,60,8,},
		};
		/*	[順番][0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…ランク、]  
		
				{1,0,0,250,250,60,45,60,3,0},*/
	
		int[] teki_status = new int[9];
		int [] random_point= {0,1,2,3,};
		Random ran = new Random();
		int teki_num = random_point[ran.nextInt(4)];
		if(pl_status[0] < 16) {
			teki_num = 0;
		}
		
		int teki_level = 1;
		teki_level += (pl_status[0] / 5) * (8 - te_all_status[teki_num][8]); 
		
		//バトル時の敵のステータス
		teki_status[0] = te_all_status[teki_num][0];
		teki_status[3] = te_all_status[teki_num][3];
		teki_status[4] = te_all_status[teki_num][4];
		teki_status[5] = te_all_status[teki_num][5];
		teki_status[6] = te_all_status[teki_num][6];
		teki_status[7] = te_all_status[teki_num][7];
		teki_status[8] = te_all_status[teki_num][8]*5;
		if(pl_status[0] > 16) {
			teki_status[0] += teki_level;	//レベル
			teki_status[3] += te_all_status[teki_num][3]/10*teki_level;	//HP
			teki_status[4] += te_all_status[teki_num][4]/10*teki_level;	//HP
			teki_status[5] += te_all_status[teki_num][5]/10*teki_level;	//攻撃力
			teki_status[6] += te_all_status[teki_num][6]/10*teki_level;	//素早さ
			teki_status[7] += te_all_status[teki_num][7]/10*teki_level;	//防御力
			teki_status[8] += teki_level;	//ランク
		}
		
		/* バトルプログラム */
		battle RPG_battle = input.method(pl_status, pl_skil, pl_name, teki_status, te_all_name[teki_num]);
		pl_status = RPG_battle.pl_status;
		game_over = RPG_battle.game_over;
		
		if(game_over.equals("no")) {
			game_over = "win";
		}
		
		output.player_status = pl_status;
		output.player_skil = pl_skil;
		output.game_over = game_over;
		return output;
	}
}


