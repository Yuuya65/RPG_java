import java.io.IOException;
import java.util.Random;

class skil {
	int[] player_status;
	int[][] player_skil;
	int total_damage;
	/* [�X�L���ԍ�][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���^�C���A3�c�З́A4�c�ő�R���{���A5�c�擾���x��]*/
	
	//[][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���\�^�C���A3�c�З́A4�c�ő�R���{���A5�c�n���x]
	
	/***********************************************************************************
	 * �U���^�[�����̃v���O����
	 ***********************************************************************************/
	public skil skil_damage (int[] pl_status, int[][] pl_skil ,int use_skil) {
		skil sd_output = new skil();
		
		Random rand = new Random();
		
		/* total_damage���v�Z���� */
		int total_damage;
		int combo;
		int ran = rand.nextInt(100) + 20;
		
		combo = (ran % pl_skil[use_skil][4]) + 1;
		total_damage = (pl_skil[use_skil][3] + pl_status[5] / 2) * combo;
		
		System.out.println(combo + "�A���@�q�b�g�I");
		
		/* �n���x���グ�� */
		pl_skil[use_skil][5]++;
		if(pl_skil[use_skil][5] % 5 == 0) {
			pl_skil[use_skil][3] += 3;
		}
		
		/* �N�[���^�C�������炷 */
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][0] != -1) {		//�X�L�����ɃX�L���������Ă���Ȃ�
				pl_skil[n][1] -= 5;
				if(pl_skil[n][1] <= 0) {
					pl_skil[n][1] = 0;
				}
			}
		}
		
		/* �g�p�����X�L���̃N�[���^�C�����ő�l�ɖ߂� */
		pl_skil[use_skil][1] = pl_skil[use_skil][2];
		
		sd_output.player_skil = pl_skil;
		sd_output.total_damage = total_damage;
		return sd_output;
	}
	/***********************************************************************************
	 * �ʏ�U�����̃v���O����
	 ***********************************************************************************/
	public skil normal_damage (int[][] pl_skil) {
		skil nd_output = new skil();
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][0] != -1) {		//�X�L�����ɃX�L���������Ă���Ȃ�
				pl_skil[n][1] -= 5;
				if(pl_skil[n][1] <= 0) {
					pl_skil[n][1] = 0;
				}
			}
		}
		nd_output.player_skil = pl_skil;
		return nd_output;
	}
	/*******************************************************************************
	 * �ړ����̃v���O����
	 *******************************************************************************/
	public skil move (int[][] pl_skil, int hosei) {
		skil m_output = new skil();
		
		for(int n = 0;n < 4;n++) {
			if(pl_skil[n][0] != -1) {		//�X�L�����ɃX�L���������Ă���Ȃ�
				pl_skil[n][1] -= 2 * hosei;
				if(pl_skil[n][1] <= 0) {
					pl_skil[n][1] = 0;
				}
			}
		}
		
		m_output.player_skil = pl_skil;
		return m_output;
	}
	/*******************************************************************************
	 * �X�L���C���̃v���O����
	 *******************************************************************************/
	public skil Acquisition_skil (int[] pl_status, int[][] pl_skil) throws IOException {
		skil as_output = new skil();
		
		/* �X�L���ꗗ */
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
				
				{12,0,20,700,11,0,0,999},
				{13,0,45,950,20,0,0,999},
		};
		/* [�X�L���ԍ�][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���^�C���A3�c�З́A
		 	4�c�ő�R���{���A5�c�擾���x���A6�c�����񐔁A7�c���������] */
		
		int lv = pl_status[0];
		int count = 0;
		int[][] count_id = new int[14][1];
		int num = 0;		//�C������X�L���ԍ�
		int num2 = 0;		//����ւ���̃X�L���ԍ�
		boolean Acquisition = false;
		boolean redo = true;
		boolean redo2 = true;
		boolean skil_max = true;
		
		System.out.println("�C���\�ȃX�L��");
		
		for(int n = 0;n < all_skil_status.length;n++) {
			if(all_skil_status[n][5] <= lv) {
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
							+ "\t�ő�A����_" + all_skil_status[n][4] + "\t���������_" + all_skil_status[n][7]);
					count_id[count][0] = all_skil_status[n][0];
				}
			}
		}
		if(count > 0) {
			while(redo) {
				String print = "�C���������X�L���̔ԍ�����͂��Ă��������B�s0���͂ŃL�����Z���t>>>";
				num = enter_number.method(print);
				if(num >= 0 && num <= count) {
					redo = false;
				}else {
					System.out.println("�ē���");
				}
			}
			if(num != 0) {
				for(int m = 0;m < 4;m++) {
					if(pl_skil[m][3] == 0) {
						pl_skil[m] = all_skil_status[count_id[num][0]];
						pl_skil[m][5] = 1;
						skil_max = false;
						Acquisition = true;
						break;
					}
				}
				if(skil_max == true) {
					System.out.println("�����X�L���g�������ς��ł�");
					count = 0;
					for(int m = 0;m < 4;m++) {
						count++;
						System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t�З�_"
								+ pl_skil[m][3] + "\t�ő�N�[���^�C��_" + pl_skil[m][2] 
								+ "\t�ő�A����_" + pl_skil[m][4] + "\t�n���x_" + pl_skil[m][5]);
					}
					while(redo2) {
						String print2 = "����ւ���X�L���ԍ�����͂��Ă��������B�s0���͂ŃL�����Z���t>>>";
						num2 = enter_number.method(print2);
						if(num2 >= 0 && num2 <= 4) {
							redo2 = false;
						}else {
							System.out.println("�ē���");
						}
					}
					if(num2 != 0) {
						pl_skil[num2-1] = all_skil_status[count_id[num][0]];
						pl_skil[num2-1][5] = 1;
						Acquisition = true;
					}else {
						System.out.println("�X�L���̏C�����L�����Z�����܂���");
					}
				}
			}else {
				System.out.println("�X�L���̏C�����L�����Z�����܂���");
			}	
		}else {
			System.out.println("�C���ł���X�L���͂���܂���");
		}
		if(Acquisition == true) {
			pl_status[8] -= 1500;
			System.out.println("�X�L���w"+ all_skil_name[count_id[num][0]] +"�x���C�����܂���\n"
					+ "1500Gold�g�p���܂���");
		}
		
		as_output.player_status = pl_status;
		as_output.player_skil = pl_skil;
		return as_output;
	}
	/*******************************************************************************
	 * ����X�L���C���̃v���O����
	 *******************************************************************************/
	public skil Limited_skil (int[][] pl_skil) throws IOException {
		skil ls_output = new skil();
		
		/* �X�L���ꗗ */
		String[] all_skil_name = {
				"�X�����g","�J�^���N�g","�o�[�`�J��",
				"�o�[�`�J���E�A�[�N","�\�j�b�N���[�u","�V���[�v�l�C��",
				"�g���C�A���M�����[","�j���[�g����","�N���[�V�t�B�N�V����",		
				"���e�I�u���C�N","�X�^�[�E�X�v���b�V��","�m���@�E�A�Z���V����",
				"�}�U�[�Y�E���U���I","�X�^�[�o�[�X�g�E�X�g���[��",
		};
		int[][] all_skil_status = {
				{0,0,10,60,1,1,0,4},
				{1,0,15,50,2,5,0,7},
				{2,0,20,100,1,15,0,12},
				
				{3,0,25,80,2,25,0,15},
				{4,0,25,180,1,35,0,9},
				{5,0,30,90,3,45,0,18},
				
				{6,0,40,150,3,55,0,24},
				{7,0,25,170,5,65,0,21},
				{8,0,55,200,6,75,0,30},
				
				{9,0,45,280,7,85,0,27},
				{10,0,35,360,8,95,0,30},
				{11,0,50,450,10,105,0,36},
				
				{12,0,20,700,11,0,0,999},
				{13,0,45,950,20,0,0,999},
		};
		String[] Limited_skil_name = {
				"�}�U�[�Y�E���U���I","�X�^�[�o�[�X�g�E�X�g���[��",
		};
		int[][] Limited_skil_status = {
				{12,0,20,700,11,0,0,999},
				{13,0,45,950,20,0,0,999},
		};
		/* [�X�L���ԍ�][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���^�C���A3�c�З́A
		 	4�c�ő�R���{���A5�c�擾���x���A6�c�����񐔁A7�c���������] */
		
		int count = 0;
		int[][] count_id = new int[14][1];
		int num = 0;		//�C������X�L���ԍ�
		int num2 = 0;		//����ւ���̃X�L���ԍ�
		boolean Acquisition = false;
		boolean redo = true;
		boolean redo2 = true;
		boolean skil_max = true;
		boolean[] Possible = {false,false};
		
		System.out.println("�C���\�ȃX�L���ꗗ");
		for(int n = 0;n < Limited_skil_status.length;n++) {
			String Unacquired = "�C���\";
			for(int m = 0;m < pl_skil.length;m++) {
				if(Limited_skil_status[n][0] == pl_skil[m][0]){
					Unacquired = "���ɏC�����Ă��܂�";
					Possible[n] = true;
				}
			}
			System.out.println("[" + (n+1) + "]\t" + Limited_skil_name[n] +"\n\t�З�_"
					+ Limited_skil_status[n][3] + "\t�ő�N�[���^�C��_" + Limited_skil_status[n][2] 
					+ "\t�ő�A����_" + Limited_skil_status[n][4] + "\t���������_" 
					+ Limited_skil_status[n][7] + "\t" + Unacquired);
		}
		if(Possible[0] == false || Possible[1] == false) {
			while(redo) {
				String print = "�C���������X�L���̔ԍ�����͂��Ă��������B�s0���͂ŃL�����Z���t>>>";
				num = enter_number.method(print);
				if(num >= 0 && num <= 2) {
					if(Possible[num-1] == false) {
						redo = false;
					}else {
						System.out.println("���łɏC�����Ă��܂�");
					}
				}else {
					System.out.println("�ē���");
				}
			}
			if(num != 0) {
				num -= 1;
				for(int m = 0;m < 4;m++) {
					if(pl_skil[m][3] == 0) {
						pl_skil[m] = Limited_skil_status[num];
						pl_skil[m][5] = 1;
						skil_max = false;
						Acquisition = true;
						break;
					}
				}
				if(skil_max == true) {
					System.out.println("�����X�L���g�������ς��ł�");
					count = 0;
					for(int m = 0;m < 4;m++) {
						count++;
						System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t�З�_"
								+ pl_skil[m][3] + "\t�ő�N�[���^�C��_" + pl_skil[m][2] 
								+ "\t�ő�A����_" + pl_skil[m][4] + "\t�n���x_" + pl_skil[m][5]);
					}
					while(redo2) {
						String print2 = "����ւ���X�L���ԍ�����͂��Ă��������B�s0���͂ŃL�����Z���t>>>";
						num2 = enter_number.method(print2);
						if(num2 >= 0 && num2 <= 4) {
							redo2 = false;
						}else {
							System.out.println("�ē���");
						}
					}
					if(num2 != 0) {
						pl_skil[num2-1] = Limited_skil_status[num];
						pl_skil[num2-1][5] = 1;
						Acquisition = true;
					}else {
						System.out.println("�X�L���̏C�����L�����Z�����܂���");
					}
				}
			}else {
				System.out.println("�X�L���̏C�����L�����Z�����܂���");
			}
			if(Acquisition == true) {
				System.out.println("�X�L���w"+ Limited_skil_name[num] +"�x���C�����܂���");
			}
		}else {
			System.out.println("�C���\�ȃX�L��������܂���B�������Ă���X�L���̏n���x���グ�܂�");
			count = 0;
			for(int m = 0;m < 4;m++) {
				if(pl_skil[m][4] != 0) {
					count++;
					System.out.println("[" + count + "]\t" + all_skil_name[pl_skil[m][0]] +"\n\t�З�_"
							+ pl_skil[m][3] + "\t�ő�N�[���^�C��_" + pl_skil[m][2] 
							+ "\t�ő�A����_" + pl_skil[m][4] + "\t�n���x_" + pl_skil[m][5]);
				}	
			}
			while(redo) {
				String print = "�X�L���̔ԍ�����͂��Ă��������B>>>";
				num = enter_number.method(print);
				if(num > 0 && num <= count) {
					redo = false;
				}else {
					System.out.println("�ē���");
				}
			}
			pl_skil[num-1][5] += 50;
			System.out.println(all_skil_name[pl_skil[num-1][0]] + "�̏n���x��50�グ�܂���");
		}
		
		
		ls_output.player_skil = pl_skil;
		return ls_output;
	}
	
	/*******************************************************************************
	 * �X�L�������̃v���O����
	 *******************************************************************************/
	public skil skil_up (int[] pl_status, int[][] pl_skil) throws IOException {
		skil ssu_output = new skil();
		
		String[] all_skil_name = {
				"�X�����g","�J�^���N�g","�o�[�`�J��",
				"�o�[�`�J���E�A�[�N","�\�j�b�N���[�u","�V���[�v�l�C��",
				"�g���C�A���M�����[","�j���[�g����","�N���[�V�t�B�N�V����",		
				"���e�I�u���C�N","�X�^�[�E�X�v���b�V��","�m���@�E�A�Z���V����",
				"�}�U�[�Y�E���U���I","�X�^�[�o�[�X�g�E�X�g���[��",
		};
		boolean[] count_place = new boolean[4];
		int count = 0;
		int num_1 = 0;
		int num_2 = 0;
		boolean success = false;
		for(int n = 0;n < count_place.length;n++) {
			count_place[n] = false;
		}
		
		System.out.println("�n���x��20�𒴂��邲�ƂɃX�L���̋������s�Ȃ��܂�\n�C�����Ă���X�L����\�����܂�");
		
		for(int m = 0;m < 4;m++) {
			if(pl_skil[m][5] / 20 > pl_skil[m][6] && pl_skil[m][6] != pl_skil[m][7] && pl_skil[m][3] != 0) {
				System.out.println("[" + (m + 1) + "]\t" + all_skil_name[pl_skil[m][0]] +"\n"
						+ "\t�З�_" + pl_skil[m][3] + "\t�ő�N�[���^�C��_" + pl_skil[m][2] 
						+ "\t�ő�A����_" + pl_skil[m][4] + "\t�n���x_" + pl_skil[m][5] + "\n"
						+ "\t������_" + pl_skil[m][6] + "\t���������_" + pl_skil[m][7] + "\t�����\");
				count ++;
				count_place[m] = true;
			}else if(pl_skil[m][3] != 0){
				System.out.println("[" + (m + 1) + "]\t" + all_skil_name[pl_skil[m][0]] +"\n"
						+ "\t�З�_" + pl_skil[m][3] + "\t�ő�N�[���^�C��_" + pl_skil[m][2] 
						+ "\t�ő�A����_" + pl_skil[m][4] + "\t�n���x_" + pl_skil[m][5] + "\n"
						+ "\t������_" + pl_skil[m][6] + "\t���������_" + pl_skil[m][7] + "\t�����ł��܂���");
			}
		}
		if(count == 0) {
			System.out.println("�����ł���X�L���͂���܂���");
		}else {
			while(true) {
				String print_1 = "��������X�L����I��ł��������B�s0���͂ŃL�����Z���t>>>";
				num_1 = enter_number.method(print_1);
				if(num_1 > 0 && num_1 <= 4 && count_place[num_1 - 1] == true || num_1 == 0) {
					break;
				}
			}
			if(num_1 == 0) {
				System.out.println("�X�L���������L�����Z�����܂���");
			}else {
				num_1 -= 1;
				while(true) {
					String print_2 = "�ǂ���������܂����H�s0���͂ŃL�����Z���t>>>\n"
							+ "[1]\t�N�[���^�C������\n[2]\t�З�UP\n[3]\t�A����UP";
					num_2 = enter_number.method(print_2);
					if(num_2 >= 0 && num_2 <= 3) {
						break;
					}
				}
				switch(num_2) {
				case 1:
					if(pl_skil[num_1][2] <= 0) {
						System.out.println("����ȏ�N�[���^�C���������ł��܂���");
					}else {
						pl_skil[num_1][2] -= 5;
						if(pl_skil[num_1][2] <= 0) {
							pl_skil[num_1][2] = 0;
						}
						success = true;
					}
					break;
				case 2:
					pl_skil[num_1][3] += 30;
					success = true;
					break;
				case 3:
					pl_skil[num_1][4] += 1;
					success = true;
					break;
				}
				if(success == true) {
					pl_status[8] -= 1000;
					pl_skil[num_1][6] += 1;
					System.out.println("�X�L���̋����ɐ������܂����B1000Gold�x�����܂���");
				}
			}
		}
		ssu_output.player_status = pl_status;
		ssu_output.player_skil = pl_skil;
		return ssu_output;
	}
}

