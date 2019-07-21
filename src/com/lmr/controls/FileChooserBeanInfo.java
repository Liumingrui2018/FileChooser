package com.lmr.controls;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class FileChooserBeanInfo extends SimpleBeanInfo {
	
	private PropertyDescriptor[] propertyDescriptors;
	
	public FileChooserBeanInfo() {
		Class<FileChooser> beanClass = FileChooser.class;
		try {
			PropertyDescriptor multiDescriptor = new PropertyDescriptor("multiSelection", beanClass);
			PropertyDescriptor extensionsDescriptor = new PropertyDescriptor("extensions",beanClass);
			PropertyDescriptor selectionModeDescriptor = new PropertyDescriptor("selectionMode", beanClass);
			PropertyDescriptor editableDescriptor = new PropertyDescriptor("editable", beanClass);
			PropertyDescriptor selectedFilesDescriptor = new PropertyDescriptor("selectedFiles", beanClass);
			PropertyDescriptor columCountDescriptor = new PropertyDescriptor("columCount", beanClass);
			propertyDescriptors = new PropertyDescriptor[]{multiDescriptor,extensionsDescriptor,selectionModeDescriptor,editableDescriptor,selectedFilesDescriptor,columCountDescriptor};
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return propertyDescriptors;
	}
	
}
