import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/******************************************************************
 * ��Ƒ�
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
		
		
		String[] name = {"aaa", "���m"};
		int[] status = {
				13,0,130,220,220,140,120,69,5000,0
		};
		/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A9�c�R���V�A���A���L�^]*/
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
		/*[][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���\�^�C���A3�c�З́A4�c�ő�R���{���A
		  5�c�n���x�A6�c�����񐔁A7�c�ő勭����]*/
		
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
		{"00","�|�[�V����\t","HP��30��",},
		{"01","�n�C�|�[�V����\t","HP��50��",},
		{"02","�G�X�g�|�[�V����","HP��100��",},
		{"03","�O�����|�[�V����","HP��250��",},
		{"04","STR�|�[�V����\t","��莞�Ԃ̊�STR��50����",},
		{"05","AGI�|�[�V����\t","��莞�Ԃ̊�AGI��30����",},
		{"06","VIT�|�[�V����\t","��莞�Ԃ̊�VIT��30����",},
		{"25","HP�|�[�V����\t","��莞�Ԃ̊�HP��80����",},
		
		{"07","��������\t","�ő�HP��30����",},
		{"08","�񕜌���\t","�ő�HP��60����",},
		{"09","�S������\t","HP��100����",},
		{"10","��������\t","��莞�Ԃ̊�STR��2�{",},
		{"11","�V������\t","��莞�Ԃ̊�AGI��2�{",},
		{"12","��猋��\t","��莞�Ԃ̊�VIT��2�{",},
		{"26","��������\t","��莞�Ԃ̊�HP��2�{",},
		
		{"27","���������[��\t","��莞�Ԃ̊�STR��3�{",},
		{"28","�V�������[��\t","��莞�Ԃ̊�AGI��3�{",},
		{"29","��猋���[��\t","��莞�Ԃ̊�VIT��3�{",},
		{"30","���������[��\t","��莞�Ԃ̊�HP��3�{",},
};
int[] item_buy_price = {
		250,500,1000,1500,1400,1400,1400,1400,
		520,1050,2000,2500,2500,2500,2500,
		3500,3500,3500,3500,
};
*/