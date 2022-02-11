import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/******************************************************************
 * 作業台
 ******************************************************************/
public class Workbench {
	String[] player_name;
	int[] player_status;
	int[][] player_skil;
	public static void main (String[] args) throws IOException {
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		use_K uk = new use_K();
		battle ba = new battle();
		town_event te = new town_event();
		dungeon d = new dungeon();
		main main = new main();
		
		
		String[] name = {"aaa", "剣士"};
		int[] status = {
				13,0,130,220,220,140,120,69,5000,0
		};
		/*[	0…レベル、1…現在の経験値、2…次のレベルアップに必要な残り経験値、3…現在のHP、4…最大のHP
		5…攻撃力、6…素早さ、7…防御力、8…所持金、9…コロシアム連勝記録]*/
		String[] belonging = {
				"01","28","01","01","12",
				"04","10","03","26","16",
				"12","12","13","22","01",
				"24","01","12","17","28",
		};
		int[][] skil = {
				{0,0,10,60,1,1,0,6},
				{8,0,55,200,6,75,0,24},
				{12,0,0,500,11,0,0,999},
				{13,0,0,950,200,0,0,999},
		};
		/*[][0…スキル番号、1…クールタイム、2…最大クール―タイム、3…威力、4…最大コンボ数、
		  5…熟練度、6…強化回数、7…最大強化回数]*/
		
		while(true) {
			String print = "Lv? >>>";
			//status[0] = enter_number.method(print);
			main.on_display_status(status, name[0]);
			dungeon teki = d.teki(status, skil, name);
			status = teki.player_status;
			skil = teki.player_skil;
		}
		
		/*
		while(true) {
			main.on_display_item(belonging);
			use_K cash = uk.Cash(name, status, belonging);
			status = cash.player_status;
			belonging = cash.player_belonging;
		}
		
		while(true) {
			main.on_display_status(status, name[0]);
			
			String print = "num>>";
			int value = enter_number.method(print);
			
			status = ba.win(status, name, value);
		}
		*/
	}
	public Workbench battle(int[] pl_status, int[][] pl_skil, String[] pl_name) throws IOException {
		Workbench output = new Workbench();
		
		battle ba = new battle();
		
		String te_name = "Monster";
		int[] te_status = {1,0,0,10,10,10,10,0,1,0};
		
		String print = "Enter the enemy level. >>>";
		te_status[8] = enter_number.method(print);
		
		battle wb = ba.method(pl_status, pl_skil, pl_name, te_status, te_name);
		pl_status = wb.pl_status;
		pl_skil = wb.pl_skil;
		
		output.player_name = pl_name;
		output.player_status = pl_status;
		output.player_skil = pl_skil;
		return output;
	}
}
/*
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
*/