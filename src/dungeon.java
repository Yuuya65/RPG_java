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
	 * �_���W�����\���̃v���O����
	 ******************************************************************************/
	public dungeon RPG_dungeon(int[] pl_point, int[] pl_status, int[][] pl_efficacy,
			int[][] pl_skil, String[] pl_name, String[] pl_belonging, int[] pl_arsenal, String event) throws IOException {
		dungeon output = new dungeon();
		
		main Game_1_main = new main();
		map pm = new map();
		location pl = new location();
		all_event pae = new all_event();
		
		//�t���A���������_���Ō��߂�
		Random rand = new Random();
		int sum_floor = 2+(rand.nextInt(10)+1)%4;
		int now_floor = 1;
		if(pl_status[0] == 1) {
			sum_floor = 4;
		}
		String game_over = "no";	//"no"�v���C���A "yes"�T�����s�A "clear"�T�������A
		
		while(game_over == "no") {
			event = "no";
			map map = pm.method();
			String[][][] main_map = map.map;
			while(!(event.equals("G")) && game_over == "no") {
				event ="no";
				System.out.print("\n" + now_floor + "/" + sum_floor + "�K�w");
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
			//�_���W�������̏������W
			pl_point[0] = 2;
			pl_point[1] = 1;
			pl_point[2] = 1;
			if(now_floor == sum_floor) {	//���̊K�w���ŏI�w�̏ꍇ
				pl_point[0] = 3;
				pl_point[1] = 7;
				pl_point[2] = 4;
			}
		}
		if(game_over == "win") {
			System.out.println("\n�T������");
		}else {
			System.out.println("\n�T�����s");
		}
		//�_���W�����T���I��
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
	 * �A�C�e���̃v���O����
	 ******************************************************************************/
	public dungeon item(int[] pl_status, String[] pl_belonging) {
		dungeon output = new dungeon();
		String[][] item_all_name = {						//���O�A�\��
				{"00","�|�[�V����","HP��30��",},
				{"07","��������","�ő�HP��30����",},
				{"13","�S�z��","�S�z��1��",},
				{"16","����z��","����z��1��",},
				{"19","�v���`���z��","�v���`���z��1��",},
				
				{"01","�n�C�|�[�V����","HP��50��",},
				{"08","�񕜌���","�ő�HP��60����",},
				{"14","�S�z��","�S�z��3��",},
				{"17","����z��","����z��3��",},
				{"20","�v���`���z��","�v���`���z��3��",},
				{"22","������","������1��",},

				{"02","�G�X�g�|�[�V����","HP��100��",},
				{"04","STR�|�[�V����","��莞�Ԃ̊�STR��50����",},
				{"05","AGI�|�[�V����","��莞�Ԃ̊�AGI��30����",},
				{"06","VIT�|�[�V����","��莞�Ԃ̊�VIT��30����",},
				{"25","HP�|�[�V����","��莞�Ԃ̊�HP��80����",},
				{"15","�S�z��","�S�z��5��",},
				{"18","����z��","����z��5��",},
				{"21","�v���`���z��","�v���`���z��5��",},
				{"23","������","������3��",},
				
				{"09","�S������","HP��100����",},
				{"10","��������","��莞�Ԃ̊�STR��2�{",},
				{"11","�V������","��莞�Ԃ̊�AGI��2�{",},
				{"12","��猋��","��莞�Ԃ̊�VIT��2�{",},
				{"26","��������","��莞�Ԃ̊�HP��2�{",},
				{"24","������","������5��",},
		};
		/* [���][0�c�A�C�e���ԍ��A1�c�A�C�e�����A2�c����] */
		
		int [] random_point= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
		Random ran = new Random();
		int item_num = 0;
		boolean belonging_max = true;
		
		/* �v���C���[�̃��x���̉����ďo������A�C�e�������߂� */
		if(pl_status[0] <=  3) {
			item_num = random_point[ran.nextInt(5)] + 20;
		}else if(pl_status[0] < 15){
			item_num = random_point[ran.nextInt(11)];
		}else if(pl_status[0] < 30){
			item_num = random_point[ran.nextInt(20)];
		}else {
			item_num = random_point[ran.nextInt(26)];
		}
		
		// �����A�C�e���̕��ёւ�
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
		
		/* �A�C�e�����l������ */
		for(int n = 0;n < pl_belonging.length;n++) {
			if(pl_belonging[n].equals("no")) {
				pl_belonging[n] = item_all_name[item_num][0];
				System.out.println(item_all_name[item_num][1] + "���E���܂����B\n"
						+ "�g�p���@>>>" + item_all_name[item_num][2]);
				belonging_max = false;
				break;
			}
		}
		if(belonging_max == true) {
			System.out.println("�����A�C�e���������ς��ŃA�C�e�����E���܂���ł����B\n");
		}
		
		output.player_belonging = pl_belonging;
		output.player_status = pl_status;
		return output;
	}
	/******************************************************************************
	 * �G���G�̃v���O����
	 * Bose�ȊO�̓G
	 ******************************************************************************/
	public dungeon teki(int[] pl_status, int[][] pl_skil, String[] pl_name) throws IOException {
		dungeon output = new dungeon();
		battle input = new battle();
		
		String game_over = "no";
		
		/* �G�̖��O */
		String[]te_all_name = {
				"�X�P���g��","�X���C��","�f�[����","�I�[�K","�S�u����","�}���e�B�R�A",
				"�}�X�^�[�f�[����","�l�N���}���T�[","�A�C�A���S�[����","�h���S���]���r"
		};
		/* �G�̃X�e�[�^�X */
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
		/*	[����][0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
			5�c�U���́A6�c�f�����A7�c�h��́A8�c�����N�A9�cnull]  */
		
		int[] teki_status = new int[10];
		int [] random_point= {0,1,2,3,4,5,6,7,8};
		Random ran = new Random();
		int teki_num = 0;
		
		/*�@���x���ɉ����ēG�̏o����ς���		 */
		if(pl_status[0] < 5) {						//���x����3�����̂Ƃ��͋����G��\�����Ȃ�
			teki_num = random_point[ran.nextInt(2)];
		}else if(pl_status[0] < 25){				//�S�u�����܂ł�\��
			teki_num = random_point[ran.nextInt(4)];
		}else if(pl_status[0] < 50){				//�����x����
			teki_num = random_point[ran.nextInt(6)];
		}else {										//�S��
			teki_num = random_point[ran.nextInt(9)];
		}
		
		int teki_level = 1;
		
		//teki_level += (pl_status[0] / 5) * (5 - te_all_status[teki_num][8]); 
		teki_level += pl_status[0] * 3 / te_all_status[teki_num][8];
		
		/*
		System.out.println("����������������������������������������������������������������������������������������"
				+ "\nteki_Lv >>> pl_status:" + pl_status[0] + " �� teki_Rank:"
				+ te_all_status[teki_num][8] + " �~ 3 = " + teki_level + "\n"
				+ "����������������������������������������������������������������������������������������");
		*/
		
		/* �C���K�v */
		//�o�g�����̓G�̃X�e�[�^�X
		teki_status[0] += teki_level;	//���x��
		teki_status[3] += te_all_status[teki_num][3]/10*teki_level;	//HP
		teki_status[4] += te_all_status[teki_num][4]/10*teki_level;	//HP
		teki_status[5] += te_all_status[teki_num][5]/10*teki_level;	//�U����
		teki_status[6] += te_all_status[teki_num][6]/10*teki_level;	//�f����
		teki_status[7] += te_all_status[teki_num][7]/10*teki_level;	//�h���
		teki_status[8] += teki_level;	//�����N
		/*
		teki_status[0] = te_all_status[teki_num][0];
		teki_status[3] = te_all_status[teki_num][3];
		teki_status[4] = te_all_status[teki_num][4];
		teki_status[5] = te_all_status[teki_num][5];
		teki_status[6] = te_all_status[teki_num][6];
		teki_status[7] = te_all_status[teki_num][7];
		teki_status[8] = te_all_status[teki_num][8];
		if(pl_status[0] >= 3) {
			teki_status[0] += teki_level;	//���x��
			teki_status[3] += te_all_status[teki_num][3]/10*teki_level;	//HP
			teki_status[4] += te_all_status[teki_num][4]/10*teki_level;	//HP
			teki_status[5] += te_all_status[teki_num][5]/10*teki_level;	//�U����
			teki_status[6] += te_all_status[teki_num][6]/10*teki_level;	//�f����
			teki_status[7] += te_all_status[teki_num][7]/10*teki_level;	//�h���
			teki_status[8] += teki_level;	//�����N
		}
		//System.out.println("te_sta= " + teki_status[0]);
		
		/* �o�g���v���O���� */
		battle Game_1_battle = input.method(pl_status, pl_skil, pl_name, teki_status, te_all_name[teki_num]);
		pl_status = Game_1_battle.pl_status;
		pl_skil = Game_1_battle.pl_skil;
		game_over = Game_1_battle.game_over;
		
		if(game_over.equals("lose")) {
			System.out.println("�T�����s");
		}
		
		output.player_status = pl_status;
		output.player_skil = pl_skil;
		output.game_over = game_over;
		return output;
	}
	/******************************************************************************
	 * �{�X�̃v���O����
	 ******************************************************************************/
	public dungeon bose(int[] pl_status, int[][] pl_skil, String[] pl_name) throws IOException {
		dungeon output = new dungeon();
		
		battle input = new battle();
		
		String game_over = "no";
		
		/* ���O */
		String[] te_all_name = {"����","����","�喂��","���Ő_",};
		/* �X�e�[�^�X */
		int[][] te_all_status = {
				{1,0,0,150,150,80,70,25,7,},
				{1,0,0,160,160,75,40,35,7,},
				{1,0,0,170,170,50,120,30,8,},
				{1,0,0,350,350,200,60,60,8,},
		};
		/*	[����][0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�����N�A]  
		
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
		
		//�o�g�����̓G�̃X�e�[�^�X
		teki_status[0] = te_all_status[teki_num][0];
		teki_status[3] = te_all_status[teki_num][3];
		teki_status[4] = te_all_status[teki_num][4];
		teki_status[5] = te_all_status[teki_num][5];
		teki_status[6] = te_all_status[teki_num][6];
		teki_status[7] = te_all_status[teki_num][7];
		teki_status[8] = te_all_status[teki_num][8]*5;
		if(pl_status[0] > 16) {
			teki_status[0] += teki_level;	//���x��
			teki_status[3] += te_all_status[teki_num][3]/10*teki_level;	//HP
			teki_status[4] += te_all_status[teki_num][4]/10*teki_level;	//HP
			teki_status[5] += te_all_status[teki_num][5]/10*teki_level;	//�U����
			teki_status[6] += te_all_status[teki_num][6]/10*teki_level;	//�f����
			teki_status[7] += te_all_status[teki_num][7]/10*teki_level;	//�h���
			teki_status[8] += teki_level;	//�����N
		}
		
		/* �o�g���v���O���� */
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


