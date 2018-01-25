package org.hermann.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ChessFileFilter extends FileFilter {
	/** The filefilter filters the files shown in the save/load window,
	 *  other files will be hidden (folders will still be visible and there
	 *  is still the option to view all files)
	 * */
	@Override
	public boolean accept(File file){
		if(file.isDirectory()){
			return true;
		}
		return file.getName().endsWith(".chs");
	}
	
	@Override
	public String getDescription(){
		return "*.chs";
	} 
	
}
