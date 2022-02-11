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
		
		//�X�e�[�^�X�ƃX�L���̕\��
		System.out.println("\n�v���C���[���");
		main.on_display_name(name);
		main.on_display_efficacy(pl_efficacy);	//efficacy�Ɋ֘A�����ꏊ�Ńf�[�^��null�ɂȂ��Ă���
		main.on_display_skil(pl_skil);
		main.on_display_item(pl_belonging);
		main.on_display_arsenal(pl_arsenal);
		
		
		if(!(pl_belonging[0].equals("no")))	{	//�A�C�e�����������Ă�����\��
			System.out.print("�A�C�e�����g�p���܂����H�sy or n �ǂ��炩���́t\t>>>");
			use = br.readLine();
			if(use.equals("n")) {
				redo = false;
			}
			while(redo) {
				main.on_display_item(pl_belonging);
				
				/* �A�C�e���̎g�p */
				SAO_use_item use_item = ui.method(pl_status, pl_efficacy, pl_belonging);
				pl_status = use_item.player_status;
				pl_efficacy = use_item.player_efficacy;
				pl_belonging = use_item.player_belonging;
				/* �A�C�e���̘A���g�p */
				System.out.print("�A�C�e���𑱂��Ďg�p���܂����H�sy or n �ǂ��炩���́t\t>>>");
				use = br.readLine();
				if(use.equals("n")) {
					redo = false;
				}
			}
		}else {
			System.out.print("�A�C�e�����������Ă��܂���");
		}
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_belonging = pl_belonging;
		return output;
	}
}
/****************************************************************************************
 * ���������g�p����Ƃ��̃v���O����
 * item_display�g�p��
 ****************************************************************************************/
class SAO_use_item{
	int[] player_status;
	int[][] player_efficacy;
	String[] player_belonging;
	public SAO_use_item method(int[] pl_status, int[][] pl_efficacy, String[] pl_belonging) throws IOException {
		SAO_use_item output = new SAO_use_item();
		//�g�p���鎝�����̑I��
		int item_num = 0;
		String print_item = null;
		boolean hyouji = true;
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
		while(true) {
			String print = "�g�p����A�C�e����ԍ��œ��́s0�ŃL�����Z���t>>>";
			item_num = enter_number.method(print)-1;
			if(item_num>=-1 && item_num <= pl_belonging.length) {
				break;
			}
		}
		if(item_num != -1) {
			/* �I�������A�C�e�����g�p�ł��邩���� */
			switch(pl_belonging[item_num]) {
			case "00":
				pl_status[3] += 30;
				print_item = "HP��30�񕜂��܂���";
				break;
			case "01":
				pl_status[3] += 50;
				print_item = "HP��50�񕜂��܂���";
				break;
			case "02":
				pl_status[3] += 100;
				print_item = "HP��100�񕜂��܂���";
				break;
			case "03":
				pl_status[3] += 250;
				print_item = "HP��250�񕜂��܂���";
				break;
			case "04":
				if(pl_efficacy[5][0] != 0) {
					pl_status[5] -= pl_efficacy[5][0];
				}
				pl_status[5] += 50;
				pl_efficacy[5][0] = 50;
				pl_efficacy[5][1] = 20;
				print_item = "��莞�Ԃ̊�STR��50�������܂�";
				break;
			case "05":
				if(pl_efficacy[6][0] != 0) {
					pl_status[6] -= pl_efficacy[6][0];
				}
				pl_status[6] += 30;
				pl_efficacy[6][0] = 30;
				pl_efficacy[6][1] = 20;
				print_item = "��莞�Ԃ̊�AGI��30�������܂�";
				break;
			case "06":
				if(pl_efficacy[7][0] != 0) {
					pl_status[7] -= pl_efficacy[7][0];
				}
				pl_status[7] += 30;
				pl_efficacy[7][0] = 30;
				pl_efficacy[7][1] = 20;
				print_item = "��莞�Ԃ̊�VIT��30�������܂�";
				break;
			case "07":
				pl_status[3] += pl_status[4] * 3 / 10;
				print_item = "�ő�HP��30���񕜂��܂���";
				break;
			case "08":
				pl_status[3] += pl_status[4] * 6 / 10;
				print_item = "�ő�HP��60���񕜂��܂���";
				break;
			case "09":
				pl_status[3] = pl_status[4];
				print_item = "HP��100���񕜂��܂���";
				break;
			case "10":
				if(pl_efficacy[5][0] != 0) {
					pl_status[5] -= pl_efficacy[5][0];
				}
				pl_efficacy[5][0] = pl_status[5];
				pl_efficacy[5][1] = 40;
				pl_status[5] += pl_efficacy[5][0];
				print_item = "��莞�Ԃ̊�STR��2�{�ɂȂ�܂�";
				break;
			case "11":
				if(pl_efficacy[6][0] != 0) {
					pl_status[6] -= pl_efficacy[6][0];
				}
				pl_efficacy[6][0] = pl_status[6];
				pl_efficacy[6][1] = 40;
				pl_status[6] += pl_efficacy[6][0];
				print_item = "��莞�Ԃ̊�AGI��2�{�ɂȂ�܂�";
				break;
			case "12":
				if(pl_efficacy[7][0] != 0) {
					pl_status[7] -= pl_efficacy[7][0];
				}
				pl_efficacy[7][0] = pl_status[7];
				pl_efficacy[7][1] = 40;
				pl_status[7] += pl_efficacy[7][0];
				print_item = "��莞�Ԃ̊�VIT��2�{�ɂȂ�܂�";
				break;
			case "25":
				if(pl_efficacy[8][0] != 0) {
					pl_status[4] -= pl_efficacy[8][0];
				}
				pl_status[3] += 80;
				pl_status[4] += 80;
				pl_efficacy[8][0] = 80;
				pl_efficacy[8][1] = 20;
				print_item = "��莞�Ԃ̊�HP��80�������܂�";
				break;
			case "26":
				if(pl_efficacy[8][0] != 0) {
					pl_status[4] -= pl_efficacy[8][0];
				}
				pl_efficacy[8][0] = pl_status[4];
				pl_efficacy[8][1] = 40;
				pl_status[3] += pl_efficacy[8][0];
				pl_status[4] += pl_efficacy[8][0];
				print_item = "��莞�Ԃ̊�HP��2�{�ɂȂ�܂�";
				break;
			case "27":
				if(pl_efficacy[5][0] != 0) {
					pl_status[5] -= pl_efficacy[5][0];
				}
				pl_efficacy[5][0] = pl_status[5] * 2;
				pl_efficacy[5][1] = 60;
				pl_status[5] += pl_efficacy[5][0];
				print_item = "��莞�Ԃ̊�STR��3�{�ɂȂ�܂�";
				break;
			case "28":
				if(pl_efficacy[6][0] != 0) {
					pl_status[6] -= pl_efficacy[6][0];
				}
				pl_efficacy[6][0] = pl_status[6] * 2;
				pl_efficacy[6][1] = 60;
				pl_status[6] += pl_efficacy[6][0];
				print_item = "��莞�Ԃ̊�AGI��3�{�ɂȂ�܂�";
				break;
			case "29":
				if(pl_efficacy[7][0] != 0) {
					pl_status[7] -= pl_efficacy[7][0];
				}
				pl_efficacy[7][0] = pl_status[7] * 2;
				pl_efficacy[7][1] = 60;
				pl_status[7] += pl_efficacy[7][0];
				print_item = "��莞�Ԃ̊�VIT��3�{�ɂȂ�܂�";
				break;
			case "30":
				if(pl_efficacy[8][0] != 0) {
					pl_status[4] -= pl_efficacy[8][0];
				}
				pl_efficacy[8][0] = pl_status[4] * 2;
				pl_efficacy[8][1] = 60;
				pl_status[3] += pl_efficacy[8][0];
				pl_status[4] += pl_efficacy[8][0];
				print_item = "��莞�Ԃ̊�HP��3�{�ɂȂ�܂�";
				break;
			default:
				hyouji = false;
			}
			/*
			player_status = 
			[0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
				5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A9�c�R���V�A���A���L�^]
			
			player_efficacy = 
			[0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
			5�c�U���́A6�c�f�����A7�c�h���][0�c���ʁA1�c�c�����]
			*/
			
			
			if(hyouji == false) {
				System.out.println("���̃A�C�e���͌��ݎg�p�ł��܂���I");
			}else {
				System.out.println(item_all_name[Integer.parseInt(pl_belonging[item_num])][1] 
						+ "���g�p���܂���\n" + print_item);
				pl_belonging[item_num] = "no";
			}
		}
		
		
		output.player_status = pl_status;
		output.player_efficacy = pl_efficacy;
		output.player_belonging = pl_belonging;
		return output;
	}
}

