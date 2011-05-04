package de.hszigr.gpics.primefaces_beans;

import de.hszigr.gpics.util.MessagePropertiesBean;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class MenuBean {

	private MenuModel menuModel;
    private MenuModel breadcrumbModel;
    private String site = "index";

    Vector<String> vec = new Vector<String>();

	public MenuBean() {



        File dir = new File(System.getenv("CATALINA_HOME")+System.getProperty("file.separator")+"webapps"+System.getProperty("file.separator")+"GPicS");
        String suffix = "xhtml";
        getAllFilesEndingWithRecursive(dir, suffix);

        for (int i = 0; i < vec.size(); i++){
            System.out.println(vec.get(i));
        }







        site = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().substring(1,  FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().indexOf("."));

        System.out.println(site);

        MessagePropertiesBean msgPB = new MessagePropertiesBean();

		menuModel = new DefaultMenuModel();
        breadcrumbModel = new DefaultMenuModel();

        MenuItem itemBread = new MenuItem();
	 	itemBread.setValue(msgPB.getPropertiesMessage("home"));
        itemBread.setUrl("index.xhtml");
        breadcrumbModel.addMenuItem(itemBread);

        
	 	//First submenu
	 	Submenu submenu = new Submenu();
	 	submenu.setLabel("GPicS");

	 	MenuItem item = new MenuItem();
	 	item.setValue(msgPB.getPropertiesMessage("home"));
	 	item.setUrl("index.xhtml");
	 	submenu.getChildren().add(item);

        menuModel.addSubmenu(submenu);

	 	//Second submenu
	 	submenu = new Submenu();
	 	submenu.setLabel("Alben");

        item = new MenuItem();
	 	item.setValue(msgPB.getPropertiesMessage("allAlbums"));
	 	item.setUrl("showAlbum.xhtml");
	 	submenu.getChildren().add(item);

        if (site.equals("showAlbum")){
            item = new MenuItem();
	 	    item.setValue(msgPB.getPropertiesMessage("createAlbum"));
	 	    item.setUrl("createAlbum.xhtml");
	 	    submenu.getChildren().add(item);
        }

	 	menuModel.addSubmenu(submenu);
	 }
    
	public MenuModel getMenuModel() {
	    return menuModel;
	}

    public MenuModel getBreadcrumbMenuModel() {
	    return breadcrumbModel;
	}

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


    private void getAllFilesEndingWithRecursive(File dir, String suffix) {

        if ((dir != null) && dir.exists()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    getAllFilesEndingWithRecursive(file, suffix);
                } else {
                    if (file.getName().endsWith(suffix)) {
                        vec.add(file.getPath());
                    }
                }
            }
        }
    }

    
}
