import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class main {
	/********************************************************************************
	 * メインプログラム
	 ********************************************************************************/
	public static void main(String[] args) throws IOException {
		player_basic_data pbd = new player_basic_data();
		
		map pm = new map();
		location pl = new location();
		all_event em = new all_event();
		data_save sds = new data_save();
		map map = pm.method();
		
		/* プレイヤーのデータ */
		String[] name = pbd.name;
		String[] player_belonging = pbd.player_belonging;
		int[] player_status = pbd.player_status;
		int[][] player_efficacy = pbd.player_efficacy;
		int[][] player_skil = pbd.player_skil;
		int[] player_point = pbd.player_point;
		int[] player_arsenal = pbd.arsenal;
		boolean logout = pbd.logout;
		String[][][] main_map = map.map;
		
		/* データセーブの設定 */
		boolean Auto_save = true;	//
		
		/* イベント判定 */
		String event = "no";
		
		/* 初期　マップとステータスの表示 （最初の一回だけ）*/
		on_display_map(main_map[0],player_point);
		on_display_status(player_status,name[0]);
		
		while(logout == false) {
			main_map = map.map;
			
			/* プレイヤーの操作 */
			location player_location = pl.method(main_map, player_point, player_belonging, player_status, player_efficacy, player_skil, name, player_arsenal, event);
			player_point = player_location.player_point;
			player_belonging = player_location.player_belonging;
			player_status = player_location.player_status;
			player_efficacy = player_location.player_efficacy;
			player_skil = player_location.player_skil;
			player_arsenal = player_location.player_arsenal;
			event = player_location.event;
			logout = player_location.logout;
			
			if(logout == false) {	//ログアウトしていない場合はイベント処理を実行
				/* 移動操作後のマップとステータスの表示 */
				on_display_map(main_map[player_point[0]],player_point);
				on_display_status(player_status,name[0]);
				
				/* イベント処理を行なう */
				all_event main = em.move(event, name, player_point, player_status, player_efficacy, player_skil, player_belonging, player_arsenal);
				event = main.event;
				player_point = main.player_point;
				player_status = main.player_status;
				player_efficacy = main.player_efficacy;
				player_skil = main.player_skil;
				player_belonging = main.player_belonging;
				player_arsenal = main.player_arsenal;
				
				//System.out.println("main_last event>>> " + event);
			}
			/* Auto save　の実行 */
			data_save save_data = sds.data_save(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal, Auto_save);
			
		}
		Auto_save = false;
		data_save save_data = sds.data_save(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal, Auto_save);
		
		System.out.println("\n---------------------Logout---------------------");
	}
	/********************************************************************************
	 * マップを表示するプログラム
	 ********************************************************************************/
	public static void on_display_map(String[][] map_2d,int[] player_point) {
		System.out.println("\nマップ表示");
		String point = map_2d[player_point[1]][player_point[2]];	//プレイヤーの移動場所のイベント
		map_2d[player_point[1]][player_point[2]] = "○";				//プレイヤーの現在地
		for(int y = 0;y < map_2d.length;y++) {
			for(int x = 0;x < map_2d[y].length;x++) {
				System.out.print(map_2d[y][x]+" ");
			}System.out.println();
		}
		if(player_point[0] == 2) {									//ダンジョンの場合通った場所はイベントを消す
			map_2d[player_point[1]][player_point[2]] = " ";
		}else {														//移動後、イベントの表示を戻す
			map_2d[player_point[1]][player_point[2]] = point;
		}
		/* プレイヤーの現在地 */
		System.out.println("座標『"+player_point[0]+"."+player_point[1]+"."+player_point[2]+"』");
	}
	/********************************************************************************
	 * ステータスを表示するプログラム
	 ********************************************************************************/
	public static void on_display_status(int[] player_status, String name) {
		System.out.println(name + " Lv_" + player_status[0] + " HP_"+player_status[3] 
				+ "/" + player_status[4] + " STR_" + player_status[5] + " AGI_"
				+ player_status[6] + " VIT_" + player_status[7] + "\n Gold_"
				+ player_status[8] + " EXP_" + player_status[1] 
				+ " 次のレベルアップに必要な経験値 " + (player_status[2]-player_status[1]));
		//レベル、HP、最大HP、攻撃力、素早さ、防御力、お金、経験値、必要な経験値
	}
	/********************************************************************************
	 * ステータスを表示するプログラム
	 ********************************************************************************/
	public static void on_display_name(String[] name) {
		System.out.println("プレイヤーネーム:"+name[0]+"\tクラス:"+name[1]);
	}
	/********************************************************************************
	 * スキル一覧を表示するプログラム
	 ********************************************************************************/
	public static void on_display_skil(int[][] player_skil) {
		String[] all_skil_name = {
				"スラント","カタラクト","バーチカル",
				"バーチカル・アーク","ソニックリーブ","シャープネイル",
				"トライアンギュラー","ニュートロン","クルーシフィクション",		
				"メテオブレイク","スター・スプラッシュ","ノヴァ・アセンション",
				"マザーズ・ロザリオ","スターバースト・ストリーム",
		};
		int count = 0;
		System.out.println("所持スキル");
		for(int m = 0;m < 4;m++) {
			if(player_skil[m][3] != 0) {
				count++;
				System.out.println("[" + count + "]\t" + all_skil_name[player_skil[m][0]] +"\n\t威力_"
						+ player_skil[m][3] + "\t最大クールタイム_" + player_skil[m][2] 
						+ "\t最大連撃数_" + player_skil[m][4] + "\t熟練度_" + player_skil[m][5]);
			}
			
		}
		/*[][0…スキル番号、1…クールタイム、2…最大クール―タイム、3…威力、4…最大コンボ数、
		  5…熟練度、6…強化回数、7…最大強化回数]*/
	}
	/********************************************************************************
	 * 発動中の効果を表示するプログラム
	 ********************************************************************************/
	public static void on_display_efficacy(int[][] player_efficacy) {
		boolean buki = false;
		boolean item = false;
		
		System.out.println("装備中の武器による効果");
		if(player_efficacy[0][0] != 0) {
			System.out.println(player_efficacy[0][1] + "ターンの間HPが" + player_efficacy[0][0] + "UP");
			buki = true;
		}
		if(player_efficacy[1][0] != 0) {
			System.out.println(player_efficacy[1][1] + "ターンの間STRが" + player_efficacy[1][0] + "UP");
			buki = true;
		}
		if(player_efficacy[2][0] != 0) {
			System.out.println(player_efficacy[2][1] + "ターンの間AGIが" + player_efficacy[2][0] + "UP");
			buki = true;
		}
		if(player_efficacy[3][0] != 0) {
			System.out.println(player_efficacy[3][1] + "ターンの間AGIが" + player_efficacy[3][0] + "UP");
			buki = true;
		}
		if(player_efficacy[4][0] != 0) {
			System.out.println(player_efficacy[4][1] + "ターンの間VITが" + player_efficacy[4][0] + "UP");
			buki = true;
		}
		if(buki == false) {
			System.out.println("装備している武器はありません\n");
		}
		
		System.out.println("使用しているアイテムによる効果");
		if(player_efficacy[8][0] != 0) {
			System.out.println(player_efficacy[8][1] + "ターンの間HPが" + player_efficacy[8][0] + "UP");
			item = true;
		}
		if(player_efficacy[5][0] != 0) {
			System.out.println(player_efficacy[5][1] + "ターンの間STRが" + player_efficacy[5][0] + "UP");
			item = true;
		}
		if(player_efficacy[6][0] != 0) {
			System.out.println(player_efficacy[6][1] + "ターンの間AGIが" + player_efficacy[6][0] + "UP");
			item = true;
		}
		if(player_efficacy[7][0] != 0) {
			System.out.println(player_efficacy[7][1] + "ターンの間VITが" + player_efficacy[7][0] + "UP");
			item = true;
		}
		if(item == false) {
			System.out.println("発動中のアイテムはありません\n");
		}
		
		/*player_efficacy = [0…武器HP、1…武器STR、2…武器AGI、3…武器AGI、4…武器VIT、5…アイテムSTR、
		6…アイテムAGI、7…アイテムVIT、8…HP][0…効果(初期値＝0)、1…残り期間(初期値＝0)]*/
	}
	/********************************************************************************
	 * アイテムを表示するプログラム
	 ********************************************************************************/
	public static void on_display_item(String[] player_belonging) {
		
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
		
		/* アイテムの並び替え */
		int cou = 0;
		int amount;
		for(int n = 0;n < player_belonging.length;n++) {
			if(!(player_belonging[n].equals("no"))) {
				player_belonging[cou] = player_belonging[n];
				cou++;
			}
		}
		amount = cou;
		for(;cou < player_belonging.length;cou++) {
			player_belonging[cou] = "no";
		}
		
		
		System.out.println("所持しているアイテム");
		if(amount == 0) {
			System.out.println("持ち物は所持していません！");
		}else {
			for(int n = 0;n < amount;n++) {
				System.out.println("[" + (n + 1) + "]\t"
						+ item_all_name[Integer.parseInt(player_belonging[n])][1] + "\t: "
						+ item_all_name[Integer.parseInt(player_belonging[n])][2]);
			}
		}
	}
	/********************************************************************************
	 * 素材の数を表示するプログラム
	 ********************************************************************************/
	public static void on_display_arsenal(int[] player_arsenal) {
		
		System.out.println("所持しているインゴッド数");
		System.out.println("鉄鉱石 "+player_arsenal[1]+"個、白銀鉱石 "+player_arsenal[2]+"個、プラチム鉱石 "
				+player_arsenal[3]+"個、黄金石 "+player_arsenal[4]+"個、所持しています。");
		
		/* 0・・・鉄鉱石　1・・・白銀鉱石　2・・・プラチム鉱石　3・・・黄金石　4・・・なし　5・・・なし */
	}
	
}
/********************************************************************************
 * ゲームに必要なデータ
 ********************************************************************************/
class player_basic_data{
	String[] name = {"no","no",};
	//[0…名前、1…クラス]
	
	int[] player_status = new int[10];
	/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…所持金、9…コロシアム連勝記録]*/
	
	int[][] player_efficacy = new int[9][2];
	/*[0…武器HP、1…武器STR、2…武器AGI、3…武器AGI、4…武器VIT、	5…アイテムSTR、6…アイテムAGI、
	 　　7…アイテムVIT、8…アイテムHP][0…効果(初期値＝0)、1…残り期間(初期値＝0)]*/
	
	int[][] player_skil = new int[4][8];
	/*[][0…スキル番号、1…クールタイム、2…最大クール―タイム、3…威力、4…最大コンボ数、
	  5…熟練度、6…強化回数、7…最大強化回数]*/
	
	int[] player_point = new int[3];
	//[0…ｚ座標、1…ｙ座標、2…ｘ座標]
	
	String [] player_belonging = new String[20];
	//[番号][0~11…道具名]最大所持数12個まで
	
	int[] arsenal = new int[6];
	//[0~5…素材数]
	
	boolean logout = false;
	//false…ログイン、true…ログアウト
	
	/********************************************************************************
	 * 初期設定orデータ引継ぎ
	 ********************************************************************************/
	public player_basic_data() throws IOException{
		data_save sds = new data_save();
		
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		Random rand = new Random();
		int ran = rand.nextInt(100);
		
		//初期キャラ設定で使用
		String[] select_name = {
				"剣士","フェンサー","ディフェンダー","トラベラー","貴族",
		};
		int[][] select_status = {
				{1,0,50,90,90,70,40,20,500,0},
				{1,0,50,80,80,60,60,15,500,0},
				{1,0,50,100,100,40,30,30,500,0},
				{1,0,50,90,90,50,50,20,500,0},
				{1,0,50,80,80,40,30,15,5000,0},
		};
		/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…所持金、9…コロシアム連勝記録]*/
		
		
		System.out.print("名前を入力してください。>>>");
	    this.name[0] = br.readLine();
	    
	    //二回目以降、データの引継ぎで使用
	    data_save data_reading = sds.data_read(name[0]);
	    this.name = data_reading.player_name;
	    this.player_status = data_reading.player_status;
	    this.player_efficacy = data_reading.player_efficacy;
	    this.player_skil = data_reading.player_skil;
	    this.player_belonging = data_reading.player_belonging;
	    this.arsenal = data_reading.player_arsenal;
		
	    //初期キャラメイク
	    if(player_status[0] == 0) {
	    	System.out.println("\nあなたの能力を診断します。\nEnterキーを押してください。>>>");
			int select_num = (ran + 10) % 5;
			
			/* アイテムボックスの作成 */
			for(int n = 0;n < player_belonging.length;n++) {
				this.player_belonging[n] = "no";
			}
			
			/* ステータスの作成 */
			this.player_status = select_status[select_num];
			
			/* クラスの決定 */
			this.name[1] = select_name[select_num];
			
			/* 効果の作成 */
			for(int n = 0;n < 9;n++) {
				this.player_efficacy[n][0] = 0;
				this.player_efficacy[n][1] = 0;
			}
			
			/* スキルの作成 */
			int[] basic_skil = {0,0,10,60,1,1,0,6};	//基本スキル『スラント』
			for(int n = 0;n < 4;n++) {
				for(int m = 0;m < 8;m++) {
					this.player_skil[n][m] = 0;
				}
			}
			this.player_skil[0] = basic_skil;
	 		
			System.out.println("なるほど。あなたの天職は『"+select_name[select_num]+"』ですね。\n"
					+ "ようこそ "+name[0]+"さん。\n終わらない冒険の始まりです!!");
	    }else {
	    	System.out.println("\nおかえりなさい、"+name[0]+"さん。\n冒険の続きを始めましょう!!");
	    }
	    
	    this.player_point[0] = 0;
		this.player_point[1] = 5;
		this.player_point[2] = 6;
		
		System.out.println("\nマップについて\n"
				+ "○...現在地\tM...マーケット\tD...ダンジョン\nK...換金所\tV...コロシアム\tW...武器作成\n"
				+ "A...アイテムショップ\tH...ホーム\tS...スキル修練場\nG...ゴール\tI...アイテム\tT...敵   \tB...ボス");
	
	}	
}

