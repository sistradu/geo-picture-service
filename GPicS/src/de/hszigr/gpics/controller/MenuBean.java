package de.hszigr.gpics.controller;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;


/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class MenuBean {

	private MenuModel model;

	public MenuBean() {
		model = new DefaultMenuModel();

	 	//First submenu
	 	Submenu submenu = new Submenu();
	 	submenu.setLabel("Startseite");

	 	MenuItem item = new MenuItem();
	 	item.setValue("Startseite");
	 	item.setUrl("index.xhtml");
	 	submenu.getChildren().add(item);

        model.addSubmenu(submenu);

	 	//Second submenu
	 	submenu = new Submenu();
	 	submenu.setLabel("öffentliche Alben");

	 	item = new MenuItem();
	 	item.setValue("Dynamic Menuitem 2.1");
	 	item.setUrl("#");
	 	submenu.getChildren().add(item);


	 	model.addSubmenu(submenu);
	 }
	 public MenuModel getModel() {
	 	 return model;
	 }
}
}
