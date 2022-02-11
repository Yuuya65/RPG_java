import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

class battle {
	int[] pl_status;
	int[] te_status;
	int[][] pl_skil;
	int total_damage;
	String result;
	String game_over;
	/**********************************************************************************************
	 * �o�g���̃v���O����
	 **********************************************************************************************/
	public battle method(int[] pl_status, int[][] pl_skil, String[] pl_name, int[] te_status, String te_name) throws IOException {
		damage damage_num = new damage();
		battle output = new battle();
		
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		
		String game_over = "no";		//�������ꍇ"yes"�ɂȂ�
		String result = "no";			//�o�g���������Ă�Ԃ�"no"�A����ȊO�͌��ʂ�\��"win" "lose"
		int pl_agi = pl_status[6];		//�v���C���[�̍s���Q�[�W��ݒ�
		int te_agi = te_status[6];		//����̍s���Q�[�W��ݒ�
		
		while(result.equals("no")) {
			on_display_battle(pl_status,pl_name[0]);
			on_display_battle(te_status,te_name);
			
			if(pl_agi > te_agi) {		//�v���C���[�̃^�[��
				/* �s���Q�[�W�̍X�V */
				pl_agi -= te_agi;
				te_agi = te_status[6];
				if(pl_agi < te_agi) {
					System.out.println("\n" + pl_name[0] + "�̃^�[��\t����" + te_name);
				}else {
					System.out.println("\n" + pl_name[0] + "�̃^�[��\t����" + pl_name[0]);
				}
				/* �v���C���[�̍U�� */
				damage p_damage = damage_num.player_attack(pl_status, pl_skil, pl_name, te_status, te_name);
				te_status = p_damage.te_status;
				pl_skil = p_damage.pl_skil;
				result = p_damage.result;
			}else if(pl_agi < te_agi) {	//�G�̃^�[��
				/* �s���Q�[�W�̍X�V */
				te_agi -= pl_agi;
				pl_agi = pl_status[6];
				if(pl_agi < te_agi) {
					System.out.println("\n" + te_name + "�̃^�[��\t����" + te_name);
				}else {
					System.out.println("\n" + te_name + "�̃^�[��\t����" + pl_name[0]);
				}
				/* �G�̍U�� */
				damage t_damage = damage_num.teki_attack(pl_status, pl_name, te_status, te_name);
				pl_status = t_damage.pl_status;
				result = t_damage.result;
			}else {						//�v���C���[�ƓG�̍s���Q�[�W���ꏏ�̂Ƃ��A�v���C���[�̃^�[���@���@�G�̃^�[��
				/* �s���Q�[�W�̐ݒ� */
				pl_agi = pl_status[6];
				te_agi = te_status[6];
				System.out.println("\n" + pl_name[0] + "�̃^�[��\t����" + te_name);
				/* �v���C���[�̍U�� */
				damage p_damage = damage_num.player_attack(pl_status, pl_skil, pl_name, te_status, te_name);
				te_status = p_damage.te_status;
				pl_skil = p_damage.pl_skil;
				result = p_damage.result;
				/* �G�������Ă�������s */
				if(result.equals("no")) {	//�G�̃^�[��
					if(pl_agi < te_agi) {
						System.out.println("\n" + te_name + "�̃^�[��\t����" + te_name);
					}else {
						System.out.println("\n" + te_name + "�̃^�[��\t����" + pl_name[0]);
					}
					/* �G�̍U�� */
					damage t_damage = damage_num.teki_attack(pl_status, pl_name, te_status, te_name);
					pl_status = t_damage.pl_status;
					result = t_damage.result;
				}
			}
			
			if(result.equals("win")) {
				System.out.println(te_name+"��|����");
				int value = te_status[8];
				pl_status = win(pl_status,pl_name,value);
				game_over = "no";
			}else if(result.equals("lose")) {
				System.out.println(te_name+"�ɔs�ꂽ");
				int value = te_status[1];
				pl_status = lose(pl_status, pl_name, te_name);
				game_over = "yes";
			}
		}
		
		/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A]*/
	
		
		output.pl_status = pl_status;
		output.pl_skil = pl_skil;
		output.game_over = game_over;
		return output;
	}
	
	/************************************************************************************************
	 * �X�e�[�^�X��\������v���O����
	 ************************************************************************************************/
	public static void on_display_battle(int[] status, String name) {
		System.out.println(name+"\tLv_"+status[0]+"\tHP_"+status[3]+"/"+status[4]
				+"\tSTR_"+status[5]+"\tAGI_"+status[6]+"\tVIT_"+status[7]);
	}
	/********************************************************************************************************
	 * ��������
	 ********************************************************************************************************/
	public int[] win(int[] pl_status, String[] pl_name, int value) {
		
		Random ran = new Random();
		
		int Ex_point = 0;
		//�o���l�����炤
		if(value != 0) {
			Ex_point =  1 + pl_status[0] / 5;		
			pl_status[1] += (Ex_point + value) * 10;
		}
		
		// Lv�A�b�v�̔���
		int lv_count = 0; //�オ�������x�������J�E���g����
		
		while(true) {
			if(pl_status[1] >= pl_status[2]) {
				pl_status[0]++;
				lv_count++;
				pl_status[1] = pl_status[1] - pl_status[2];
				pl_status[2] = pl_status[0] * 25;
			}else {
				break;
			}
		}
		
		// ���x���A�b�v���A�v���C���[�̃X�e�[�^�X�A�b�v������
		
		
		int[] status_point = new int[20];		//�P�`20�܂ł̐������i�[����
		for(int a = 0;a < status_point.length;a++) {
			status_point[a] = a + 1;
		}
		int[] status_record = new int [10];
		
		// �v���C���[�̐E�ƕʃ{�[�i�X
		int[] hosei = {1,1,1,1,1,1,1,1,1};
		/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A9�c�R���V�A���A���L�^]*/
		
		switch(pl_name[1]) {
			case "���m":			//STR��AGI����{
				hosei[5] = 2;
				hosei[6] = 2;
				break;
			case "�t�F���T�[":		//AGI���O�{
				hosei[6] = 3;
				break;
			case "�f�B�t�F���_�[":	//VIT���O�{
				hosei[7] = 3;
				break;
			case "�g���x���[":		//HP���O�{
				hosei[3] = 3;
				hosei[4] = 3;
				break;
			case "�M��":			//���������l�{
				hosei[8] = 2;
				break;
		}
		
		for(int a = 0;a < lv_count;a++) {
			status_record[4] += status_point[ran.nextInt(hosei[4] * 3)] + hosei[4] * 10;
			status_record[5] += status_point[ran.nextInt(hosei[5] * 3)] + hosei[5] * 2;
			status_record[6] += status_point[ran.nextInt(hosei[6] * 3)] + hosei[6] * 2;
			status_record[7] += status_point[ran.nextInt(hosei[7] * 3)] + hosei[7] * 2;
			
			/*
			for(int b = 4;b < status_record.length;b++) {
				System.out.print("b>" + b + " record>" + status_record[b] + " ");
			}
			System.out.println();
			*/
		}
		status_record[3] = status_record[4]; 
		status_record[8] = hosei[8] * value * 100 / (pl_status[0] / 20 + 1);
		
		/* ���x���A�b�v��̃X�e�[�^�X�𔽉f */
		for(int a = 0;a < pl_status.length;a++) {
			pl_status[a] += status_record[a];
		}
		/* HP�̕s��C�� */
		if(pl_status[3] > pl_status[4]) {
			pl_status[3] = pl_status[4];
		}
		
		/* �\�� */
		System.out.println("�o���l��"+ ((Ex_point + value) * 10 )+"��ɓ��ꂽ!");
		if(lv_count > 0) {
			System.out.println("���x����"+lv_count+"�オ�����B\t�ő�HP��"+status_record[4]+"�������B"
				+ "\tSTR��"+status_record[5]+"�オ�����B\nAGI��"+status_record[6]+"�オ�����B"
				+ "\tVIT��"+status_record[7]+"�オ�����B");
		}
		System.out.println("Gold��"+ status_record[8] + "��ɓ��ꂽ!");
		
		/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A]*/
		
		return pl_status;
	}
	/***************************************************************************************
	 * ��������
	 ***************************************************************************************/
	public int[] lose(int[] pl_status, String[] pl_name, String te_name) {
		int lost;
		if(pl_name[1].equals("�M��")) {
			pl_status[8] += 1000;
			System.out.println(pl_name[0] + "�́A" + te_name + "�ɔs�ꂽ�B\n"
					+ "�w�M���̓����x�@��������1000������B");
		}else {
			lost = pl_status[0] / 10 * 100;
			pl_status[8] -= lost;
			if(pl_status[8] <= 0) {
				pl_status[8] = 0;
				lost = 0 - pl_status[8];
			}
			if(pl_status[8] == 0) {
				System.out.println(pl_name[0] + "�́A" + te_name + "�ɔs�ꂽ�B\n"
						+ "���������Ȃ��Ȃ����B");
			}else {
				System.out.println(pl_name[0] + "�́A" + te_name + "�ɔs�ꂽ�B\n"
						+ "������" + lost + "���������B");
			}
			
		}
		pl_status[3] = pl_status[4];
		return pl_status;
	}
}

/******************************************************************************************
 * �_���[�W���v�Z����v���O����
 ******************************************************************************************/
class damage{
	int[] pl_status;
	int[] te_status;
	int[][] pl_skil;
	String result;
	
	/***********************************************************************************
	 * �v���C���[�̍U��
	 ***********************************************************************************/
	public damage player_attack(int[] pl_status, int[][] pl_skil, String[] pl_name, int[] te_status, String te_name) throws IOException {
		damage pl_attack = new damage();
		skil ss = new skil();
		
		String[] all_skil_name = {
				"�X�����g","�J�^���N�g","�o�[�`�J��",
				"�o�[�`�J���E�A�[�N","�\�j�b�N���[�u","�V���[�v�l�C��",
				"�g���C�A���M�����[","�j���[�g����","�N���[�V�t�B�N�V����",		
				"���e�I�u���C�N","�X�^�[�E�X�v���b�V��","�m���@�E�A�Z���V����",
				"�}�U�[�Y�E���U���I","�X�^�[�o�[�X�g�E�X�g���[��",
		};
		int total_damage = 0;
		String result = "no";
		
		/* �U�����@��\�� */
		System.out.println("[1]\t�ʏ�U��\n\t�З�_" + pl_status[5]);
		int count = 1;
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][3] != 0) {
				count++;
				System.out.print("[" + count + "]\t" + all_skil_name[pl_skil[n][0]] +"\n"
						+ "\t�З�_" + pl_skil[n][3] + "\t�N�[���^�C��_" + pl_skil[n][1] + "\t");
				if(pl_skil[n][1] == 0) {
					System.out.println("�g�p��");
				}else {
					System.out.println("�g�p�s��");
				}
				
			}
		}
		
		boolean redo = true;
		while(redo) {
			String print = "�U����i��I��ł�������>>>";
			int num = enter_number.method(print);
			num = enter_number.cut_out(num);
			
			if(num == 1) {
				System.out.println("�ʏ�U��");
				skil n_damage = ss.normal_damage(pl_skil);
				pl_skil = n_damage.player_skil;
				total_damage = pl_status[5];
				redo = false;
			}else if(num > count || num <= 0) {
				System.out.println("�ē���");
			}else {
				num -= 2;
				if(pl_skil[num][1] == 0) {
					System.out.print("�X�L���U��\n" + all_skil_name[pl_skil[num][0]] + "\t");
					skil s_damage = ss.skil_damage(pl_status, pl_skil, num);
					pl_skil = s_damage.player_skil;
					total_damage = s_damage.total_damage;
					redo = false;
				}else {
					System.out.println("���̃X�L���͎g�p�s�ł�");
				}
			}
		}
		total_damage -= te_status[7];
		if(total_damage <= 0) {
			total_damage = 0;
		}
		te_status[3] -= total_damage;
		System.out.println(te_name + "��" + total_damage + "�_���[�W�󂯂��I");
		
		/* �����HP��0�ȉ��ɂȂ����Ƃ� */
		if(te_status[3] <= 0) {
			te_status[3] = 0;
			result = "win";
		}
		
		/*	[0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
			5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A]*/
		
		pl_attack.pl_skil = pl_skil;
		pl_attack.te_status = te_status;
		pl_attack.result = result;
		return pl_attack;
	}
	/***********************************************************************************
	 * �G�̍U��
	 ***********************************************************************************/
	public damage teki_attack(int[] pl_status, String[] pl_name, int[] te_status, String te_name) throws IOException {
		damage te_attack = new damage();
		
		/* �G�̍U����i�͒ʏ�U���̂�
		 * 
		 *  �����I�ɂ͓G���X�L�����g�p�ł���悤�ɂ���*/
		int total_damage = 0;
		String result = "no";
		
		total_damage = te_status[5] - pl_status[7];
		if(total_damage <= 0) {
			total_damage = 0;
		}
		pl_status[3] -= total_damage;
		System.out.println(pl_name[0] + "��" + total_damage + "�_���[�W�󂯂��I");
		
		/* �v���C���[��HP��0�ȉ��ɂȂ����Ƃ� */
		if(pl_status[3] <= 0) {
			pl_status[3] = 0;
			result = "lose";
		}
		
		te_attack.pl_status = pl_status;
		te_attack.result = result;
		return te_attack;
	}
}


