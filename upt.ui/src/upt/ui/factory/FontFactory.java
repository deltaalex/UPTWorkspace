/**
 * <copyright>
 * 
 * Copyright (c) Alexandru Topirceanu. alex.topirceanu@yahoo.com All rights
 * reserved.
 * 
 * File created by Alexander May 14, 2011 10:19:04 AM </copyright>
 */
package upt.ui.factory;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

public class FontFactory
{
	private static final String FONT_PATH = "config/fonts/CAMBRIA.ttc";
	private static Font FONT;

	static
	{
		File f = new File(FONT_PATH);
		FileInputStream in;
		try
		{
			in = new FileInputStream(f);
			FONT = Font.createFont(Font.TRUETYPE_FONT, in);
		}
		catch (Exception e)
		{
			FONT = new Font("serif", Font.PLAIN, 12);

		}

	}

	public static void setFontSize(Component item, int size)
	{
		Font font = item.getFont();
		item.setFont(new Font("Cambria", font.getStyle(), size));

		FONT.getName();
	}
}
