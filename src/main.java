import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class main {
	/********************************************************************************
	 * ���C���v���O����
	 ********************************************************************************/
	public static void main(String[] args) throws IOException {
		player_basic_data pbd = new player_basic_data();
		
		map pm = new map();
		location pl = new location();
		all_event em = new all_event();
		data_save sds = new data_save();
		map map = pm.method();
		
		/* �v���C���[�̃f�[�^ */
		String[] name = pbd.name;
		String[] player_belonging = pbd.player_belonging;
		int[] player_status = pbd.player_status;
		int[][] player_efficacy = pbd.player_efficacy;
		int[][] player_skil = pbd.player_skil;
		int[] player_point = pbd.player_point;
		int[] player_arsenal = pbd.arsenal;
		boolean logout = pbd.logout;
		String[][][] main_map = map.map;
		
		/* �f�[�^�Z�[�u�̐ݒ� */
		boolean Auto_save = true;	//
		
		/* �C�x���g���� */
		String event = "no";
		
		/* �����@�}�b�v�ƃX�e�[�^�X�̕\�� �i�ŏ��̈�񂾂��j*/
		on_display_map(main_map[0],player_point);
		on_display_status(player_status,name[0]);
		
		while(logout == false) {
			main_map = map.map;
			
			/* �v���C���[�̑��� */
			location player_location = pl.method(main_map, player_point, player_belonging, player_status, player_efficacy, player_skil, name, player_arsenal, event);
			player_point = player_location.player_point;
			player_belonging = player_location.player_belonging;
			player_status = player_location.player_status;
			player_efficacy = player_location.player_efficacy;
			player_skil = player_location.player_skil;
			player_arsenal = player_location.player_arsenal;
			event = player_location.event;
			logout = player_location.logout;
			
			if(logout == false) {	//���O�A�E�g���Ă��Ȃ��ꍇ�̓C�x���g���������s
				/* �ړ������̃}�b�v�ƃX�e�[�^�X�̕\�� */
				on_display_map(main_map[player_point[0]],player_point);
				on_display_status(player_status,name[0]);
				
				/* �C�x���g�������s�Ȃ� */
				all_event main = em.move(event, name, player_point, player_status, player_efficacy, player_skil, player_belonging, player_arsenal);
				event = main.event;
				player_point = main.player_point;
				player_status = main.player_status;
				player_efficacy = main.player_efficacy;
				player_skil = main.player_skil;
				player_belonging = main.player_belonging;
				player_arsenal = main.player_arsenal;
				
				//System.out.println("main_last event>>> " + event);
			}
			/* Auto save�@�̎��s */
			data_save save_data = sds.data_save(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal, Auto_save);
			
		}
		Auto_save = false;
		data_save save_data = sds.data_save(name, player_status, player_efficacy, player_skil, player_belonging, player_arsenal, Auto_save);
		
		System.out.println("\n---------------------Logout---------------------");
	}
	/********************************************************************************
	 * �}�b�v��\������v���O����
	 ********************************************************************************/
	public static void on_display_map(String[][] map_2d,int[] player_point) {
		System.out.println("\n�}�b�v�\��");
		String point = map_2d[player_point[1]][player_point[2]];	//�v���C���[�̈ړ��ꏊ�̃C�x���g
		map_2d[player_point[1]][player_point[2]] = "��";				//�v���C���[�̌��ݒn
		for(int y = 0;y < map_2d.length;y++) {
			for(int x = 0;x < map_2d[y].length;x++) {
				System.out.print(map_2d[y][x]+" ");
			}System.out.println();
		}
		if(player_point[0] == 2) {									//�_���W�����̏ꍇ�ʂ����ꏊ�̓C�x���g������
			map_2d[player_point[1]][player_point[2]] = " ";
		}else {														//�ړ���A�C�x���g�̕\����߂�
			map_2d[player_point[1]][player_point[2]] = point;
		}
		/* �v���C���[�̌��ݒn */
		System.out.println("���W�w"+player_point[0]+"."+player_point[1]+"."+player_point[2]+"�x");
	}
	/********************************************************************************
	 * �X�e�[�^�X��\������v���O����
	 ********************************************************************************/
	public static void on_display_status(int[] player_status, String name) {
		System.out.println(name + " Lv_" + player_status[0] + " HP_"+player_status[3] 
				+ "/" + player_status[4] + " STR_" + player_status[5] + " AGI_"
				+ player_status[6] + " VIT_" + player_status[7] + "\n Gold_"
				+ player_status[8] + " EXP_" + player_status[1] 
				+ " ���̃��x���A�b�v�ɕK�v�Ȍo���l " + (player_status[2]-player_status[1]));
		//���x���AHP�A�ő�HP�A�U���́A�f�����A�h��́A�����A�o���l�A�K�v�Ȍo���l
	}
	/********************************************************************************
	 * �X�e�[�^�X��\������v���O����
	 ********************************************************************************/
	public static void on_display_name(String[] name) {
		System.out.println("�v���C���[�l�[��:"+name[0]+"\t�N���X:"+name[1]);
	}
	/********************************************************************************
	 * �X�L���ꗗ��\������v���O����
	 ********************************************************************************/
	public static void on_display_skil(int[][] player_skil) {
		String[] all_skil_name = {
				"�X�����g","�J�^���N�g","�o�[�`�J��",
				"�o�[�`�J���E�A�[�N","�\�j�b�N���[�u","�V���[�v�l�C��",
				"�g���C�A���M�����[","�j���[�g����","�N���[�V�t�B�N�V����",		
				"���e�I�u���C�N","�X�^�[�E�X�v���b�V��","�m���@�E�A�Z���V����",
				"�}�U�[�Y�E���U���I","�X�^�[�o�[�X�g�E�X�g���[��",
		};
		int count = 0;
		System.out.println("�����X�L��");
		for(int m = 0;m < 4;m++) {
			if(player_skil[m][3] != 0) {
				count++;
				System.out.println("[" + count + "]\t" + all_skil_name[player_skil[m][0]] +"\n\t�З�_"
						+ player_skil[m][3] + "\t�ő�N�[���^�C��_" + player_skil[m][2] 
						+ "\t�ő�A����_" + player_skil[m][4] + "\t�n���x_" + player_skil[m][5]);
			}
			
		}
		/*[][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���\�^�C���A3�c�З́A4�c�ő�R���{���A
		  5�c�n���x�A6�c�����񐔁A7�c�ő勭����]*/
	}
	/********************************************************************************
	 * �������̌��ʂ�\������v���O����
	 ********************************************************************************/
	public static void on_display_efficacy(int[][] player_efficacy) {
		boolean buki = false;
		boolean item = false;
		
		System.out.println("�������̕���ɂ�����");
		if(player_efficacy[0][0] != 0) {
			System.out.println(player_efficacy[0][1] + "�^�[���̊�HP��" + player_efficacy[0][0] + "UP");
			buki = true;
		}
		if(player_efficacy[1][0] != 0) {
			System.out.println(player_efficacy[1][1] + "�^�[���̊�STR��" + player_efficacy[1][0] + "UP");
			buki = true;
		}
		if(player_efficacy[2][0] != 0) {
			System.out.println(player_efficacy[2][1] + "�^�[���̊�AGI��" + player_efficacy[2][0] + "UP");
			buki = true;
		}
		if(player_efficacy[3][0] != 0) {
			System.out.println(player_efficacy[3][1] + "�^�[���̊�AGI��" + player_efficacy[3][0] + "UP");
			buki = true;
		}
		if(player_efficacy[4][0] != 0) {
			System.out.println(player_efficacy[4][1] + "�^�[���̊�VIT��" + player_efficacy[4][0] + "UP");
			buki = true;
		}
		if(buki == false) {
			System.out.println("�������Ă��镐��͂���܂���\n");
		}
		
		System.out.println("�g�p���Ă���A�C�e���ɂ�����");
		if(player_efficacy[8][0] != 0) {
			System.out.println(player_efficacy[8][1] + "�^�[���̊�HP��" + player_efficacy[8][0] + "UP");
			item = true;
		}
		if(player_efficacy[5][0] != 0) {
			System.out.println(player_efficacy[5][1] + "�^�[���̊�STR��" + player_efficacy[5][0] + "UP");
			item = true;
		}
		if(player_efficacy[6][0] != 0) {
			System.out.println(player_efficacy[6][1] + "�^�[���̊�AGI��" + player_efficacy[6][0] + "UP");
			item = true;
		}
		if(player_efficacy[7][0] != 0) {
			System.out.println(player_efficacy[7][1] + "�^�[���̊�VIT��" + player_efficacy[7][0] + "UP");
			item = true;
		}
		if(item == false) {
			System.out.println("�������̃A�C�e���͂���܂���\n");
		}
		
		/*player_efficacy = [0�c����HP�A1�c����STR�A2�c����AGI�A3�c����AGI�A4�c����VIT�A5�c�A�C�e��STR�A
		6�c�A�C�e��AGI�A7�c�A�C�e��VIT�A8�cHP][0�c����(�����l��0)�A1�c�c�����(�����l��0)]*/
	}
	/********************************************************************************
	 * �A�C�e����\������v���O����
	 ********************************************************************************/
	public static void on_display_item(String[] player_belonging) {
		
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
		
		/* �A�C�e���̕��ёւ� */
		int cou = 0;
		int amount;
		for(int n = 0;n < player_belonging.length;n++) {
			if(!(player_belonging[n].equals("no"))) {
				player_belonging[cou] = player_belonging[n];
				cou++;
			}
		}
		amount = cou;
		for(;cou < player_belonging.length;cou++) {
			player_belonging[cou] = "no";
		}
		
		
		System.out.println("�������Ă���A�C�e��");
		if(amount == 0) {
			System.out.println("�������͏������Ă��܂���I");
		}else {
			for(int n = 0;n < amount;n++) {
				System.out.println("[" + (n + 1) + "]\t"
						+ item_all_name[Integer.parseInt(player_belonging[n])][1] + "\t: "
						+ item_all_name[Integer.parseInt(player_belonging[n])][2]);
			}
		}
	}
	/********************************************************************************
	 * �f�ނ̐���\������v���O����
	 ********************************************************************************/
	public static void on_display_arsenal(int[] player_arsenal) {
		
		System.out.println("�������Ă���C���S�b�h��");
		System.out.println("�S�z�� "+player_arsenal[1]+"�A����z�� "+player_arsenal[2]+"�A�v���`���z�� "
				+player_arsenal[3]+"�A������ "+player_arsenal[4]+"�A�������Ă��܂��B");
		
		/* 0�E�E�E�S�z�΁@1�E�E�E����z�΁@2�E�E�E�v���`���z�΁@3�E�E�E�����΁@4�E�E�E�Ȃ��@5�E�E�E�Ȃ� */
	}
	
}
/********************************************************************************
 * �Q�[���ɕK�v�ȃf�[�^
 ********************************************************************************/
class player_basic_data{
	String[] name = {"no","no",};
	//[0�c���O�A1�c�N���X]
	
	int[] player_status = new int[10];
	/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A9�c�R���V�A���A���L�^]*/
	
	int[][] player_efficacy = new int[9][2];
	/*[0�c����HP�A1�c����STR�A2�c����AGI�A3�c����AGI�A4�c����VIT�A	5�c�A�C�e��STR�A6�c�A�C�e��AGI�A
	 �@�@7�c�A�C�e��VIT�A8�c�A�C�e��HP][0�c����(�����l��0)�A1�c�c�����(�����l��0)]*/
	
	int[][] player_skil = new int[4][8];
	/*[][0�c�X�L���ԍ��A1�c�N�[���^�C���A2�c�ő�N�[���\�^�C���A3�c�З́A4�c�ő�R���{���A
	  5�c�n���x�A6�c�����񐔁A7�c�ő勭����]*/
	
	int[] player_point = new int[3];
	//[0�c�����W�A1�c�����W�A2�c�����W]
	
	String [] player_belonging = new String[20];
	//[�ԍ�][0~11�c���]�ő及����12�܂�
	
	int[] arsenal = new int[6];
	//[0~5�c�f�ސ�]
	
	boolean logout = false;
	//false�c���O�C���Atrue�c���O�A�E�g
	
	/********************************************************************************
	 * �����ݒ�or�f�[�^���p��
	 ********************************************************************************/
	public player_basic_data() throws IOException{
		data_save sds = new data_save();
		
		BufferedReader br =
				new BufferedReader(new InputStreamReader(System.in));
		Random rand = new Random();
		int ran = rand.nextInt(100);
		
		//�����L�����ݒ�Ŏg�p
		String[] select_name = {
				"���m","�t�F���T�[","�f�B�t�F���_�[","�g���x���[","�M��",
		};
		int[][] select_status = {
				{1,0,50,90,90,70,40,20,500,0},
				{1,0,50,80,80,60,60,15,500,0},
				{1,0,50,100,100,40,30,30,500,0},
				{1,0,50,90,90,50,50,20,500,0},
				{1,0,50,80,80,40,30,15,5000,0},
		};
		/*[	0�c���x���A1�c���݂̌o���l�A2�c���̃��x���A�b�v�ɕK�v�Ȏc��o���l�A3�c���݂�HP�A4�c�ő��HP
		5�c�U���́A6�c�f�����A7�c�h��́A8�c�������A9�c�R���V�A���A���L�^]*/
		
		
		System.out.print("���O����͂��Ă��������B>>>");
	    this.name[0] = br.readLine();
	    
	    //���ڈȍ~�A�f�[�^�̈��p���Ŏg�p
	    data_save data_reading = sds.data_read(name[0]);
	    this.name = data_reading.player_name;
	    this.player_status = data_reading.player_status;
	    this.player_efficacy = data_reading.player_efficacy;
	    this.player_skil = data_reading.player_skil;
	    this.player_belonging = data_reading.player_belonging;
	    this.arsenal = data_reading.player_arsenal;
		
	    //�����L�������C�N
	    if(player_status[0] == 0) {
	    	System.out.println("\n���Ȃ��̔\�͂�f�f���܂��B\nEnter�L�[�������Ă��������B>>>");
			int select_num = (ran + 10) % 5;
			
			/* �A�C�e���{�b�N�X�̍쐬 */
			for(int n = 0;n < player_belonging.length;n++) {
				this.player_belonging[n] = "no";
			}
			
			/* �X�e�[�^�X�̍쐬 */
			this.player_status = select_status[select_num];
			
			/* �N���X�̌��� */
			this.name[1] = select_name[select_num];
			
			/* ���ʂ̍쐬 */
			for(int n = 0;n < 9;n++) {
				this.player_efficacy[n][0] = 0;
				this.player_efficacy[n][1] = 0;
			}
			
			/* �X�L���̍쐬 */
			int[] basic_skil = {0,0,10,60,1,1,0,6};	//��{�X�L���w�X�����g�x
			for(int n = 0;n < 4;n++) {
				for(int m = 0;m < 8;m++) {
					this.player_skil[n][m] = 0;
				}
			}
			this.player_skil[0] = basic_skil;
	 		
			System.out.println("�Ȃ�قǁB���Ȃ��̓V�E�́w"+select_name[select_num]+"�x�ł��ˁB\n"
					+ "�悤���� "+name[0]+"����B\n�I���Ȃ��`���̎n�܂�ł�!!");
	    }else {
	    	System.out.println("\n��������Ȃ����A"+name[0]+"����B\n�`���̑������n�߂܂��傤!!");
	    }
	    
	    this.player_point[0] = 0;
		this.player_point[1] = 5;
		this.player_point[2] = 6;
		
		System.out.println("\n�}�b�v�ɂ���\n"
				+ "��...���ݒn\tM...�}�[�P�b�g\tD...�_���W����\nK...������\tV...�R���V�A��\tW...����쐬\n"
				+ "A...�A�C�e���V���b�v\tH...�z�[��\tS...�X�L���C����\nG...�S�[��\tI...�A�C�e��\tT...�G   \tB...�{�X");
	
	}	
}

