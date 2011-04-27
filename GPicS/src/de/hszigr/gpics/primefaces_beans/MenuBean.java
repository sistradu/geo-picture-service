package de.hszigr.gpics.primefaces_beans;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class MenuBean {

	private MenuModel model;
    private String site = "index";

	public MenuBean() {
		model = new DefaultMenuModel();


        site = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().substring(1,  FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().indexOf("."));


	 	//First submenu
	 	Submenu submenu = new Submenu();
	 	submenu.setLabel("GPicS");

	 	MenuItem item = new MenuItem();
	 	item.setValue("Startseite");
	 	item.setUrl("index.xhtml");
	 	submenu.getChildren().add(item);

        model.addSubmenu(submenu);

	 	//Second submenu
	 	submenu = new Submenu();
	 	submenu.setLabel("Alben");

        item = new MenuItem();
	 	item.setValue("Albumübersicht");
	 	item.setUrl("showAlbum");
	 	submenu.getChildren().add(item);

        if (site.equals("showAlbum")){
            item = new MenuItem();
	 	    item.setValue("Album erstellen");
	 	    item.setUrl("createAlbum");
	 	    submenu.getChildren().add(item);
        }



	 	model.addSubmenu(submenu);
	 }
    
	 public MenuModel getModel() {
	 	 return model;
	 }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
