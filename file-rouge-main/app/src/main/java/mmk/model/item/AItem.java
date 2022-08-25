package mmk.model.item;

import mmk.model.util.ASavable;

/**
 * représente un item
 */
public abstract class AItem extends ASavable {

    //#region attributs
    protected String name;
    protected String iconURL="icon";
    protected int id;
    protected int idPersonnage=0;
    protected int rarity =1;
    //#endregion


    protected void setName(String name){
        this.name = name;
    }
    public void setIcon(String icon){
        this.iconURL=icon;
    }
    protected String getName(){
        return name;
    }
    protected String getIconURL(){
        return iconURL;
    }
    public int getIdPersonnage() {
        return idPersonnage;
    }
    public void setIdPersonnage(int idPersonnage) {
        this.idPersonnage = idPersonnage;
    }
    public void setRarity(int r){
        this.rarity=r;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
}
