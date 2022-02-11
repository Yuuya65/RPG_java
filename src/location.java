import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class location {
	int [] player_point;
	String[] player_belonging;
	int[] player_status;
	int[][] player_efficacy;
	int[][] player_skil;
	int[] player_arsenal;
	String event;
	boolean logout;
	location method(String[][][] map, int[] player_point, String[] player_belonging, int[] player_status,
			int[][] player_efficacy, int[][] player_skil, String[] name, int[] player_arsenal ,String event) throws IOException {
		
		location output = new location();
		
		admin sa = new admin();
		main sm = new main();
		skil ss = new skil();
		item si = new item();
		
		//String event = "no";
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		boolean logout = false;
		String error = null;	//"no"�������l����͂ł��Ă���A"redo"��蒼���A"repeat"�J��Ԃ�
		String enter = "enter";	//��������
		String dict = "dict";		//switch���̔���Ŏg��
		int skip = 0;			//�B���R�}���h�@2�����ȏ���͂ŃC�x���g�ɓ�����܂Ői�ݑ�����
		
		//System.out.println("Judgment of event >>> " + event);
		while(error != "no"){
			if(event == "end") {
				event = "no";
				
				main.on_display_map(map[player_point[0]], player_point);
				main.on_display_status(player_status, name[0]);
				
			}else{
				
				if(event == "continue") {
					enter = br.readLine();
					if(enter.length()==0) {
						dict = "no";
					}else {
						skip = enter.length();
						dict = enter.substring(0, 1);
					}
				}else {
					if(error != "repeat") {
						System.out.print("��>>>a, �E>>>d, ��>>>w, ��>>>s,�@�A�C�e���g�p>>>i, �����");
						enter = br.readLine();
						if(enter.length()==0) {
							dict = "no";
						}else {
							skip = enter.length();
							dict = enter.substring(0, 1);
						}
					}
				}
				
				switch(dict) {
				/* �� */
				case "s":
					player_point[1]++;
					break;
				/* �� */
				case "w":
					player_point[1]--;
					break;
				/* �� */
				case "a":
					player_point[2]--;
					break;
				/* �E */
				case "d":
					player_point[2]++;
					break;
				/* �e���|�[�g */
				case "t":
					player_point = Teleport(map,player_point);
					break;
				/* �A�C�e�� */
				case "i":
					//�A�C�e�����g��
					item item = si.method(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal);
					player_status = item.player_status;
					player_efficacy = item.player_efficacy;
					player_belonging = item.player_belonging;
					skip = 1;
					break;
				/* ���O�A�E�g */
				case "l":
					error = "no";
					skip = 1;
					break;
				/* admin */
				case "@":
					admin admin = sa.Authority(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal);
					name = admin.player_name;
					player_status = admin.player_status;
					player_efficacy = admin.player_efficacy;
					player_skil = admin.player_skil;
					player_belonging = admin.player_belonging;
					skip = 1;
					break;
				/* �w���v */
				case "?":
					output.help();
					skip = 1;
					break;
				/* ���̑� */
				default:
					skip = 1;
				}
				/* ���O�A�E�g�ȊO */
				if(!(enter.equals("logout"))) {
					if(map[player_point[0]][player_point[1]][player_point[2]] =="��") {
						if(skip>1) {
							error = "no";
						}else {
							System.out.println("�ǂɓ���������");
							error = "redo";
						}
						switch(dict) {
							case "s":
								player_point[1]--;
								break;
							case "w":
								player_point[1]++;
								break;
							case "a":
								player_point[2]++;
								break;
							case "d":
								player_point[2]--;
								break;
						}
						
					}else {
						//�X�e�[�^�X�̎�����
						if(!(dict.equals("i"))) {
							//HP�̒���
							if(player_status[3] > player_status[4]) {
								player_status[3] = player_status[4];
							}
							
							//�X�L���̃N�[���^�C������
							int hosei = 1;
							switch(name[1]) {
							case "���m":
								hosei = 3;
								break;
							case "�t�F���T�[":
								hosei = 2;
								break;
							}
							skil move = ss.move(player_skil, hosei);
							player_skil = move.player_skil;
							
							// ���ʂ̎c��^�[���̌���
							/* ���� */
							if(player_efficacy[0][1] != 0) {
								player_efficacy[0][1] --;
								if(player_efficacy[0][1] <= 0) {
									player_status[3] -= player_efficacy[0][0];
									player_status[4] -= player_efficacy[0][0];
									player_efficacy[0][0] = 0;
									player_efficacy[0][1] = 0;
								}
							}
							if(player_efficacy[1][1] != 0) {
								player_efficacy[1][1] --;
								if(player_efficacy[1][1] <= 0) {
									player_status[5] -= player_efficacy[1][0];
									player_efficacy[1][0] = 0;
									player_efficacy[1][1] = 0;
								}
							}
							if(player_efficacy[2][1] != 0) {
								player_efficacy[2][1] --;
								if(player_efficacy[2][1] <= 0) {
									player_status[6] -= player_efficacy[2][0];
									player_efficacy[2][0] = 0;
									player_efficacy[2][1] = 0;
								}
							}
							if(player_efficacy[3][1] != 0) {
								player_efficacy[3][1] --;
								if(player_efficacy[3][1] <= 0) {
									player_status[6] -= player_efficacy[3][0];
									player_efficacy[3][0] = 0;
									player_efficacy[3][1] = 0;
								}
							}
							if(player_efficacy[4][1] != 0) {
								player_efficacy[4][1] --;
								if(player_efficacy[4][1] <= 0) {
									player_status[7] -= player_efficacy[4][0];
									player_efficacy[4][0] = 0;
									player_efficacy[4][1] = 0;
								}
							}
							/* �A�C�e�� */
							if(player_efficacy[5][1] != 0) {
								player_efficacy[5][1] --;
								if(player_efficacy[5][1] <= 0) {
									player_status[5] -= player_efficacy[5][0];
									player_efficacy[5][0] = 0;
									player_efficacy[5][1] = 0;
								}
							}
							if(player_efficacy[6][1] != 0) {
								player_efficacy[6][1] --;
								if(player_efficacy[6][1] <= 0) {
									player_status[6] -= player_efficacy[6][0];
									player_efficacy[6][0] = 0;
									player_efficacy[6][1] = 0;
								}
							}
							if(player_efficacy[7][1] != 0) {
								player_efficacy[7][1] --;
								if(player_efficacy[7][1] <= 0) {
									player_status[7] -= player_efficacy[7][0];
									player_efficacy[7][0] = 0;
									player_efficacy[7][1] = 0;
								}
							}
							if(player_efficacy[8][1] != 0) {
								player_efficacy[8][1] --;
								if(player_efficacy[8][1] <= 0) {
									player_status[4] -= player_efficacy[8][0];
									player_efficacy[8][0] = 0;
									player_efficacy[8][1] = 0;
								}
							}
							
							/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
							5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A9�c�R���V�A���A���L�^]*/
						}
						//�C�x���g�m�F
						String event_name = map[player_point[0]][player_point[1]][player_point[2]];
						switch(event_name) {
							case "M":			//�}�[�P�b�g
								event = "M";
								break;
							case "D":			//�_���W����
								event = "D";
								break;
							case "K":			//������
								event = "K";
								break;
							case "V":			//�R���V�A��
								event = "V";
								break;
							case "W":			//�]�E
								event = "W";
								break;
							case "A":			//�A�r���e�B����
								event = "A";
								break;
							case "S":			//�X�L������
								event = "S";
								break;
							case "H":			//home
								event = "H";
								break;
							case "I":			//�A�C�e��
								event = "I";
								break;
							case "T":			//�G
								event = "T";
								break;
							case "G":			//�S�[��
								event = "G";
								break;
							case "B":			//�{�X
								event = "B";
								break;
							case "R":			//���[���b�g
								event = "R";
								break;
							case " ":			//��
								event = "no";
								break;
							case "!":			//��
								event = "!";
								break;
						}
						error = "no";
						if(skip>1) {
							if(event == "no") {
								error = "repeat";
							}
						}
					}
				}
			}
						
		}
		if(enter.equals("logout")) {
			System.out.print("Logout���܂����H�sy or n���́t ");
			enter = br.readLine();
			if(enter.equals("y")) {			//logout����͎��̏���
				logout = true;
			}
		}
		output.player_point = player_point;
		output.player_status = player_status;
		output.player_efficacy = player_efficacy;
		output.player_skil = player_skil;
		output.player_belonging = player_belonging;
		output.player_arsenal = player_arsenal;
		output.event = event;
		output.logout = logout;
		return output;
	}
	/*****************************************************************************************
	 * �e���|�[�g���̃v���O����
	 * 
	 * �wZ���W�ɂ��āx
	 * 0�E�E�E�X�^�[�g�t���A�A1�E�E�E�}�[�P�b�g���A2�E�E�E�_���W�����iBose�����ȊO�j�A3�E�E�EBose����
	 *****************************************************************************************/
	public static int[] Teleport(String[][][] map, int[] player_point) throws IOException {
		
		String print_x = "X���W�����";
		String print_y = "Y���W�����";
		String print_z = "Z���W�����\n0�E�E�E�X�^�[�g�t���A�A1�E�E�E�}�[�P�b�g���A2�E�E�E�_���W�����iBose�����ȊO�j�A3�E�E�EBose����";
		
		String error = null;
		do {
			player_point[0]=enter_number.method(print_z);
			if(player_point[0] > map.length) {
				System.out.println("�����Ȑ��������͂���Ă��܂��B");
				error = "redo";
			}else {
				error = null;
			}
		}while(error == "redo");
		do {
			player_point[1]=enter_number.method(print_y);
			if(player_point[1] > map[player_point[0]].length) {
				System.out.println("�����Ȑ��������͂���Ă��܂��B");
				error = "redo";
			}else {
				error = null;
			}
		}while(error == "redo");
		do {
			player_point[2]=enter_number.method(print_x);
			if(player_point[2] > map[player_point[0]][player_point[1]].length) {
				System.out.println("�����Ȑ��������͂���Ă��܂��B");
				error = "redo";
			}else {
				error = null;
			}
		}while(error == "redo");
		
		return player_point;
	}
	/*****************************************************************************************
	 * �w���v�̎Q�ƃv���O����
	 *****************************************************************************************/
	public static void help() throws IOException {
		
		BufferedReader br = 
	            new BufferedReader(new InputStreamReader(System.in));
		
		String redo = "y";
		int num = 0;
		
		System.out.println("���������������������������������������@�w���v�@��������������������������������������\n");
		
		while(redo.equals("y")) {
			String print = "�}�[�P�b�g�ɂ���\n"
					+ "[1]\t����쐬\n[2]\t�A�C�e���V���b�v\n[3]\t�X�L���C����\n[4]\t�z�[��\n"
					+ "�_���W�����ɂ���\n[5]\t�A�C�e��\n[6]\t�G\n[7]\t�{�X\n[8]\t�o�g��\n"
					+ "�m�肽�����ڔԍ�����͂��Ă��������B�s0�ŃL�����Z���t >>>";
			num = enter_number.method(print);
			if(num != 0) {
				switch (num) {
				case 1:
					System.out.println("����쐬�ɂ���\n"
							+ "������쐬����ɂ́A���킲�ƂɌ��߂�ꂽ�f�ށi�S�z�΁A����z�΁A�v���`���z�΁A\n"
							+ "�����΁j�̕K�v���ƕ��킲�ƂɌ��߂�ꂽGold���K�v�ł��B\n"
							+ "���̑��A������背�x���̍�������͍쐬�ł��܂���B\n"
							+ "������w������ƕ���ɕt�^���ꂽ�X�e�[�^�X�l�������̃X�e�[�^�X�ɒǉ�����܂��B\n"
							+ "����ɂ͑ϋv�l���ݒ肳��Ă��܂��B�ϋv�l�͈ړ�����Ɠ����Ɍ����Ă����܂��B\n"
							+ "�ϋv�l��0�ɂȂ�Ǝ����̃X�e�[�^�X���畐��̃X�e�[�^�X�l��������܂��B");
					break;
				case 2:
					System.out.println("�A�C�e���V���b�v�ɂ���\n"
							+ "8��ނ̃|�[�V������11��ނ̌������w�����邱�Ƃ��o���܂��B\n"
							+ "�A�C�e�����ƂɕK�vGold���قȂ�A����Gold�ȏ�̃A�C�e���͍w���ł��܂���");
					break;
				case 3:
					System.out.println("�X�L���C����ɂ���\n"
							+ "���x����5�ȏ�ɂȂ�ƁA�V�����X�L���̏C���ƃX�L���̋������s�Ȃ��܂��B\n"
							+ "�V�����X�L���̏C���ɂ�1500Gold�A�X�L���̋����ɂ�1000Gold���K�v�ł��B\n"
							+ "�V�����X�L���̏C���͎����̃��x���ɉ����ĐV�����X�L�����C���ł��܂��B\n"
							+ "�X�L���̋����͏n���x��20�̔{���𒴂��邲�ƂɃX�L���̋������ł��܂��B\n"
							+ "�����ɂ́A�З͂�30�㏸��A������1�����A�N�[���^�C����5�^�[������������܂��B\n"
							+ "�Ȃ��A�����񐔂ɂ͏��������A����𒴂����X�L���͋������ł��܂���B");
					break;
				case 4:
					System.out.println("�z�[���ɂ���\n"
							+ "�}�[�P�b�g���烁�C���}�b�v�Ɉړ����܂��B");
					break;
				case 5:
					System.out.println("�A�C�e���ɂ���\n"
							+ "�A�C�e�����E���܂��B\n"
							+ "�������̍ő及�����𒴂����ꍇ�A�C�e�����E�����Ƃ��ł��܂���B\n"
							+ "�A�C�e���̎�ނ͎��2��ނŁA��̓|�[�V�����⌋���̃v���C���[�����A\n"
							+ "������́A�S�z�΂┒��z�΂Ȃǂ̕���쐬�ɕK�v�ȑf�ނł��B\n"
							+ "�����̃��x�����オ��ɂ�Ď��̗ǂ��A�C�e�����E�����Ƃ��ł��܂��B");
					break;
				case 6:
					System.out.println("�G�ɂ���\n"
							+ "�G�ƑΌ����܂��B\n"
							+ "�����̃��x�����オ��ɂ�ēG�������Ȃ�܂��B\n"
							+ "�G�ɔs�ꂽ�ꍇ�A������������A���C���}�b�v�ɖ߂�܂��B");
					break;
				case 7:
					System.out.println("�{�X�ɂ���\n"
							+ "�{�X�ƑΌ����܂��B\n"
							+ "�����̃��x�����オ��ɂ�ēG�������Ȃ�܂��B\n"
							+ "�{�X�ɏ�������ƃ_���W�����T�������ł��B");
					break;
				case 8:
					System.out.println("�o�g���ɂ���\n"
							+ "AGI�̍�����������ɍU�����ł��܂��B\n"
							+ "�U����i�͒ʏ�U���ƃX�L���U���̓��ނł��B\n"
							+ "�X�L���U���͑I�������X�L���̈З͂ƒʏ�U���̔����̈З͂ɘA������\n"
							+ "�|�����_���[�W��^���܂��B�g�p�����X�L���͏n���x��+1����܂��B�n���x\n"
							+ "��5�オ�邲�ƂɈЗ͂�3UP���܂��B�o�g�����̃N�[���^�C�������ʂ�5�ł��B\n"
							+ "�^����_���[�W�̑��ʂ̓_���[�W���瑊���VIT���������l�ɂȂ�܂��B");
					break;
				}
				
				System.out.print("�Q�Ƃ𑱂��܂����H �sy or n�t >>>");
				redo = br.readLine();
			}else {
				redo = "n";
			}
		}
		System.out.println("\n��������������������������������������������������������������������������������������");
	}
}


