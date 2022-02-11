import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class item {
	int[] player_status;
	int[][] player_efficacy;
	String[] player_belonging;
	public item method (String[] name, int[] pl_status, int[][] pl_efficacy, int[][] pl_skil, 
			String[] pl_belonging, int[] pl_arsenal) throws IOException {
		
		item output = new item();
		
		main main = new main();
		SAO_use_item ui = new SAO_use_item();
		
		boolean redo = true;
		String use;
		
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		
		//ステータスとスキルの表示
		System.out.println("\nプレイヤー情報");
		main.on_display_name(name);
		main.on_display_efficacy(pl_efficacy);	//efficacyに関連した場所でデータがnullになっている
		main.on_display_skil(pl_skil);
		main.on_display_item(pl_belonging);
		main.on_display_arsenal(pl_arsenal);
		
		
		if(!(pl_belonging[0].equals("no")))	{	//アイテムを所持していたら表示
			System.out.print("アイテムを使用しますか？《y or n どちらか入力》\t>>>");
			use = br.readLine();
			if(use.equals("n")) {
				redo = false;
			}
			while(redo) {
				main.on_display_item(pl_belonging);
				
				/* アイテムの使用 */
				SAO_use_item use_item = ui.method(pl_status, pl_efficacy, pl_belonging);
				pl_status = use_item.player_status;
				pl_efficacy = use_item.player_efficacy;
				pl_belonging = use_item.player_belonging;
				/* アイテムの連続使用 */
				System.out.print("アイテムを続けて使用しますか？《y or n どちらか入力》\t>>>");
				use = br.readLine();
				if(use.equals("n")) {
					redo = false;
				}
			}
		}else {
			System.out.print("アイテムを所持していません");
		}
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_belonging = pl_belonging;
		return output;
	}
}
/****************************************************************************************
 * 持ち物を使用するときのプログラム
 * item_display使用時
 ****************************************************************************************/
class SAO_use_item{
	int[] player_status;
	int[][] player_efficacy;
	String[] player_belonging;
	public SAO_use_item method(int[] pl_status, int[][] pl_efficacy, String[] pl_belonging) throws IOException {
		SAO_use_item output = new SAO_use_item();
		//使用する持ち物の選択
		int item_num = 0;
		String print_item = null;
		boolean hyouji = true;
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
		while(true) {
			String print = "使用するアイテムを番号で入力《0でキャンセル》>>>";
			item_num = enter_number.method(print)-1;
			if(item_num>=-1 && item_num <= pl_belonging.length) {
				break;
			}
		}
		if(item_num != -1) {
			/* 選択したアイテムが使用できるか判定 */
			switch(pl_belonging[item_num]) {
			case "00":
				pl_status[3] += 30;
				print_item = "HPを30回復しました";
				break;
			case "01":
				pl_status[3] += 50;
				print_item = "HPを50回復しました";
				break;
			case "02":
				pl_status[3] += 100;
				print_item = "HPを100回復しました";
				break;
			case "03":
				pl_status[3] += 250;
				print_item = "HPを250回復しました";
				break;
			case "04":
				if(pl_efficacy[5][0] != 0) {
					pl_status[5] -= pl_efficacy[5][0];
				}
				pl_status[5] += 50;
				pl_efficacy[5][0] = 50;
				pl_efficacy[5][1] = 20;
				print_item = "一定時間の間STRを50増加します";
				break;
			case "05":
				if(pl_efficacy[6][0] != 0) {
					pl_status[6] -= pl_efficacy[6][0];
				}
				pl_status[6] += 30;
				pl_efficacy[6][0] = 30;
				pl_efficacy[6][1] = 20;
				print_item = "一定時間の間AGIを30増加します";
				break;
			case "06":
				if(pl_efficacy[7][0] != 0) {
					pl_status[7] -= pl_efficacy[7][0];
				}
				pl_status[7] += 30;
				pl_efficacy[7][0] = 30;
				pl_efficacy[7][1] = 20;
				print_item = "一定時間の間VITを30増加します";
				break;
			case "07":
				pl_status[3] += pl_status[4] * 3 / 10;
				print_item = "最大HPの30％回復しました";
				break;
			case "08":
				pl_status[3] += pl_status[4] * 6 / 10;
				print_item = "最大HPの60％回復しました";
				break;
			case "09":
				pl_status[3] = pl_status[4];
				print_item = "HPを100％回復しました";
				break;
			case "10":
				if(pl_efficacy[5][0] != 0) {
					pl_status[5] -= pl_efficacy[5][0];
				}
				pl_efficacy[5][0] = pl_status[5];
				pl_efficacy[5][1] = 40;
				pl_status[5] += pl_efficacy[5][0];
				print_item = "一定時間の間STRが2倍になります";
				break;
			case "11":
				if(pl_efficacy[6][0] != 0) {
					pl_status[6] -= pl_efficacy[6][0];
				}
				pl_efficacy[6][0] = pl_status[6];
				pl_efficacy[6][1] = 40;
				pl_status[6] += pl_efficacy[6][0];
				print_item = "一定時間の間AGIが2倍になります";
				break;
			case "12":
				if(pl_efficacy[7][0] != 0) {
					pl_status[7] -= pl_efficacy[7][0];
				}
				pl_efficacy[7][0] = pl_status[7];
				pl_efficacy[7][1] = 40;
				pl_status[7] += pl_efficacy[7][0];
				print_item = "一定時間の間VITが2倍になります";
				break;
			case "25":
				if(pl_efficacy[8][0] != 0) {
					pl_status[4] -= pl_efficacy[8][0];
				}
				pl_status[3] += 80;
				pl_status[4] += 80;
				pl_efficacy[8][0] = 80;
				pl_efficacy[8][1] = 20;
				print_item = "一定時間の間HPを80増加します";
				break;
			case "26":
				if(pl_efficacy[8][0] != 0) {
					pl_status[4] -= pl_efficacy[8][0];
				}
				pl_efficacy[8][0] = pl_status[4];
				pl_efficacy[8][1] = 40;
				pl_status[3] += pl_efficacy[8][0];
				pl_status[4] += pl_efficacy[8][0];
				print_item = "一定時間の間HPが2倍になります";
				break;
			case "27":
				if(pl_efficacy[5][0] != 0) {
					pl_status[5] -= pl_efficacy[5][0];
				}
				pl_efficacy[5][0] = pl_status[5] * 2;
				pl_efficacy[5][1] = 60;
				pl_status[5] += pl_efficacy[5][0];
				print_item = "一定時間の間STRが3倍になります";
				break;
			case "28":
				if(pl_efficacy[6][0] != 0) {
					pl_status[6] -= pl_efficacy[6][0];
				}
				pl_efficacy[6][0] = pl_status[6] * 2;
				pl_efficacy[6][1] = 60;
				pl_status[6] += pl_efficacy[6][0];
				print_item = "一定時間の間AGIが3倍になります";
				break;
			case "29":
				if(pl_efficacy[7][0] != 0) {
					pl_status[7] -= pl_efficacy[7][0];
				}
				pl_efficacy[7][0] = pl_status[7] * 2;
				pl_efficacy[7][1] = 60;
				pl_status[7] += pl_efficacy[7][0];
				print_item = "一定時間の間VITが3倍になります";
				break;
			case "30":
				if(pl_efficacy[8][0] != 0) {
					pl_status[4] -= pl_efficacy[8][0];
				}
				pl_efficacy[8][0] = pl_status[4] * 2;
				pl_efficacy[8][1] = 60;
				pl_status[3] += pl_efficacy[8][0];
				pl_status[4] += pl_efficacy[8][0];
				print_item = "一定時間の間HPが3倍になります";
				break;
			default:
				hyouji = false;
			}
			/*
			player_status = 
			[0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
				5…攻撃力、6…素早さ、7…防御力、8…所持金、9…コロシアム連勝記録]
			
			player_efficacy = 
			[0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
			5…攻撃力、6…素早さ、7…防御力][0…効果、1…残り期間]
			*/
			
			
			if(hyouji == false) {
				System.out.println("このアイテムは現在使用できません！");
			}else {
				System.out.println(item_all_name[Integer.parseInt(pl_belonging[item_num])][1] 
						+ "を使用しました\n" + print_item);
				pl_belonging[item_num] = "no";
			}
		}
		
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_belonging = pl_belonging;
		return output;
	}
}

