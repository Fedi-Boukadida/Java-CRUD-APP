import java.sql.Date;

public class Produit {

    @Override
    public String toString() {
        return "Produit [refProduit=" + refProduit + ", descriptifProduit=" + descriptifProduit + ", datePeremption="
                + datePeremption + ", quantiteProduit=" + quantiteProduit + ", prixProduit=" + prixProduit
                + ", refRangement=" + refRangement + ", refIngredient=" + refIngredient + "]";
    }
    
    private int refProduit;
    private String descriptifProduit;
    private Date datePeremption;
    private int quantiteProduit;
    private float prixProduit;
    private int refRangement;
    private int refIngredient;
    
    public Produit(int refProduit, String descriptifProduit, Date datePeremption, int quantiteProduit, float prixProduit, int refRangement, int refIngredient) {
        this.refProduit = refProduit;
        this.descriptifProduit = descriptifProduit;
        this.datePeremption = datePeremption;
        this.quantiteProduit = quantiteProduit;
        this.prixProduit = prixProduit;
        this.refRangement = refRangement;
        this.refIngredient = refIngredient;
    }

    public int getRefProduit() {
        return refProduit;
    }

    public void setRefProduit(int refProduit) {
        this.refProduit = refProduit;
    }

    public String getDescriptifProduit() {
        return descriptifProduit;
    }

    public void setDescriptifProduit(String descriptifProduit) {
        this.descriptifProduit = descriptifProduit;
    }

    public Date getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }

    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public float getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(float prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getRefRangement() {
        return refRangement;
    }

    public void setRefRangement(int refRangement) {
        this.refRangement = refRangement;
    }

    public int getRefIngredient() {
        return refIngredient;
    }

    public void setRefIngredient(int refIngredient) {
        this.refIngredient = refIngredient;
    }
}