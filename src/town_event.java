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
	 * �������̃v���O����
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
				String print = "�悤�����������ցB�Ȃɂ����܂����H\n"
						+ "[1]\t�A�C�e���̊���\n[2]\t�C���S�b�h�̍쐬\n[3]\t�L�����Z���s1~3����́t\t>>>";
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
		System.out.print("������ꍇ�͂��L�[�A��߂�ꍇ��Enter�L�[������");
		
		output.player_belonging = pl_belonging;
		output.player_status = pl_status;
		output.player_arsenal = pl_arsenal;
		
		return output;
	}
	
	/******************************************************************************
	 * ����쐬�̃v���O����
	 ******************************************************************************/
	public town_event buki(int[] pl_status, int[][] pl_efficacy, int[] pl_arsenal) throws IOException {
		town_event output = new town_event();
		
		//�@����̖��O
		String[][] weapon_name = {
				{"�O���t�B���Y�R�[�g","AGI+5�AVIT+10�A�ϋv��150",},
				{"���F�[���A�[�}�[","HP+35�AVIT+40�A�ϋv��420",},
				{"�Z�C���c���C��","HP+150�AVIT+60�A�ϋv��460",},
				{"�u���b�N�E�B�����E�R�[�g","HP120�AAGI+210�A�ϋv��2750",},
				{"���C�m�N�X�A�[�}�[","HP+500�AVIT+350�A�ϋv��2800",},
				
				{"�A�C�A���\�[�h","STR+15�AAGI+5�A�ϋv��150",},
				{"�G���y���[�\�[�h","STR+40�AAGI+25�A�ϋv��600",},
				{"�I�u���r�I���G�b�W","STR+70�AAGI+100�A�ϋv��1300",},
				{"�G�����V�f�[�^","STR+120�AAGI+200�A�ϋv��2550",},
				{"�����e�B�����B���O","STR+350�AAGI+120�A�ϋv��2600",},
				{"�G�N�X�L�����o�[","STR+520�AAGI+270�A�ϋv��3800",},
				
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
		 * 0�E�E�EHP�̃X�e�[�^�X�@1�E�E�ESTR�̃X�e�[�^�X�@2�E�E�EAGI�̃X�e�[�^�X �R�E�E�EVIT�̃X�e�[�^�X
		 * 4�E�E�E�K�v�f��1�i0�c�Ȃ��@1�c�S�z�΁@2�c����z�΁@3�c�v���`���z�΁@4�c�����΁j�@5�E�E�E�f��1�̕K�v��
		 * 6�E�E�E�K�v�f��2�i0�c�Ȃ��@1�c�S�z�΁@2�c����z�΁@3�c�v���`���z�΁@4�c�����΁j�@7�E�E�E�f��2�̕K�v��
		 * 8�E�E�E�K�v���x���@�X�E�E�E�ϋv�� 10�E�E�E���z
		 * 
		 * //0�E�E�E�Ȃ��@1�E�E�E�S�z�΁@2�E�E�E����z�΁@3�E�E�E�v���`���z�΁@4�E�E�E�����΁@5�E�E�E�Ȃ�
		*/
		};
		String[] all_sozai = {"","�S�z��","����z��","�v���`���z��","������"};
		String sozai_1 = null;
		String sozai_2 = null;
		int num = 0;
		String make = "sozai";
		String weapon_type = "sword";
		
		System.out.println("�C���S�b�h������");
		System.out.println("�S�z�� "+pl_arsenal[1]+"�A����z�� "+pl_arsenal[2]+"�A�v���`���z�� "
				+pl_arsenal[3]+"�A������ "+pl_arsenal[4]+"�A�������Ă��܂��B");
		System.out.println("����ꗗ");
		for(int i = 0;i < weapon_name.length;i++) {
			System.out.print("["+(i+1)+"]\t"+weapon_name[i][0]+"\t�K�vGold:"+weapon_status[i][10]
					+ "\t�K�vLv:"+weapon_status[i][8]+"\n\t����:"+weapon_name[i][1] +"\n"
					+ "\t�K�v�f��:");
			if(weapon_status[i][4] == 0) {
				System.out.println("�Ȃ�\n");
			}else {
				switch(weapon_status[i][4]) {
				case 1:
					sozai_1 = "�S�z��";
					break;
				case 2:
					sozai_1 = "����z��";
					break;
				case 3:
					sozai_1 = "�v���`���z��";
					break;
				case 4:
					sozai_1 = "������";
					break;
				}
				switch(weapon_status[i][6]) {
				case 1:
					sozai_2 = "�S�z��";
					break;
				case 2:
					sozai_2 = "����z��";
					break;
				case 3:
					sozai_2 = "�v���`���z��";
					break;
				case 4:
					sozai_2 = "������";
					break;
				}
				System.out.println(sozai_1+"��"+weapon_status[i][5]+"�A"
						+ sozai_2+"��"+weapon_status[i][7]+"�A�K�v�ł�\n");
			}
		}
		while(true) {
			String print = "�쐬���镐��̔ԍ������ 0���͂ŃL�����Z��>>>";
			num = enter_number.method(print);
			if(num >= 0 && num <= 11) {
				break;
			}
		}
		if(num == 0) {
			System.out.println("����̍쐬���L�����Z�����܂���");
			make = "cancel";
		}else {
			num -= 1;
			if(pl_status[0] >= weapon_status[num][8]) {
				if(pl_status[8] >= weapon_status[num][10]) {
					if(pl_arsenal[weapon_status[num][4]] >= weapon_status[num][5]) {		//�f��1�̑f�ސ�������Ă��邩�̔���
						if(pl_arsenal[weapon_status[num][6]] >= weapon_status[num][7]) {	//�f��2�̑f�ސ�������Ă��邩�̔���
							
							pl_arsenal[weapon_status[num][4]]-=weapon_status[num][5];	//�g�����f�ސ����炷
							pl_arsenal[weapon_status[num][6]]-=weapon_status[num][7];	//�g�����f�ސ����炷
							
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
							
							/* 0�E�E�EHP�̃X�e�[�^�X�@1�E�E�ESTR�̃X�e�[�^�X�@2�E�E�EAGI�̃X�e�[�^�X �R�E�E�EVIT�̃X�e�[�^�X �X�E�E�E�ϋv��
							/*player_efficacy = /*[0�c����HP�A1�c����STR�A2�c����AGI�A3�c����AGI�A4�c����VIT
							5�c�A�C�e��STR�A6�c�A�C�e��AGI�A7�c�A�C�e��VIT][0�c����(�����l��0)�A1�c�c�����(�����l��0)]*/
							
							pl_status[3] += weapon_status[num][0];	//Hp�̃X�e�[�^�X�X�V
							pl_status[4] += weapon_status[num][0];	//Hp�̃X�e�[�^�X�X�V
							pl_status[5] += weapon_status[num][1];	//STR�̃X�e�[�^�X�X�V
							pl_status[6] += weapon_status[num][2];	//AGI�̃X�e�[�^�X�X�V
							pl_status[7] += weapon_status[num][3];	//VIT�̃X�e�[�^�X�X�V
							pl_status[8] -= weapon_status[num][10];	//Gold�̍X�V
							
							make = "true";		//����̍쐬���o�����ꍇ
						}
					}
				}else {
					System.out.println("�����������肸"+weapon_name[num][0]+"���쐬�o���܂���ł���");
					make = "Gold";
				}
			}else {
				System.out.println("�v���C���[L�������肸"+weapon_name[num][0]+"���쐬�o���܂���ł���");
				make = "Lv";
			}
		}
		
		if(make.equals("true")) {
			System.out.println(weapon_name[num][0]+"���쐬���܂���\n");
		}else if(make.equals("sozai")){
			System.out.println("�f�ނ����肸"+weapon_name[num][0]+"���쐬�o���܂���ł���");
		}
		System.out.print("\n������ꍇ�͂��L�[�A��߂�ꍇ��Enter�L�[������");
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_arsenal = pl_arsenal;
		return output;
	}
	
	/******************************************************************************
	 * �A�C�e���w���̃v���O����
	 ******************************************************************************/
	public town_event buy(int[] pl_status, String[] pl_belonging) throws IOException {
		town_event output = new town_event();
		
		main main = new main();
		
		
		//�A�C�e���ꗗ
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
		
		int count = 0;
		int num = 0;
		boolean belonging_max = true;
		
		System.out.println("��������Ⴂ�܂��I�ǂ̂悤�ȏ��i�����T���ł����H");
		System.out.println("�A�C�e���ꗗ");
		for(int i = 0;i < item_buy_name.length;i++) {
			count++;
			System.out.println("["+ count +"]\t" + item_buy_name[i][1] + "\t���z:"
					+ item_buy_price[i] +"\n\t����:\t" + item_buy_name[i][2] + "\n");
		}
		
		while(true) {
			String print = "�g�p����A�C�e����ԍ��œ���>>>\n0���͂ŃL�����Z��";
			num = enter_number.method(print);
			if(num>=0 && num<=19) {
				break;
			}
		}
		num -= 1;
		if(num == -1) {
			System.out.println("�w�����L�����Z�����܂����B");
		}else {
			if(pl_status[8] >= item_buy_price[num]) {
				for(int n = 0;n < pl_belonging.length;n++) {
					if(pl_belonging[n].equals("no")) {
						pl_belonging[n] = item_buy_name[num][0];
						pl_status[8] -= item_buy_price[num];
						System.out.println(item_buy_name[num][1] + "���w�����܂����B\n"
								+ "��������" + item_buy_price[num] + "����܂���");
						belonging_max = false;
						break;
					}
				}
				if(belonging_max == true) {
					System.out.println("�����A�C�e���������ς��ōw���ł��܂���");
				}
			}else {
				System.out.println("������������Ă��܂���");
			}
		}
		System.out.print("\n������ꍇ�͂��L�[�A��߂�ꍇ��Enter�L�[������");
		output.player_status = pl_status;
		output.player_belonging = pl_belonging;
		return output;
	}
	/******************************************************************************
	 * �X�L���C���̃v���O����
	 ******************************************************************************/
	public town_event get_skil(int[] pl_status, int[][] pl_skil) throws IOException {
		town_event gs_output = new town_event();
		skil ss = new skil();
		
		int num;
		
		while(true) {
			String print = "�悤�����A�X�L���̏C����ցI�Ȃɂ����܂����H\n"
					+ "[1]\t�V�����X�L���̏C��\n[2]\t�X�L���̋���";
			num = enter_number.method(print);
			if(num > 0 && num <= 2) {
				break;
			}
		}
		switch (num) {
		case 1:
			if(pl_status[0] < 5) {
				System.out.println("���݂̃��x���ł͗��p�ł��܂���I");
			}else if(pl_status[8] < 1500){
				System.out.println("������������܂���I�X�L���C���ɂ�1500Gold�K�v�ł�");
			}else {
				skil Acquisition = ss.Acquisition_skil(pl_status, pl_skil);
				pl_status = Acquisition.player_status;
				pl_skil = Acquisition.player_skil;
			}
			break;
		case 2:
			if(pl_status[0] < 5) {
				System.out.println("���݂̃��x���ł͗��p�ł��܂���I");
			}else if(pl_status[8] < 1000){
				System.out.println("������������܂���I�X�L���C���ɂ�1000Gold�K�v�ł�");
			}else {
				skil skil_up = ss.skil_up(pl_status, pl_skil);
				pl_status = skil_up.player_status;
				pl_skil = skil_up.player_skil;
			}
		}
		
		
		

		System.out.print("\n������ꍇ��s�L�[�A��߂�ꍇ��Enter�L�[������");
		gs_output.player_status = pl_status;
		gs_output.player_skil = pl_skil;
		return gs_output;
	}
	
	/******************************************************************************
	 * �R���V�A���̃v���O����
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
				"���^���X���C��","�u���b�N�f�[����","�}���e�B�R�A","�l�N���}���T�[","�t���[�Y�S�[����",
				"�n�[�f�X","���Ő_","�S�b�h�h���S��","�喂���T�^��","�����w��","�j��_�V���@",
		};
		//0...���x���A1...value�A2...0�A3...HP�A4...�ő�HP�A5...�U���́A6...���́A7...0�A0
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
		/*	[����][0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�����N�A9�cnull]  */
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
			System.out.println("����"+ren+"�A����!");
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
					teki_status[0] += teki_lv;	//���x��
					teki_status[3] += te_all_status[teki_num][3]/10*teki_lv;	//HP
					teki_status[4] += te_all_status[teki_num][4]/10*teki_lv;	//HP
					teki_status[5] += te_all_status[teki_num][5]/10*teki_lv;	//�U����
					teki_status[6] += te_all_status[teki_num][6]/10*teki_lv;	//�f����
					teki_status[7] += te_all_status[teki_num][7]/10*teki_lv;	//�h���
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
					System.out.print("��������"+(ren*30)+"�����܂���\n����"+ren+"�A����!!\t�ΐ�𑱂��܂����H�s��߂�ꍇ�� n ����́t\t>>>");
					String y = br.readLine();
					if(y.equals("n")) {
						System.out.println("�ΐ���I�����܂���");
						pl_status[9] = ren;
						game_over = "end";
					}
				}
				if(ren == 50) {
					pl_status[8] += 100000;
					pl_status[9] = 0;
					System.out.println("�R���V�A�����e!!\n���߂łƂ��������܂��B�܋�100000Golod!!\n"
							+ "���ʂȃX�L�����l���ł��܂��B");
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
			System.out.println(ren+"��ڂŔs�k���܂���");
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
 * �������Ŏ��s����v���O����
 **********************************************************************************************/
class use_K{
	int[] player_status;
	String[] player_belonging;
	int[] player_arsenal;	//0�E�E�E�Ȃ��@1�E�E�E�S�z�΁@2�E�E�E����z�΁@3�E�E�E�v���`���z�΁@4�E�E�E�����΁@5�E�E�E�Ȃ�
	
	/*********************************************************************************************
	 * ����
	 *********************************************************************************************/
	public use_K Cash(String[] pl_name, int[] pl_status, String[] pl_belonging) throws IOException {
		use_K Cash_output = new use_K();
		
		main main = new main();
		
		String[][] item_all_name = {
				{"00","�|�[�V����","HP��30��",},
				{"01","�n�C�|�[�V����","HP��50��",},
				{"02","�G�X�g�|�[�V����","HP��100��",},
				{"03","�O�����|�[�V����","HP��250��",},
				{"04","STR�|�[�V����","��莞�Ԃ̊�STR��50����",},
				{"05","AGI�|�[�V����","��莞�Ԃ̊�AGI��30����",},
				{"06","VIT�|�[�V����","��莞�Ԃ̊�VIT��30����",},
				{"07","��������","�ő�HP��30����",},
				{"08","�񕜌���","�ő�HP��60����",},
				{"09","�S������","HP��100����",},
				
				{"10","��������","��莞�Ԃ̊�STR��2�{",},
				{"11","�V������","��莞�Ԃ̊�AGI��2�{",},
				{"12","��猋��","��莞�Ԃ̊�VIT��2�{",},
				{"13","�S�z��","�S�z��1��",},
				{"14","�S�z��","�S�z��3��",},
				{"15","�S�z��","�S�z��5��",},
				{"16","����z��","����z��1��",},
				{"17","����z��","����z��3��",},
				{"18","����z��","����z��5��",},
				{"19","�v���`���z��","�v���`���z��1��",},
				
				{"20","�v���`���z��","�v���`���z��3��",},
				{"21","�v���`���z��","�v���`���z��5��",},
				{"22","������","������1��",},
				{"23","������","������3��",},
				{"24","������","������5��",},
				{"25","HP�|�[�V����","��莞�Ԃ̊�HP��80����",},
				{"26","��������","��莞�Ԃ̊�HP��2�{",},
				{"27","���������[��","��莞�Ԃ̊�STR��3�{",},
				{"28","�V�������[��","��莞�Ԃ̊�AGI��3�{",},
				{"29","��猋���[��","��莞�Ԃ̊�VIT��3�{",},
				
				{"30","���������[��","��莞�Ԃ̊�HP��3�{",},
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
		
		System.out.println("\n[1]\t�A�C�e���̊���");
		while(redo) {
			main.on_display_item(pl_belonging);
			while(true) {
				String print = "��������A�C�e����I�����Ă��������s0�ŃL�����Z���t\t>>>";
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
						System.out.println(item_all_name[a][1] + "�𔄂���" + item_sell_price[a]
								+ "Gold��ɓ���܂����B");
						break;
					}
				}
			}
			if(choice == -1) {
				redo = false;
				System.out.println("�������L�����Z�����܂���");
			}else {
				System.out.print("�����𑱂��܂����H�sy or n ���́t>>>");
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
	 * �C���S�b�h
	 *********************************************************************************************/
	public use_K Ingod(String[] pl_name, String[] pl_belonging, int[] pl_arsenal) throws IOException {
		use_K Ingod_output = new use_K();
		
		//�g�p���鎝�����̑I��
		int Privilege = 1;
		boolean hyouji = true;
		int[] arsenal_count = {0,0,0,0,0,0};
				
		if(pl_name[1].equals("�g���x���[")) {
			Privilege = 2;
		}
		System.out.println("\n[2]\t�C���S�b�h�̍쐬");
		for(int i = 0;i < pl_belonging.length;i++) {
			hyouji = true;
			switch(pl_belonging[i]) {
			/* �S�z�� */
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
			/* ����z�� */
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
			/* �v���`���z�� */
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
			/* ������ */
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
		if(pl_name[1] == "�g���x���[") {
			System.out.println("�w�g���x���[�̓����x�C���S�b�h��2�{�ɂȂ�܂�");
		}
		System.out.println("�S�z�΂�"+arsenal_count[1]+"�A����z�΂�"+arsenal_count[2]+"�A"
				+ "�v���`���z�΂�"+arsenal_count[3]+"�A�����΂�"+arsenal_count[4]+"�A������܂���");
		
		Ingod_output.player_arsenal = pl_arsenal;
		Ingod_output.player_belonging = pl_belonging;
		return Ingod_output;
	}
	
}

