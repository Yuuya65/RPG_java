import java.io.IOException;

public class all_event {
	int[] player_point;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	int[] player_arsenal;
	String game_over;
	String event;
	/******************************************************************************
	 * 移動に関するイベント
	 ******************************************************************************/
	public all_event move(String event, String[] pl_name, int[] pl_point, int[] pl_status,
			int[][] pl_efficacy, int[][] pl_skil, String[] pl_belonging, int[] pl_arsenal) throws IOException {
		
		all_event output = new all_event();
		town_event te = new town_event();
		dungeon dun = new dungeon();
		Workbench wb = new Workbench();
		
		String game_over = "no";
		
		/* イベント処理 */
		switch(event) {
		case "M":
			//マーケット内の初期座標
			pl_point[0] = 1;
			pl_point[1] = 5;
			pl_point[2] = 4;
			event = "end";
			break;
		case "D":
			System.out.println("ダンジョン");
			//ダンジョン内の初期座標
			pl_point[0] = 2;
			pl_point[1] = 1;
			pl_point[2] = 1;
			//ダンジョン内の処理
			dungeon dungeon = dun.RPG_dungeon(pl_point, pl_status, pl_efficacy, pl_skil, pl_name, pl_belonging, pl_arsenal, event);
			pl_point = dungeon.player_point;
			pl_status = dungeon.player_status;
			pl_efficacy = dungeon.player_efficacy;
			pl_skil = dungeon.player_skil;
			pl_belonging = dungeon.player_belonging;
			event = "end";
			break;
		case "K":
			System.out.println("換金所");
			town_event kankin = te.storage(pl_status, pl_belonging, pl_arsenal, pl_name);
			pl_belonging = kankin.player_belonging;
			pl_arsenal = kankin.player_arsenal;
			//処理終了後プレイヤーの位置を一つ手前に配置
			pl_point[1] = 3;
			event = "continue";
			break;
		case "V":
			//コロシアム内の初期座標
			pl_point[0] = 4;
			pl_point[1] = 6;
			pl_point[2] = 3;
			town_event Coliseum = te.Coliseum(pl_status, pl_efficacy, pl_skil, pl_name, pl_point, event, pl_belonging, pl_arsenal);
			pl_status = Coliseum.player_status;
			pl_efficacy = Coliseum.player_efficacy;
			pl_skil = Coliseum.player_skil;
			event = Coliseum.event;
			pl_point[0] = 0;
			pl_point[1] = 5;
			pl_point[2] = 6;
			event = "end";
			break;
			
		/* タウンイベント */
		case "W":
			System.out.println("武器作成");
			town_event buki = te.buki(pl_status, pl_efficacy, pl_arsenal);
			pl_status = buki.player_status;
			pl_efficacy = buki.player_efficacy;
			pl_arsenal = buki.player_arsenal;
			//処理終了後プレイヤーの位置を一つ手前に配置
			pl_point[1] = 3;
			event = "continue";
			break;
		case "A":
			System.out.println("アイテム購入\n");
			town_event ite = te.buy(pl_status, pl_belonging);
			pl_status = ite.player_status;
			pl_belonging = ite.player_belonging;
			//処理終了後プレイヤーの位置を一つ手前に配置
			pl_point[1] = 3;
			event = "continue";
			break;
		case "S":
			System.out.println("スキル習得\n");
			town_event skil = te.get_skil(pl_status, pl_skil);
			pl_status = skil.player_status;
			pl_skil = skil.player_skil;
			pl_point[1] = 7;
			event = "continue";
			break;
		case "H":
			//座標を戻す
			pl_point[0] = 0;
			pl_point[1] = 5;
			pl_point[2] = 6;
			event = "end";
			break;
			
		/* ダンジョンイベント */
		case "I":
			System.out.println("アイテム");
			dungeon item = dun.item(pl_status, pl_belonging);
			pl_belonging = item.player_belonging;
			pl_status = item.player_status;
			break;
		case "T":
			System.out.println("敵");
			dungeon teki = dun.teki(pl_status, pl_skil, pl_name);
			pl_status = teki.player_status;
			pl_skil = teki.player_skil;
			game_over = teki.game_over;
			break;
		case "G":
			System.out.println("Goal");
			break;	
		case "B":
			System.out.println("Bose");
			dungeon bose = dun.bose(pl_status, pl_skil, pl_name);
			pl_status = bose.player_status;
			pl_skil = bose.player_skil;
			game_over = bose.game_over;
			break;
		
		/* イベントなし */
		case "no":
			break;
		case  "!":
			System.out.println("Workbench");
			Workbench battle = wb.battle(pl_status, pl_skil, pl_name);
			pl_status = battle.player_status;
			pl_skil = battle.player_skil;
			pl_name = battle.player_name;
			pl_point[1] = 5;
			event = "end";
		}
		
		output.event = event;
		output.player_point = pl_point;
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_skil = pl_skil;
		output.player_belonging = pl_belonging;
		output.game_over = game_over;
		output.player_arsenal = pl_arsenal;
		return output;
	}
}


