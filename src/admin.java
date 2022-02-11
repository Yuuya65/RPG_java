import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class admin {
	String[] player_name;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	int[] player_arsenal;
	String[][][] main_map;
	
	public admin Authority(String[] pl_name, int[] pl_status, int[][] pl_efficacy,
			int[][] pl_skil, String[] pl_belonging, int[] pl_arsenal) throws IOException {
		admin output = new admin();
		to_edit te = new to_edit();
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String code = null;
		String repeat = "y";
		
		code = br.readLine();
		
		if(code.equals("admin")) {
			while(repeat.equals("y")) {
				System.out.println("Please enter the location to edit.(First 3 letters)>>>");
				code = br.readLine();
				
				switch(code) {
				case "nam":
					to_edit nam = te.name(pl_name);
					pl_name = nam.player_name;
					break;
				case "sta":
					to_edit sta = te.status(pl_status, pl_name);
					pl_status = sta.player_status;
					break;
				case "ski":
					to_edit ski = te.skil(pl_skil);
					pl_skil = ski.player_skil;
					break;
				case "bel":
					to_edit bel = te.belonging(pl_belonging);
					pl_belonging = bel.player_belonging;
					break;
				case "ars":
					to_edit ars = te.arsenal(pl_arsenal);
					pl_arsenal = ars.player_arsenal;
					break;
				case "map":
					to_edit map = te.map(main_map);
					main_map = map.main_map;
				}
				
				System.out.print("Do you want to continue using administrator privileges? (y or n) >>>");
				repeat = br.readLine();
			}
		}
		
		output.player_name = pl_name;
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_skil = pl_skil;
		output.player_belonging = pl_belonging;
		output.player_arsenal = pl_arsenal;
		return output;
	}
}
class to_edit{
	String[] player_name;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	String[] player_belonging;
	int[] player_arsenal;
	String[][][] main_map;
	/********************************************************************************
	 * name
	 ********************************************************************************/
	public to_edit name(String[] pl_name) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String[] select_name = {
				"���m","�t�F���T�[","�f�B�t�F���_�[","�g���x���[","�M��",
		};
		String code = null;
		String repeat = "y";
		int num = 0;
		
		while(repeat.equals("y")) {
			main.on_display_name(pl_name);
			
			System.out.print("Please enter the item to edit. "
					+ "name or class >>>");
			code = br.readLine();
			
			switch(code) {
			case "name":
				System.out.print("Please enter the letter. >>>");
				pl_name[0] = br.readLine();
				break;
			case "class":
				while(true) {
					String print = "Please enter the item to edit.\n"
							+ "[1] ���m  [2] �t�F���T�[  [3] �f�B�t�F���_�[  [4] �g���x���[  [5] �M�� >>>";
					num = enter_number.method(print);
					if(num <= 5 && num > 0) {
						break;
					}
				}
				num -= 1;
				pl_name[1] = select_name[num];
				break;
			}
			
			System.out.print("Do you want to continue editing the name? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_name = pl_name;
		return sta_output;
	}
	/********************************************************************************
	 * status
	 ********************************************************************************/
	public to_edit status(int[] pl_status, String[] pl_name) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String code = null;
		String repeat = "y";
		int value;
		boolean edit = true;
		
		while(repeat.equals("y")) {
			main.on_display_status(pl_status, pl_name[0]);
			int place = -1;
			System.out.print("Please enter the item to edit.\n"
					+ "Lv or HP or STR or AGI or VIT or Gold or record>>>");
			
			code = br.readLine();
			
			switch(code) {
			case "Lv":
				place = 0;
				break;
			case "HP":
				place = 3;
				break;
			case "STR":
				place = 5;
				break;
			case "AGI":
				place = 6;
				break;
			case "VIT":
				place = 7;
				break;
			case "Gold":
				place = 8;
				break;
			case "record":
				place = 9;
				break;
			default:
				edit = false;
			}
			
			if(edit == true) {
				String print = "Please enter the number.>>>";
				value = enter_number.method(print);
				pl_status[place] = value;
				if(place == 3) {
					pl_status[4] = value;
				}else if(place == 9){
					if(value >= 49){
						pl_status[place] = 49;
					}
				}
			}
			System.out.print("Do you want to continue editing the status? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_status = pl_status;
		return sta_output;
	}
	/********************************************************************************
	 * skil
	 ********************************************************************************/
	public to_edit skil(int[][] pl_skil) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String[] all_skil_name = {
				"�X�����g","�J�^���N�g","�o�[�`�J��",
				"�o�[�`�J���E�A�[�N","�\�j�b�N���[�u","�V���[�v�l�C��",
				"�g���C�A���M�����[","�j���[�g����","�N���[�V�t�B�N�V����",		
				"���e�I�u���C�N","�X�^�[�E�X�v���b�V��","�m���@�E�A�Z���V����",
				"�}�U�[�Y�E���U���I","�X�^�[�o�[�X�g�E�X�g���[��",
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
				
				{12,0,55,600,11,150,0,999},
				{13,0,60,650,16,150,0,999},
		};
		
		String repeat = "y";
		
		while(repeat.equals("y")) {
			int count = 0;
			int[][] count_id = new int[14][1];
			int num = 0;		//�C������X�L���ԍ�
			int num2 = 0;		//����ւ���̃X�L���ԍ�
			boolean redo = true;
			boolean redo2 = true;
			boolean skil_max = true;
			
			main.on_display_skil(pl_skil);
			System.out.println("all_skil_name");
			for(int n = 0;n < all_skil_name.length;n++) {
				boolean have = false;
				for(int m = 0;m < 4;m++) {
					if(all_skil_status[n][0] == pl_skil[m][0]) {		//�\���X�L�������łɏ������Ă���ꍇ
						have = true;
					}
				}
				if(have == false) {
					count++;
					System.out.println("[" + count + "]\t" + all_skil_name[n] +"\n\t�З�_"
							+ all_skil_status[n][3] + "\t�ő�N�[���^�C��_" + all_skil_status[n][2] 
							+ "\t�ő�A����_" + all_skil_status[n][4]);
					count_id[count][0] = all_skil_status[n][0];
				}
			}
			while(true) {
				String print = "Enter the number of the skil you want to acquire.�s0=Cancel�t>>>";
				num = enter_number.method(print);
				if(num >= 0 && num <= count) {
					break;
				}else {
					System.out.println("Re-enter");
				}
			}
			if(num != 0) {
				for(int m = 0;m < 4;m++) {
					if(pl_skil[m][3] == 0) {
						pl_skil[m] = all_skil_status[count_id[num][0]];
						pl_skil[m][5] = 1;
						skil_max = false;
						break;
					}
				}
				if(skil_max == true) {
					System.out.println("The frame is full");
					count = 0;
					for(int m = 0;m < 4;m++) {
						count++;
						System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t�З�_"
								+ pl_skil[m][3] + "\t�ő�N�[���^�C��_" + pl_skil[m][2] 
								+ "\t�ő�A����_" + pl_skil[m][4] + "\t�n���x_" + pl_skil[m][5]);
					}
					while(redo2) {
						String print2 = "Please enter the my skil number to be exchanged.�s0=Cancel�t>>>";
						num2 = enter_number.method(print2);
						if(num2 >= 0 && num2 <= 4) {
							redo2 = false;
						}else {
							System.out.println("Re-enter");
						}
					}
					if(num2 != 0) {
						pl_skil[num2-1] = all_skil_status[count_id[num][0]];
						pl_skil[num2-1][5] = 1;
					}
				}
			}
			
			System.out.print("Do you want to continue editing the skil? (y or n)>>>");
			repeat = br.readLine();
		}
		
		sta_output.player_skil = pl_skil;
		return sta_output;
	}
	/********************************************************************************
	 * belonging
	 ********************************************************************************/
	public to_edit belonging(String[] pl_belonging) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String[][] item_all_name = {
				{"00","�|�[�V����\t","HP��30��",},
				{"01","�n�C�|�[�V����","HP��50��",},
				{"02","�G�X�g�|�[�V����","HP��100��",},
				{"03","�O�����|�[�V����","HP��250��",},
				{"04","STR�|�[�V����","��莞�Ԃ̊�STR��50����",},
				{"05","AGI�|�[�V����","��莞�Ԃ̊�AGI��30����",},
				{"06","VIT�|�[�V����","��莞�Ԃ̊�VIT��30����",},
				{"07","��������\t","�ő�HP��30����",},
				{"08","�񕜌���\t","�ő�HP��60����",},
				{"09","�S������\t","HP��100����",},
				{"10","��������\t","��莞�Ԃ̊�STR��2�{",},
				{"11","�V������\t","��莞�Ԃ̊�AGI��2�{",},
				{"12","��猋��\t","��莞�Ԃ̊�VIT��2�{",},
				{"13","�S�z��\t","�S�z��1��",},
				{"14","�S�z��\t","�S�z��3��",},
				{"15","�S�z��\t","�S�z��5��",},
				{"16","����z��\t","����z��1��",},
				{"17","����z��\t","����z��3��",},
				{"18","����z��\t","����z��5��",},
				{"19","�v���`���z��","�v���`���z��1��",},
				{"20","�v���`���z��","�v���`���z��3��",},
				{"21","�v���`���z��","�v���`���z��5��",},
				{"22","������\t","������1��",},
				{"23","������\t","������3��",},
				{"24","������\t","������5��",},
				{"25","HP�|�[�V����","��莞�Ԃ̊�HP��80����",},
				{"26","��������\t","��莞�Ԃ̊�HP��2�{",},
				{"27","���������[��","��莞�Ԃ̊�STR��3�{",},
				{"28","�V�������[��","��莞�Ԃ̊�AGI��3�{",},
				{"29","��猋���[��","��莞�Ԃ̊�VIT��3�{",},
				{"30","���������[��","��莞�Ԃ̊�HP��3�{",},
		};
		
		String code = null;
		String repeat = "y";
		int value;
		boolean edit = true;
		int item_num;
		boolean belonging_max = true;
		
		while(repeat.equals("y")) {
			
			System.out.println("Show your belongings.");
			main.on_display_item(pl_belonging);
			
			System.out.println("Show all_item belongings.");
			for(int a = 0;a < item_all_name.length;a++) {
				System.out.println("["+(a+1)+"]\t"+item_all_name[a][1]+"\t: "+item_all_name[a][2]);
			}
			
			while(true) {
				String print = "Please enter the item number.�s0=Cancel�t>>>";
				item_num = enter_number.method(print);
				if(item_num >= 0 && item_num <= 31) {
					break;
				}
			}
			item_num -= 1;
			
			if(item_num != -1) {
				for(int n = 0;n < pl_belonging.length;n++) {
					if(pl_belonging[n].equals("no")) {
						pl_belonging[n] = item_all_name[item_num][0];
						belonging_max = false;
						break;
					}
				}
				if(belonging_max == true) {
					System.out.println("Item could not be obtained.");
				}
			}
			
			System.out.print("Do you want to continue editing the belonging? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_belonging = pl_belonging;
		return sta_output;
	}
	/********************************************************************************
	 * arsenal
	 ********************************************************************************/
	public to_edit arsenal(int[] pl_arsenal) throws IOException {
		to_edit sta_output = new to_edit();
		main main = new main(); 
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String repeat = "y";
		
		int num = 0;
		boolean edit = true;
		
		while(repeat.equals("y")) {
			main.on_display_arsenal(pl_arsenal);
			while(true) {
				String print = "Please enter the item to edit.\n"
						+ "[1] �S�z��  [2] ����z��  [3] �v���`���z��  [4] ������ >>>";
				num = enter_number.method(print);
				if(num <= 4 && num > 0) {
					break;
				}
			}
			String print = "Please enter the number you want to change. >>>";
			int value = enter_number.method(print);
			
			switch (num) {
			case 1:
				pl_arsenal[1] = value;
				break;
			case 2:
				pl_arsenal[2] = value;
				break;
			case 3:
				pl_arsenal[3] = value;
				break;
			case 4:
				pl_arsenal[4] = value;
				break;
			}
			
			System.out.print("Do you want to continue editing the arsenal? (y or n) >>>");
			repeat = br.readLine();
		}
		
		sta_output.player_arsenal = pl_arsenal;
		return sta_output;
	}
	/*************************************************************************************
	 * map
	 *************************************************************************************/
	public to_edit map(String[][][] map) throws IOException {
		to_edit map_output = new to_edit();
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String repeat = "y";
		String choose = "n";
		
		while(repeat.equals("y")) {
			if(map[0][6][2].equals(" ")) {
				System.out.print("Do you want to display the administrator function of the main map? (y or n) >>>");
				choose = br.readLine();
				if(choose.equals("y")) {
					map[0][6][2] = "!";
				}
			}else {
				System.out.print("Do you want to hide the admin function of the main map? (y or n) >>>");
				choose = br.readLine();
				if(choose.equals("y")) {
					map[0][6][2] = " ";
				}
			}
			
			System.out.print("Do you want to continue editing the map? (y or n) >>>");
			repeat = br.readLine();
		}
		map_output.main_map = map;
		return map_output;
	}
}
