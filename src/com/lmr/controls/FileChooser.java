package com.lmr.controls;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JPanel {
	private JTextField textField;
	private JButton browserBtn;
	private JFileChooser fileChooser;
	private enum SelectionMode{FILES_ONLY,DIRECTORIES_ONLY,FILES_AND_DIRECTORIES};
	//属性
	private boolean multiSelection;//是否可以选择多个文件
	private String[] extensions;//用户可选的文件类型
	private SelectionMode selectionMode;//选择文件还是目录
	private boolean editable;//文本框是否可编辑
	private File[] selectedFiles;//已选的文件
	private int columCount = 10;//文本框的列数
	
	public FileChooser() {
		//初始化组件及属性
		textField = new JTextField();
		browserBtn = new JButton("浏览");
		fileChooser = new JFileChooser();
		editable = true;
		selectionMode = SelectionMode.FILES_ONLY;
		//注册事件
		browserBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int option = fileChooser.showOpenDialog(FileChooser.this);
				if(option==JFileChooser.APPROVE_OPTION) {
					if(fileChooser.isMultiSelectionEnabled()) {
						File[] files = fileChooser.getSelectedFiles();
						setSelectedFiles(files);
					}else {
						File file = fileChooser.getSelectedFile();
						setSelectedFiles(new File[] {file});
					}
					//在文本框中显示已选择的文件
					StringBuilder builder = new StringBuilder();
					for(int i=0;i<selectedFiles.length;i++) {
						File file = selectedFiles[i];
						builder.append(file.getAbsolutePath());
						if(i<selectedFiles.length-1)
							builder.append(';');
					}
					textField.setText(builder.toString());
				}
			}
			
		});
		textField.addFocusListener(new FocusListener() {
			private String preValue = textField.getText();
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				String setValue = textField.getText();
				boolean accept = false;
				String[] filePaths = setValue.split(";");
				for(int i=0;i<filePaths.length;i++) {
					File file = new File(filePaths[i]);
					switch(selectionMode) {
					case FILES_ONLY:accept = Files.isRegularFile(file.toPath());
					case DIRECTORIES_ONLY:accept = Files.isDirectory(file.toPath());
					case FILES_AND_DIRECTORIES:accept = Files.isRegularFile(file.toPath())||Files.isDirectory(file.toPath());
					}
					if(!accept) {
						textField.setText(preValue);
					}
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				preValue = textField.getText();
			}
		});
//		textField.addVetoableChangeListener(new VetoableChangeListener() {
//			
//			@Override
//			public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
//				// TODO Auto-generated method stub
//				if(evt.getPropertyName().equals("text")){
//					String newValue = (String) evt.getNewValue();
//					boolean accept = false;
//					String[] filePaths = newValue.split(";");
//					for(int i=0;i<filePaths.length;i++) {
//						File file = new File(filePaths[i]);
//						switch(selectionMode) {
//						case FILES_ONLY:accept = Files.isRegularFile(file.toPath());
//						case DIRECTORIES_ONLY:accept = Files.isDirectory(file.toPath());
//						case FILES_AND_DIRECTORIES:accept = Files.isRegularFile(file.toPath())||Files.isDirectory(file.toPath());
//						}
//						if(!accept) {
//							throw new PropertyVetoException("无效的路径名", evt);
//						}
//					}
//				}
//				System.out.println(evt);
//			}
//		});
//		textField.addPropertyChangeListener("text", new PropertyChangeListener() {
//			
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				// TODO Auto-generated method stub
//				String newValue = (String) evt.getNewValue();
//				String[] filePaths = newValue.split(";");
//				java.util.List<File> files = new ArrayList<File>();
//				for(String filePath : filePaths) {
//					File file = new File(filePath);
//					files.add(file);
//				}
//				setSelectedFiles((File[]) files.toArray());
//				System.out.println(evt);
//			}
//		});
		//添加组件
		this.add(textField);
		this.add(browserBtn);
	}
	//事件
	/**
	 * 对{@link com.lmr.controls.FileChooser.FileSelectedEvent}事件对象的监听器
	 * @author lmr
	 *
	 */
	public interface FileSelectedListener extends EventListener{
		void fileSelected(FileSelectedEvent evt);
	}
	/**
	 * 当文件选择完毕后触发该事件类的对象
	 * @author lmr
	 *
	 */
	public class FileSelectedEvent extends EventObject{
		private File[] selectedFiles;
		
		public FileSelectedEvent(Object source) {
			super(source);
		}
		public FileSelectedEvent(Object source,File[] selectedFiles) {
			super(source);
			this.selectedFiles = selectedFiles;
		}
		public File[] getSelectedFiles() {
			return selectedFiles;
		}
		public void setSelectedFiles(File[] selectedFiles) {
			this.selectedFiles = selectedFiles;
		}
	}
	private java.util.List<FileSelectedListener> listeners = new java.util.LinkedList<FileSelectedListener>();
	public boolean isMultiSelection() {
		return multiSelection;
	}
	public void setMultiSelection(boolean multi) {
		this.multiSelection = multi;
		fileChooser.setMultiSelectionEnabled(multi);
	}
	public String[] getExtensions() {
		return extensions;
	}
	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
		fileChooser.setFileFilter(new FileNameExtensionFilter("",extensions));
	}
	public SelectionMode getSelectionMode() {
		return selectionMode;
	}
	public void setSelectionMode(SelectionMode selectionMode) {
		this.selectionMode = selectionMode;
		fileChooser.setFileSelectionMode(selectionMode.ordinal());
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
		textField.setEditable(editable);
	}
	public File[] getSelectedFiles() {
		return selectedFiles;
	}
	public void setSelectedFiles(File[] selectedFiles) {
		this.selectedFiles = selectedFiles;
		//通知监听器们文件已被选择
		for(FileSelectedListener listener : listeners) {
			listener.fileSelected(new FileSelectedEvent(this, selectedFiles));
		}
	}
	public int getColumCount() {
		return columCount;
	}
	public void setColumCount(int columCount) {
		this.columCount = columCount;
		textField.setColumns(columCount);
	}
	public void addFileSelectedListener(FileSelectedListener listener) {
		listeners.add(listener);
	}
	public void removeFileSelectedListener(FileSelectedListener listener) {
		listeners.remove(listener);
	}
	
}