package de.hszigr.gpics.primefaces_beans;

import de.hszigr.gpics.util.MessagePropertiesBean;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import javax.faces.bean.ManagedBean;


/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class MenuBean {

//	private MenuModel menuModel = new DefaultMenuModel();
    private MenuModel breadcrumbModel = new DefaultMenuModel();
//    private String site = "index";
//
//    Vector<String> vec = new Vector<String>();

	public MenuBean() {

        MessagePropertiesBean msgPB = new MessagePropertiesBean();
//
//        site = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().substring(1,  FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath().indexOf("."));
//
//        createMenuModel(msgPB);
        createBreadcrumbModel(msgPB);
//
//
////
////        File dir = new File(System.getenv("CATALINA_HOME")+System.getProperty("file.separator")+"webapps"+System.getProperty("file.separator")+"GPicS");
////        String suffix = "xhtml";
////        getAllFilesEndingWithRecursive(dir, suffix);
////
////        for (int i = 0; i < vec.size(); i++){
////            System.out.println(vec.get(i));
////        }
////
//
//
	}
//
//    private void createMenuModel(MessagePropertiesBean msgPB){
//        UserController uc = (UserController) GPicSUtil.getBean("userController");
//        AlbumController ac = (AlbumController) GPicSUtil.getBean("albumController");
//
//	 	//Startseite
//	 	Submenu submenu = new Submenu();
//	 	submenu.setLabel(msgPB.getPropertiesMessage("gpics"));
//
//	 	MenuItem item = new MenuItem();
//	 	item.setValue(msgPB.getPropertiesMessage("home"));
//	 	item.setUrl("index.xhtml");
//	 	submenu.getChildren().add(item);
//
//        menuModel.addSubmenu(submenu);
//
//
//	 	submenu = new Submenu();
//	 	submenu.setLabel(msgPB.getPropertiesMessage("albums"));
//
//        //Eigene Alben
//        if (uc.isEingeloggt()){
//          item = new MenuItem();
//	 	    item.setValue(msgPB.getPropertiesMessage("ownAlbums"));
//	 	    item.setUrl("showOwnAlbum.xhtml");
//	 	    submenu.getChildren().add(item);
//        }
//
//        //alle Alben
//        item = new MenuItem();
//	 	item.setValue(msgPB.getPropertiesMessage("allAlbums"));
//	 	item.setUrl("allAlbums.xhtml");
//	 	submenu.getChildren().add(item);
//
//
//
//
////        FacesContext f = FacesContext.getCurrentInstance();
////
////        ELContext elc = f.getELContext();
////        ExpressionFactory ef = ExpressionFactory.newInstance();
////
////        ValueExpression expr = ef.createValueExpression(elc, "${userController}", UserController.class);
////        UserController uc = (UserController)expr.getValue(elc);
//
//        //Album erstellen
//        if (uc.isEingeloggt()){
//            item = new MenuItem();
//	 	    item.setValue(msgPB.getPropertiesMessage("createAlbum"));
//	 	    item.setUrl("createAlbum.xhtml");
//	 	    submenu.getChildren().add(item);
//        }
//
//	 	menuModel.addSubmenu(submenu);
//    }
//
    private void createBreadcrumbModel(MessagePropertiesBean msgPB){


        MenuItem itemBread = new MenuItem();
	 	itemBread.setValue(msgPB.getPropertiesMessage("home"));
        itemBread.setUrl("index.xhtml");
        breadcrumbModel.addMenuItem(itemBread);


    }
//
//	public MenuModel getMenuModel() {
//	    return menuModel;
//	}
//
    public MenuModel getBreadcrumbMenuModel() {
	    return breadcrumbModel;
	}
//
//    public String getSite() {
//        return site;
//    }
//
//    public void setSite(String site) {
//        this.site = site;
//    }
//
//    private void getAllFilesEndingWithRecursive(File dir, String suffix) {
//
//        if ((dir != null) && dir.exists()) {
//            for (File file : dir.listFiles()) {
//                if (file.isDirectory()) {
//                    getAllFilesEndingWithRecursive(file, suffix);
//                } else {
//                    if (file.getName().endsWith(suffix)) {
//                        vec.add(file.getPath());
//                    }
//                }
//            }
//        }
//    }

}
