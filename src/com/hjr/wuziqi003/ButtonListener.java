package com.hjr.wuziqi003;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class ButtonListener implements ActionListener{
	playListener p;
	JComboBox<String> box;
	public ButtonListener(playListener p,JComboBox<String> box) {
		// TODO Auto-generated constructor stub
		this.p = p;
		this.box =box;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		switch (e.getActionCommand()) {
		case "����Ϸ":
			p.replay();
			break;
		case "����":
			p.regret();
			break;
		case "����":
			p.defeat();
			break;
		default:
			System.out.println("�㰴��ɶ");
			break;
		}
		switch (box.getSelectedIndex()) {
			//�˻���ս
			case 0:
				p.battleType = 0;
				break;
			//���˶�ս
			case 1:
				p.battleType = 1;
				break;
			//������ս
			case 2:
				p.battleType = 2;
				break;
			default:
				System.out.println("��ѡ��ʲô������");
				break;
		}
	}

}
