import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class location {
	int [] player_point;
	String[] player_belonging;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	int[] player_arsenal;
	String event;
	boolean logout;
	location method(String[][][] map, int[] player_point, String[] player_belonging, int[] player_status,
			int[][] player_efficacy, int[][] player_skil, String[] name, int[] player_arsenal ,String event) throws IOException {
		
		location output = new location();
		
		admin sa = new admin();
		main sm = new main();
		skil ss = new skil();
		item si = new item();
		
		//String event = "no";
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		boolean logout = false;
		String error = null;	//"no"正しい値を入力できている、"redo"やり直す、"repeat"繰り返す
		String enter = "enter";	//文字入力
		String dict = "dict";		//switch文の判定で使う
		int skip = 0;			//隠しコマンド　2文字以上入力でイベントに当たるまで進み続ける
		
		//System.out.println("Judgment of event >>> " + event);
		while(error != "no"){
			if(event == "end") {
				event = "no";
				
				main.on_display_map(map[player_point[0]], player_point);
				main.on_display_status(player_status, name[0]);
				
			}else{
				
				if(event == "continue") {
					enter = br.readLine();
					if(enter.length()==0) {
						dict = "no";
					}else {
						skip = enter.length();
						dict = enter.substring(0, 1);
					}
				}else {
					if(error != "repeat") {
						System.out.print("左>>>a, 右>>>d, 上>>>w, 下>>>s,　アイテム使用>>>i, を入力");
						enter = br.readLine();
						if(enter.length()==0) {
							dict = "no";
						}else {
							skip = enter.length();
							dict = enter.substring(0, 1);
						}
					}
				}
				
				switch(dict) {
				/* 下 */
				case "s":
					player_point[1]++;
					break;
				/* 上 */
				case "w":
					player_point[1]--;
					break;
				/* 左 */
				case "a":
					player_point[2]--;
					break;
				/* 右 */
				case "d":
					player_point[2]++;
					break;
				/* テレポート */
				case "t":
					player_point = Teleport(map,player_point);
					break;
				/* アイテム */
				case "i":
					//アイテムを使う
					item item = si.method(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal);
					player_status = item.player_status;
					player_efficacy = item.player_efficacy;
					player_belonging = item.player_belonging;
					skip = 1;
					break;
				/* ログアウト */
				case "l":
					error = "no";
					skip = 1;
					break;
				/* admin */
				case "@":
					admin admin = sa.Authority(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal);
					name = admin.player_name;
					player_status = admin.player_status;
					player_efficacy = admin.player_efficacy;
					player_skil = admin.player_skil;
					player_belonging = admin.player_belonging;
					skip = 1;
					break;
				/* ヘルプ */
				case "?":
					output.help();
					skip = 1;
					break;
				/* その他 */
				default:
					skip = 1;
				}
				/* ログアウト以外 */
				if(!(enter.equals("logout"))) {
					if(map[player_point[0]][player_point[1]][player_point[2]] =="■") {
						if(skip>1) {
							error = "no";
						}else {
							System.out.println("壁に当たったよ");
							error = "redo";
						}
						switch(dict) {
							case "s":
								player_point[1]--;
								break;
							case "w":
								player_point[1]++;
								break;
							case "a":
								player_point[2]++;
								break;
							case "d":
								player_point[2]--;
								break;
						}
						
					}else {
						//ステータスの自動回復
						if(!(dict.equals("i"))) {
							//HPの調整
							if(player_status[3] > player_status[4]) {
								player_status[3] = player_status[4];
							}
							
							//スキルのクールタイム減少
							int hosei = 1;
							switch(name[1]) {
							case "剣士":
								hosei = 3;
								break;
							case "フェンサー":
								hosei = 2;
								break;
							}
							skil move = ss.move(player_skil, hosei);
							player_skil = move.player_skil;
							
							// 効果の残りターンの減少
							/* 武器 */
							if(player_efficacy[0][1] != 0) {
								player_efficacy[0][1] --;
								if(player_efficacy[0][1] <= 0) {
									player_status[3] -= player_efficacy[0][0];
									player_status[4] -= player_efficacy[0][0];
									player_efficacy[0][0] = 0;
									player_efficacy[0][1] = 0;
								}
							}
							if(player_efficacy[1][1] != 0) {
								player_efficacy[1][1] --;
								if(player_efficacy[1][1] <= 0) {
									player_status[5] -= player_efficacy[1][0];
									player_efficacy[1][0] = 0;
									player_efficacy[1][1] = 0;
								}
							}
							if(player_efficacy[2][1] != 0) {
								player_efficacy[2][1] --;
								if(player_efficacy[2][1] <= 0) {
									player_status[6] -= player_efficacy[2][0];
									player_efficacy[2][0] = 0;
									player_efficacy[2][1] = 0;
								}
							}
							if(player_efficacy[3][1] != 0) {
								player_efficacy[3][1] --;
								if(player_efficacy[3][1] <= 0) {
									player_status[6] -= player_efficacy[3][0];
									player_efficacy[3][0] = 0;
									player_efficacy[3][1] = 0;
								}
							}
							if(player_efficacy[4][1] != 0) {
								player_efficacy[4][1] --;
								if(player_efficacy[4][1] <= 0) {
									player_status[7] -= player_efficacy[4][0];
									player_efficacy[4][0] = 0;
									player_efficacy[4][1] = 0;
								}
							}
							/* アイテム */
							if(player_efficacy[5][1] != 0) {
								player_efficacy[5][1] --;
								if(player_efficacy[5][1] <= 0) {
									player_status[5] -= player_efficacy[5][0];
									player_efficacy[5][0] = 0;
									player_efficacy[5][1] = 0;
								}
							}
							if(player_efficacy[6][1] != 0) {
								player_efficacy[6][1] --;
								if(player_efficacy[6][1] <= 0) {
									player_status[6] -= player_efficacy[6][0];
									player_efficacy[6][0] = 0;
									player_efficacy[6][1] = 0;
								}
							}
							if(player_efficacy[7][1] != 0) {
								player_efficacy[7][1] --;
								if(player_efficacy[7][1] <= 0) {
									player_status[7] -= player_efficacy[7][0];
									player_efficacy[7][0] = 0;
									player_efficacy[7][1] = 0;
								}
							}
							if(player_efficacy[8][1] != 0) {
								player_efficacy[8][1] --;
								if(player_efficacy[8][1] <= 0) {
									player_status[4] -= player_efficacy[8][0];
									player_efficacy[8][0] = 0;
									player_efficacy[8][1] = 0;
								}
							}
							
							/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
							5…攻撃力、6…素早さ、7…防御力、8…所持金、9…コロシアム連勝記録]*/
						}
						//イベント確認
						String event_name = map[player_point[0]][player_point[1]][player_point[2]];
						switch(event_name) {
							case "M":			//マーケット
								event = "M";
								break;
							case "D":			//ダンジョン
								event = "D";
								break;
							case "K":			//換金所
								event = "K";
								break;
							case "V":			//コロシアム
								event = "V";
								break;
							case "W":			//転職
								event = "W";
								break;
							case "A":			//アビリティ強化
								event = "A";
								break;
							case "S":			//スキル収得
								event = "S";
								break;
							case "H":			//home
								event = "H";
								break;
							case "I":			//アイテム
								event = "I";
								break;
							case "T":			//敵
								event = "T";
								break;
							case "G":			//ゴール
								event = "G";
								break;
							case "B":			//ボス
								event = "B";
								break;
							case "R":			//ルーレット
								event = "R";
								break;
							case " ":			//道
								event = "no";
								break;
							case "!":			//道
								event = "!";
								break;
						}
						error = "no";
						if(skip>1) {
							if(event == "no") {
								error = "repeat";
							}
						}
					}
				}
			}
						
		}
		if(enter.equals("logout")) {
			System.out.print("Logoutしますか？《y or n入力》 ");
			enter = br.readLine();
			if(enter.equals("y")) {			//logoutを入力時の処理
				logout = true;
			}
		}
		output.player_point = player_point;
		output.player_status = player_status;
		output.player_efficacy = player_efficacy;
		output.player_skil = player_skil;
		output.player_belonging = player_belonging;
		output.player_arsenal = player_arsenal;
		output.event = event;
		output.logout = logout;
		return output;
	}
	/*****************************************************************************************
	 * テレポート時のプログラム
	 * 
	 * 『Z座標について』
	 * 0・・・スタートフロア、1・・・マーケット内、2・・・ダンジョン（Bose部屋以外）、3・・・Bose部屋
	 *****************************************************************************************/
	public static int[] Teleport(String[][][] map, int[] player_point) throws IOException {
		
		String print_x = "X座標を入力";
		String print_y = "Y座標を入力";
		String print_z = "Z座標を入力\n0・・・スタートフロア、1・・・マーケット内、2・・・ダンジョン（Bose部屋以外）、3・・・Bose部屋";
		
		String error = null;
		do {
			player_point[0]=enter_number.method(print_z);
			if(player_point[0] > map.length) {
				System.out.println("無効な数字が入力されています。");
				error = "redo";
			}else {
				error = null;
			}
		}while(error == "redo");
		do {
			player_point[1]=enter_number.method(print_y);
			if(player_point[1] > map[player_point[0]].length) {
				System.out.println("無効な数字が入力されています。");
				error = "redo";
			}else {
				error = null;
			}
		}while(error == "redo");
		do {
			player_point[2]=enter_number.method(print_x);
			if(player_point[2] > map[player_point[0]][player_point[1]].length) {
				System.out.println("無効な数字が入力されています。");
				error = "redo";
			}else {
				error = null;
			}
		}while(error == "redo");
		
		return player_point;
	}
	/*****************************************************************************************
	 * ヘルプの参照プログラム
	 *****************************************************************************************/
	public static void help() throws IOException {
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String redo = "y";
		int num = 0;
		
		System.out.println("▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽　ヘルプ　▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽\n");
		
		while(redo.equals("y")) {
			String print = "マーケットについて\n"
					+ "[1]\t武器作成\n[2]\tアイテムショップ\n[3]\tスキル修練場\n[4]\tホーム\n"
					+ "ダンジョンについて\n[5]\tアイテム\n[6]\t敵\n[7]\tボス\n[8]\tバトル\n"
					+ "知りたい項目番号を入力してください。《0でキャンセル》 >>>";
			num = enter_number.method(print);
			if(num != 0) {
				switch (num) {
				case 1:
					System.out.println("武器作成について\n"
							+ "武器を作成するには、武器ごとに決められた素材（鉄鉱石、白銀鉱石、プラチム鉱石、\n"
							+ "黄金石）の必要数と武器ごとに決められたGoldが必要です。\n"
							+ "その他、自分よりレベルの高い武器は作成できません。\n"
							+ "武器を購入すると武器に付与されたステータス値が自分のステータスに追加されます。\n"
							+ "武器には耐久値が設定されています。耐久値は移動すると同時に減っていきます。\n"
							+ "耐久値が0になると自分のステータスから武器のステータス値が引かれます。");
					break;
				case 2:
					System.out.println("アイテムショップについて\n"
							+ "8種類のポーションと11種類の結晶を購入することが出来ます。\n"
							+ "アイテムごとに必要Goldが異なり、所持Gold以上のアイテムは購入できません");
					break;
				case 3:
					System.out.println("スキル修練場について\n"
							+ "レベルが5以上になると、新しいスキルの修得とスキルの強化が行なえます。\n"
							+ "新しいスキルの修得には1500Gold、スキルの強化には1000Goldが必要です。\n"
							+ "新しいスキルの修得は自分のレベルに応じて新しくスキルを修得できます。\n"
							+ "スキルの強化は熟練度が20の倍数を超えるごとにスキルの強化ができます。\n"
							+ "強化には、威力の30上昇や連撃数の1増加、クールタイムの5ターン減少があります。\n"
							+ "なお、強化回数には上限があり、上限を超えたスキルは強化ができません。");
					break;
				case 4:
					System.out.println("ホームについて\n"
							+ "マーケットからメインマップに移動します。");
					break;
				case 5:
					System.out.println("アイテムについて\n"
							+ "アイテムを拾います。\n"
							+ "持ち物の最大所持数を超えた場合アイテムを拾うことができません。\n"
							+ "アイテムの種類は主に2種類で、一つはポーションや結晶のプレイヤー強化、\n"
							+ "もう一つは、鉄鉱石や白銀鉱石などの武器作成に必要な素材です。\n"
							+ "自分のレベルが上がるにつれて質の良いアイテムを拾うことができます。");
					break;
				case 6:
					System.out.println("敵について\n"
							+ "敵と対決します。\n"
							+ "自分のレベルが上がるにつれて敵も強くなります。\n"
							+ "敵に敗れた場合、所持金が減り、メインマップに戻ります。");
					break;
				case 7:
					System.out.println("ボスについて\n"
							+ "ボスと対決します。\n"
							+ "自分のレベルが上がるにつれて敵も強くなります。\n"
							+ "ボスに勝利するとダンジョン探索成功です。");
					break;
				case 8:
					System.out.println("バトルについて\n"
							+ "AGIの高い方が相手に攻撃ができます。\n"
							+ "攻撃手段は通常攻撃とスキル攻撃の二種類です。\n"
							+ "スキル攻撃は選択したスキルの威力と通常攻撃の半分の威力に連撃数を\n"
							+ "掛けたダメージを与えます。使用したスキルは熟練度が+1されます。熟練度\n"
							+ "が5上がるごとに威力が3UPします。バトル中のクールタイム減少量は5です。\n"
							+ "与えるダメージの総量はダメージから相手のVITを引いた値になります。");
					break;
				}
				
				System.out.print("参照を続けますか？ 《y or n》 >>>");
				redo = br.readLine();
			}else {
				redo = "n";
			}
		}
		System.out.println("\n▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽▲▽");
	}
}


