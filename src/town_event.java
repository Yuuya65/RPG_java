import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class town_event {
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	String[] name;
	int[] player_arsenal;
	String event;
	/******************************************************************************
	 * 換金所のプログラム
	 ******************************************************************************/
	public town_event storage(int[] pl_status, String[] pl_belonging, int[] pl_arsenal, String[] pl_name) throws IOException {
		town_event output = new town_event();
		
		main main = new main();
		use_K uk = new use_K();
		
		main.on_display_item(pl_belonging);
		
		if(pl_belonging[0].equals("no")) {
			System.out.println();
		}else {
			int choice;
			while(true) {
				String print = "ようこそ換金所へ。なにをしますか？\n"
						+ "[1]\tアイテムの換金\n[2]\tインゴッドの作成\n[3]\tキャンセル《1~3を入力》\t>>>";
				choice = enter_number.method(print);
				choice = enter_number.cut_out(choice);
				if(choice >= 1 && choice <= 3) {
					break;
				}
			}
			
			switch (choice) {
			case 1:
				use_K Cash = uk.Cash(pl_name, pl_status, pl_belonging);
				pl_status = Cash.player_status;
				pl_belonging = Cash.player_belonging;
				break;
			case 2:
				use_K Ingod = uk.Ingod(pl_name, pl_belonging, pl_arsenal);
				pl_belonging = Ingod.player_belonging;
				pl_arsenal = Ingod.player_arsenal;
				break;
			case 3:
				System.out.println();
				break;
			}
			
		}
		System.out.print("続ける場合はｗキー、やめる場合はEnterキーを押す");
		
		output.player_belonging = pl_belonging;
		output.player_status = pl_status;
		output.player_arsenal = pl_arsenal;
		
		return output;
	}
	
	/******************************************************************************
	 * 武器作成のプログラム
	 ******************************************************************************/
	public town_event buki(int[] pl_status, int[][] pl_efficacy, int[] pl_arsenal) throws IOException {
		town_event output = new town_event();
		
		//　武器の名前
		String[][] weapon_name = {
				{"グリフィンズコート","AGI+5、VIT+10、耐久力150",},
				{"ヴェールアーマー","HP+35、VIT+40、耐久力420",},
				{"セインツメイル","HP+150、VIT+60、耐久力460",},
				{"ブラックウィルム・コート","HP120、AGI+210、耐久力2750",},
				{"ライノクスアーマー","HP+500、VIT+350、耐久力2800",},
				
				{"アイアンソード","STR+15、AGI+5、耐久力150",},
				{"エンペラーソード","STR+40、AGI+25、耐久力600",},
				{"オブリビオンエッジ","STR+70、AGI+100、耐久力1300",},
				{"エリュシデータ","STR+120、AGI+200、耐久力2550",},
				{"魔剣ティルヴィング","STR+350、AGI+120、耐久力2600",},
				{"エクスキャリバー","STR+520、AGI+270、耐久力3800",},
				
		};
		int[][] weapon_status = {
				{0,0,5,10,0,0,0,0,1,150,300},
				{35,0,0,40,1,15,2,10,15,420,800},
				{150,0,0,60,1,15,2,10,35,460,800},
				{120,0,210,0,1,20,3,20,60,2750,1000},
				{500,0,0,350,2,25,4,30,100,2800,1200},

				{0,15,5,0,0,0,0,0,1,150,300},
				{0,40,25,0,1,15,2,10,20,600,750},
				{0,70,100,0,1,20,3,15,50,1300,900},
				{0,120,200,0,1,20,2,20,90,2550,1000},
				{0,350,120,0,2,30,4,15,120,2600,1200},
				{0,520,270,0,3,40,4,20,150,3800,0},
		/*
		 * 0・・・HPのステータス　1・・・STRのステータス　2・・・AGIのステータス ３・・・VITのステータス
		 * 4・・・必要素材1（0…なし　1…鉄鉱石　2…白銀鉱石　3…プラチム鉱石　4…黄金石）　5・・・素材1の必要数
		 * 6・・・必要素材2（0…なし　1…鉄鉱石　2…白銀鉱石　3…プラチム鉱石　4…黄金石）　7・・・素材2の必要数
		 * 8・・・必要レベル　９・・・耐久力 10・・・金額
		 * 
		 * //0・・・なし　1・・・鉄鉱石　2・・・白銀鉱石　3・・・プラチム鉱石　4・・・黄金石　5・・・なし
		*/
		};
		String[] all_sozai = {"","鉄鉱石","白銀鉱石","プラチム鉱石","黄金石"};
		String sozai_1 = null;
		String sozai_2 = null;
		int num = 0;
		String make = "sozai";
		String weapon_type = "sword";
		
		System.out.println("インゴッド所持数");
		System.out.println("鉄鉱石 "+pl_arsenal[1]+"個、白銀鉱石 "+pl_arsenal[2]+"個、プラチム鉱石 "
				+pl_arsenal[3]+"個、黄金石 "+pl_arsenal[4]+"個、所持しています。");
		System.out.println("武器一覧");
		for(int i = 0;i < weapon_name.length;i++) {
			System.out.print("["+(i+1)+"]\t"+weapon_name[i][0]+"\t必要Gold:"+weapon_status[i][10]
					+ "\t必要Lv:"+weapon_status[i][8]+"\n\t説明:"+weapon_name[i][1] +"\n"
					+ "\t必要素材:");
			if(weapon_status[i][4] == 0) {
				System.out.println("なし\n");
			}else {
				switch(weapon_status[i][4]) {
				case 1:
					sozai_1 = "鉄鉱石";
					break;
				case 2:
					sozai_1 = "白銀鉱石";
					break;
				case 3:
					sozai_1 = "プラチム鉱石";
					break;
				case 4:
					sozai_1 = "黄金石";
					break;
				}
				switch(weapon_status[i][6]) {
				case 1:
					sozai_2 = "鉄鉱石";
					break;
				case 2:
					sozai_2 = "白銀鉱石";
					break;
				case 3:
					sozai_2 = "プラチム鉱石";
					break;
				case 4:
					sozai_2 = "黄金石";
					break;
				}
				System.out.println(sozai_1+"が"+weapon_status[i][5]+"個、"
						+ sozai_2+"が"+weapon_status[i][7]+"個、必要です\n");
			}
		}
		while(true) {
			String print = "作成する武器の番号を入力 0入力でキャンセル>>>";
			num = enter_number.method(print);
			if(num >= 0 && num <= 11) {
				break;
			}
		}
		if(num == 0) {
			System.out.println("武器の作成をキャンセルしました");
			make = "cancel";
		}else {
			num -= 1;
			if(pl_status[0] >= weapon_status[num][8]) {
				if(pl_status[8] >= weapon_status[num][10]) {
					if(pl_arsenal[weapon_status[num][4]] >= weapon_status[num][5]) {		//素材1の素材数が足りているかの判定
						if(pl_arsenal[weapon_status[num][6]] >= weapon_status[num][7]) {	//素材2の素材数が足りているかの判定
							
							pl_arsenal[weapon_status[num][4]]-=weapon_status[num][5];	//使った素材数減らす
							pl_arsenal[weapon_status[num][6]]-=weapon_status[num][7];	//使った素材数減らす
							
							if(num >= 0 && num <= 4) {
								weapon_type = "shield";
							}
							switch(weapon_type) {
							case "shield":
								/* HP */
								if(pl_efficacy[0][0] != 0) {
									pl_status[4] -= pl_efficacy[0][0];
								}
								pl_efficacy[0][0] = weapon_status[num][0];
								pl_efficacy[0][1] = weapon_status[num][9];
								/* AGI */
								if(pl_efficacy[2][0] != 0) {
									pl_status[6] -= pl_efficacy[2][0];
								}
								pl_efficacy[2][0] = weapon_status[num][2];
								pl_efficacy[2][1] = weapon_status[num][9];
								/* VIT */
								if(pl_efficacy[4][0] != 0) {
									pl_status[7] -= pl_efficacy[4][0];
								}
								pl_efficacy[4][0] = weapon_status[num][3];
								pl_efficacy[4][1] = weapon_status[num][9];
								break;
							default:
								/* STR */
								if(pl_efficacy[1][0] != 0) {
									pl_status[5] -= pl_efficacy[1][0];
								}
								pl_efficacy[1][0] = weapon_status[num][1];
								pl_efficacy[1][1] = weapon_status[num][9];
								/* AGI */
								if(pl_efficacy[3][0] != 0) {
									pl_status[6] -= pl_efficacy[3][0];
								}
								pl_efficacy[3][0] = weapon_status[num][2];
								pl_efficacy[3][1] = weapon_status[num][9];
							}
							
							/* 0・・・HPのステータス　1・・・STRのステータス　2・・・AGIのステータス ３・・・VITのステータス ９・・・耐久力
							/*player_efficacy = /*[0…武器HP、1…武器STR、2…武器AGI、3…武器AGI、4…武器VIT
							5…アイテムSTR、6…アイテムAGI、7…アイテムVIT][0…効果(初期値＝0)、1…残り期間(初期値＝0)]*/
							
							pl_status[3] += weapon_status[num][0];	//Hpのステータス更新
							pl_status[4] += weapon_status[num][0];	//Hpのステータス更新
							pl_status[5] += weapon_status[num][1];	//STRのステータス更新
							pl_status[6] += weapon_status[num][2];	//AGIのステータス更新
							pl_status[7] += weapon_status[num][3];	//VITのステータス更新
							pl_status[8] -= weapon_status[num][10];	//Goldの更新
							
							make = "true";		//武器の作成が出来た場合
						}
					}
				}else {
					System.out.println("所持金が足りず"+weapon_name[num][0]+"を作成出来ませんでした");
					make = "Gold";
				}
			}else {
				System.out.println("プレイヤーLｖが足りず"+weapon_name[num][0]+"を作成出来ませんでした");
				make = "Lv";
			}
		}
		
		if(make.equals("true")) {
			System.out.println(weapon_name[num][0]+"を作成しました\n");
		}else if(make.equals("sozai")){
			System.out.println("素材が足りず"+weapon_name[num][0]+"を作成出来ませんでした");
		}
		System.out.print("\n続ける場合はｗキー、やめる場合はEnterキーを押す");
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_arsenal = pl_arsenal;
		return output;
	}
	
	/******************************************************************************
	 * アイテム購入のプログラム
	 ******************************************************************************/
	public town_event buy(int[] pl_status, String[] pl_belonging) throws IOException {
		town_event output = new town_event();
		
		main main = new main();
		
		
		//アイテム一覧
		String[][] item_buy_name = {
				{"00","ポーション\t","HPを30回復",},
				{"01","ハイポーション\t","HPを50回復",},
				{"02","エストポーション","HPを100回復",},
				{"03","グランポーション","HPを250回復",},
				{"04","STRポーション\t","一定時間の間STRを50増加",},
				{"05","AGIポーション\t","一定時間の間AGIを30増加",},
				{"06","VITポーション\t","一定時間の間VITを30増加",},
				{"25","HPポーション\t","一定時間の間HPを80増加",},
				
				{"07","治癒結晶\t","最大HPの30％回復",},
				{"08","回復結晶\t","最大HPの60％回復",},
				{"09","全快結晶\t","HPを100％回復",},
				{"10","剣撃結晶\t","一定時間の間STRが2倍",},
				{"11","新速結晶\t","一定時間の間AGIが2倍",},
				{"12","絶守結晶\t","一定時間の間VITが2倍",},
				{"26","強堅結晶\t","一定時間の間HPが2倍",},
				
				{"27","剣撃結晶ー改\t","一定時間の間STRが3倍",},
				{"28","新速結晶ー改\t","一定時間の間AGIが3倍",},
				{"29","絶守結晶ー改\t","一定時間の間VITが3倍",},
				{"30","強堅結晶ー改\t","一定時間の間HPが3倍",},
		};
		int[] item_buy_price = {
				250,500,1000,1500,1400,1400,1400,1400,
				520,1050,2000,2500,2500,2500,2500,
				3500,3500,3500,3500,
		};
		
		int count = 0;
		int num = 0;
		boolean belonging_max = true;
		
		System.out.println("いらっしゃいませ！どのような商品をお探しですか？");
		System.out.println("アイテム一覧");
		for(int i = 0;i < item_buy_name.length;i++) {
			count++;
			System.out.println("["+ count +"]\t" + item_buy_name[i][1] + "\t金額:"
					+ item_buy_price[i] +"\n\t説明:\t" + item_buy_name[i][2] + "\n");
		}
		
		while(true) {
			String print = "使用するアイテムを番号で入力>>>\n0入力でキャンセル";
			num = enter_number.method(print);
			if(num>=0 && num<=19) {
				break;
			}
		}
		num -= 1;
		if(num == -1) {
			System.out.println("購入をキャンセルしました。");
		}else {
			if(pl_status[8] >= item_buy_price[num]) {
				for(int n = 0;n < pl_belonging.length;n++) {
					if(pl_belonging[n].equals("no")) {
						pl_belonging[n] = item_buy_name[num][0];
						pl_status[8] -= item_buy_price[num];
						System.out.println(item_buy_name[num][1] + "を購入しました。\n"
								+ "所持金が" + item_buy_price[num] + "減りました");
						belonging_max = false;
						break;
					}
				}
				if(belonging_max == true) {
					System.out.println("所持アイテムがいっぱいで購入できません");
				}
			}else {
				System.out.println("所持金が足りていません");
			}
		}
		System.out.print("\n続ける場合はｗキー、やめる場合はEnterキーを押す");
		output.player_status = pl_status;
		output.player_belonging = pl_belonging;
		return output;
	}
	/******************************************************************************
	 * スキル修得のプログラム
	 ******************************************************************************/
	public town_event get_skil(int[] pl_status, int[][] pl_skil) throws IOException {
		town_event gs_output = new town_event();
		skil ss = new skil();
		
		int num;
		
		while(true) {
			String print = "ようこそ、スキルの修練場へ！なにをしますか？\n"
					+ "[1]\t新しいスキルの修得\n[2]\tスキルの強化";
			num = enter_number.method(print);
			if(num > 0 && num <= 2) {
				break;
			}
		}
		switch (num) {
		case 1:
			if(pl_status[0] < 5) {
				System.out.println("現在のレベルでは利用できません！");
			}else if(pl_status[8] < 1500){
				System.out.println("所持金が足りません！スキル修得には1500Gold必要です");
			}else {
				skil Acquisition = ss.Acquisition_skil(pl_status, pl_skil);
				pl_status = Acquisition.player_status;
				pl_skil = Acquisition.player_skil;
			}
			break;
		case 2:
			if(pl_status[0] < 5) {
				System.out.println("現在のレベルでは利用できません！");
			}else if(pl_status[8] < 1000){
				System.out.println("所持金が足りません！スキル修得には1000Gold必要です");
			}else {
				skil skil_up = ss.skil_up(pl_status, pl_skil);
				pl_status = skil_up.player_status;
				pl_skil = skil_up.player_skil;
			}
		}
		
		
		

		System.out.print("\n続ける場合はsキー、やめる場合はEnterキーを押す");
		gs_output.player_status = pl_status;
		gs_output.player_skil = pl_skil;
		return gs_output;
	}
	
	/******************************************************************************
	 * コロシアムのプログラム
	 ******************************************************************************/
	public town_event Coliseum(int[] pl_status, int[][] pl_efficacy, int[][] pl_skil, String[] pl_name,
			int[] pl_point, String event, String[] player_belonging, int[] pl_arsenal) throws IOException {
		town_event output = new town_event();
		
		main main = new main();
		battle input = new battle();
		map map = new map();
		location pl = new location();
		skil ps = new skil();
		
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		
		String[] teki_all_name = {
				"メタルスライム","ブラックデーモン","マンティコア","ネクロマンサー","フリーズゴーレム",
				"ハーデス","理滅神","ゴッドドラゴン","大魔王サタン","女王ヘラ","破壊神シヴァ",
		};
		//0...レベル、1...value、2...0、3...HP、4...最大HP、5...攻撃力、6...魔力、7...0、0
		int[][] te_all_status = {
				{1,0,0,150,150,50,20,40,0,0},
				{1,0,0,180,180,100,50,0,0,0},
				{1,0,0,160,160,60,40,60,0,0},
				{1,0,0,280,280,150,70,80,0,0},
				{1,0,0,620,620,180,30,100,0,0},
				
				{1,0,0,580,580,380,150,80,0,0},
				{1,0,0,800,800,500,300,150,0,0},
				{1,0,0,1200,1200,520,240,350,0,0},
				{1,0,0,900,900,600,1000,180,0,0},
				{1,0,0,2000,2000,500,800,420,0,0},
				{1,0,0,2200,2200,900,700,350,0,0},
		};
		/*	[順番][0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…ランク、9…null]  */
		int[] teki_status = new int[10];
		int teki_lv = 1;
		int teki_num = 0;
		int [] random_point= {0,1,2,3,4,5,6,7,8,9,};
		Random ran = new Random();
		
		String game_over = "no";
		int ren = pl_status[9];
		
		while(game_over == "no") {
			map m = map.method();
			String[][][] main_map = m.map;
			main.on_display_map(main_map[pl_point[0]], pl_point);
			System.out.println("現在"+ren+"連勝中!");
			main.on_display_status(pl_status, pl_name[0]);
			
			location pl_location = pl.method(main_map, pl_point, player_belonging, pl_status, pl_efficacy, pl_skil, pl_name, pl_arsenal, event);
			pl_point = pl_location.player_point;
			pl_status = pl_location.player_status;
			pl_efficacy = pl_location.player_efficacy;
			pl_skil = pl_location.player_skil;
			pl_arsenal = pl_location.player_arsenal;
			event = pl_location.event;
			
			if(event == "B") {
				if(ren <= 5) {
					teki_num = random_point[ran.nextInt(2)];
				}else if(ren <= 10) {
					teki_num = random_point[ran.nextInt(7)];
				}else if(ren <= 20) {
					teki_num = 2+random_point[ran.nextInt(5)];
				}else if(ren <= 35) {
					teki_num = 2+random_point[ran.nextInt(8)];
				}else if(ren <= 45) {
					teki_num = 2+random_point[ran.nextInt(5)];
				}else if(ren == 49){
					teki_num = 10;
				}else {
					teki_num = 5+random_point[ran.nextInt(5)];
				}
				
				teki_lv = ren+1;
				
				teki_status[0] = te_all_status[teki_num][0];
				teki_status[3] = te_all_status[teki_num][3];
				teki_status[4] = te_all_status[teki_num][4];
				teki_status[5] = te_all_status[teki_num][5];
				teki_status[6] = te_all_status[teki_num][6];
				teki_status[7] = te_all_status[teki_num][7];
				teki_status[8] = te_all_status[teki_num][8];
				if(ren >= 1) {
					teki_status[0] += teki_lv;	//レベル
					teki_status[3] += te_all_status[teki_num][3]/10*teki_lv;	//HP
					teki_status[4] += te_all_status[teki_num][4]/10*teki_lv;	//HP
					teki_status[5] += te_all_status[teki_num][5]/10*teki_lv;	//攻撃力
					teki_status[6] += te_all_status[teki_num][6]/10*teki_lv;	//素早さ
					teki_status[7] += te_all_status[teki_num][7]/10*teki_lv;	//防御力
				}
				
				battle battle = input.method(pl_status, pl_skil, pl_name, teki_status, teki_all_name[teki_num]);
				pl_status = battle.pl_status;
				pl_skil = battle.pl_skil;
				game_over = battle.game_over;
				if(game_over == "no") {
					ren++;
				}
				if(game_over == "no" && ren != 50) {
					pl_status[8] += ren * 100;
					System.out.print("所持金が"+(ren*30)+"増えました\n現在"+ren+"連勝中!!\t対戦を続けますか？《やめる場合は n を入力》\t>>>");
					String y = br.readLine();
					if(y.equals("n")) {
						System.out.println("対戦を終了しました");
						pl_status[9] = ren;
						game_over = "end";
					}
				}
				if(ren == 50) {
					pl_status[8] += 100000;
					pl_status[9] = 0;
					System.out.println("コロシアム制覇!!\nおめでとうございます。賞金100000Golod!!\n"
							+ "特別なスキルを獲得できます。");
					skil skil = ps.Limited_skil(pl_skil);
					pl_skil = skil.player_skil;
					game_over = "end";
				}
				pl_point[0] = 4;
				pl_point[1] = 6;
				pl_point[2] = 3;
			}
			
		}
		if(game_over == "yes") {
			System.out.println(ren+"戦目で敗北しました");
			pl_status[9] = 0;
		}
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_skil = pl_skil;
		output.event = event;
		return output;
	}
}
/**********************************************************************************************
 * 換金所で実行するプログラム
 **********************************************************************************************/
class use_K{
	int[] player_status;
	String[] player_belonging;
	int[] player_arsenal;	//0・・・なし　1・・・鉄鉱石　2・・・白銀鉱石　3・・・プラチム鉱石　4・・・黄金石　5・・・なし
	
	/*********************************************************************************************
	 * 換金
	 *********************************************************************************************/
	public use_K Cash(String[] pl_name, int[] pl_status, String[] pl_belonging) throws IOException {
		use_K Cash_output = new use_K();
		
		main main = new main();
		
		String[][] item_all_name = {
				{"00","ポーション","HPを30回復",},
				{"01","ハイポーション","HPを50回復",},
				{"02","エストポーション","HPを100回復",},
				{"03","グランポーション","HPを250回復",},
				{"04","STRポーション","一定時間の間STRを50増加",},
				{"05","AGIポーション","一定時間の間AGIを30増加",},
				{"06","VITポーション","一定時間の間VITを30増加",},
				{"07","治癒結晶","最大HPの30％回復",},
				{"08","回復結晶","最大HPの60％回復",},
				{"09","全快結晶","HPを100％回復",},
				
				{"10","剣撃結晶","一定時間の間STRが2倍",},
				{"11","新速結晶","一定時間の間AGIが2倍",},
				{"12","絶守結晶","一定時間の間VITが2倍",},
				{"13","鉄鉱石","鉄鉱石1個分",},
				{"14","鉄鉱石","鉄鉱石3個分",},
				{"15","鉄鉱石","鉄鉱石5個分",},
				{"16","白銀鉱石","白銀鉱石1個分",},
				{"17","白銀鉱石","白銀鉱石3個分",},
				{"18","白銀鉱石","白銀鉱石5個分",},
				{"19","プラチム鉱石","プラチム鉱石1個分",},
				
				{"20","プラチム鉱石","プラチム鉱石3個分",},
				{"21","プラチム鉱石","プラチム鉱石5個分",},
				{"22","黄金石","黄金石1個分",},
				{"23","黄金石","黄金石3個分",},
				{"24","黄金石","黄金石5個分",},
				{"25","HPポーション","一定時間の間HPを80増加",},
				{"26","強堅結晶","一定時間の間HPが2倍",},
				{"27","剣撃結晶ー改","一定時間の間STRが3倍",},
				{"28","新速結晶ー改","一定時間の間AGIが3倍",},
				{"29","絶守結晶ー改","一定時間の間VITが3倍",},
				
				{"30","強堅結晶ー改","一定時間の間HPが3倍",},
		};
		int[] item_sell_price = {
				125,250,500,750,700,700,700,260,525,1000,
				1250,1250,1250,50,150,250,75,225,375,100,
				300,500,150,450,750,700,1250,1750,1750,1750,
				1750,
		};
		
		int choice;
		boolean redo = true;
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n[1]\tアイテムの換金");
		while(redo) {
			main.on_display_item(pl_belonging);
			while(true) {
				String print = "換金するアイテムを選択してください《0でキャンセル》\t>>>";
				choice = enter_number.method(print) - 1;
				if(choice < pl_belonging.length) {
					if(choice == -1 || !(pl_belonging[choice].equals("no"))) {
						break;
					}
				}
				
			}
			if(choice != -1) {
				for(int a = 0;a < item_all_name.length;a++) {
					if(pl_belonging[choice].equals(item_all_name[a][0])) {
						pl_belonging[choice] = "no";
						pl_status[8] += item_sell_price[a];
						System.out.println(item_all_name[a][1] + "を売って" + item_sell_price[a]
								+ "Gold手に入れました。");
						break;
					}
				}
			}
			if(choice == -1) {
				redo = false;
				System.out.println("換金をキャンセルしました");
			}else {
				System.out.print("換金を続けますか？《y or n 入力》>>>");
				String repeat = br.readLine();
				if(repeat.equals("n")) {
					redo = false;
				}
			}
		}
		
		
		Cash_output.player_status = pl_status;
		Cash_output.player_belonging = pl_belonging;
		return Cash_output;
	}
	/*********************************************************************************************
	 * インゴッド
	 *********************************************************************************************/
	public use_K Ingod(String[] pl_name, String[] pl_belonging, int[] pl_arsenal) throws IOException {
		use_K Ingod_output = new use_K();
		
		//使用する持ち物の選択
		int Privilege = 1;
		boolean hyouji = true;
		int[] arsenal_count = {0,0,0,0,0,0};
				
		if(pl_name[1].equals("トラベラー")) {
			Privilege = 2;
		}
		System.out.println("\n[2]\tインゴッドの作成");
		for(int i = 0;i < pl_belonging.length;i++) {
			hyouji = true;
			switch(pl_belonging[i]) {
			/* 鉄鉱石 */
			case "13":
				pl_arsenal[1] += 1*Privilege;
				arsenal_count[1] += 1*Privilege;
				break;
			case "14":
				pl_arsenal[1] += 3*Privilege;
				arsenal_count[1] += 3*Privilege;
				break;
			case "15":
				pl_arsenal[1] += 5*Privilege;
				arsenal_count[1] += 5*Privilege;
				break;
			/* 白銀鉱石 */
			case "16":
				pl_arsenal[2] += 1*Privilege;
				arsenal_count[2] += 1*Privilege;
				break;
			case "17":
				pl_arsenal[2] += 3*Privilege;
				arsenal_count[2] += 3*Privilege;
				break;
			case "18":
				pl_arsenal[2] += 5*Privilege;
				arsenal_count[2] += 5*Privilege;
				break;
			/* プラチム鉱石 */
			case "19":
				pl_arsenal[3] += 1*Privilege;
				arsenal_count[3] += 1*Privilege;
				break;
			case "20":
				pl_arsenal[3] += 3*Privilege;
				arsenal_count[3] += 3*Privilege;
				break;
			case "21":
				pl_arsenal[3] += 5*Privilege;
				arsenal_count[3] += 5*Privilege;
				break;
			/* 黄金石 */
			case "22":
				pl_arsenal[4] += 1*Privilege;
				arsenal_count[4] += 1*Privilege;
				break;
			case "23":
				pl_arsenal[4] += 3*Privilege;
				arsenal_count[4] += 3*Privilege;
				break;
			case "24":
				pl_arsenal[4] += 5*Privilege;
				arsenal_count[4] += 5*Privilege;
				break;
			default:
				hyouji = false;
			}
			if(hyouji == true) {
				pl_belonging[i] = "no";
			}
		}
		if(pl_name[1] == "トラベラー") {
			System.out.println("『トラベラーの特権』インゴッドが2倍になります");
		}
		System.out.println("鉄鉱石を"+arsenal_count[1]+"個、白銀鉱石を"+arsenal_count[2]+"個、"
				+ "プラチム鉱石を"+arsenal_count[3]+"個、黄金石を"+arsenal_count[4]+"個、回収しました");
		
		Ingod_output.player_arsenal = pl_arsenal;
		Ingod_output.player_belonging = pl_belonging;
		return Ingod_output;
	}
	
}

