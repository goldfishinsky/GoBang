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
		case "新游戏":
			p.replay();
			break;
		case "悔棋":
			p.regret();
			break;
		case "认输":
			p.defeat();
			break;
		default:
			System.out.println("你按了啥");
			break;
		}
		switch (box.getSelectedIndex()) {
			//人机对战
			case 0:
				p.battleType = 0;
				break;
			//人人对战
			case 1:
				p.battleType = 1;
				break;
			//机机对战
			case 2:
				p.battleType = 2;
				break;
			default:
				System.out.println("你选了什么。。。");
				break;
		}
	}

}
