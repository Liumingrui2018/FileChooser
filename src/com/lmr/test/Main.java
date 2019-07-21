package com.lmr.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.lmr.controls.FileChooser;
import com.lmr.controls.FileChooser.FileSelectedEvent;

public class Main extends JFrame {
	JLabel imgLabel = new JLabel();
	
	public Main() {
		super("FileChooserTest");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setColumCount(15);
		fileChooser.setMultiSelection(false);
		fileChooser.setExtensions(new String[] {"jpg","jpeg","png","gif"});
		fileChooser.addFileSelectedListener(new FileChooser.FileSelectedListener() {
			
			@Override
			public void fileSelected(FileSelectedEvent evt) {
				// TODO Auto-generated method stub
				File imgFile = evt.getSelectedFiles()[0];
				try {
					imgLabel.setIcon(new ImageIcon(ImageIO.read(imgFile)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.getContentPane().add(fileChooser, "North");
		imgLabel.setSize(50,50);
		this.getContentPane().add(imgLabel,"Center");
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
		main.setVisible(true);
	}

}
