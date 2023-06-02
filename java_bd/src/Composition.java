public class Composition {
    
    @Override
    public String toString() {
        return "Composition [refComposition=" + refComposition + ", quantiteComposition=" + quantiteComposition
                + ", refRecette=" + refRecette + ", refIngredient=" + refIngredient + "]";
    }

    private int refComposition;
    private int quantiteComposition;
    private int refRecette;
    private int refIngredient;
    
    public Composition(int refComposition, int quantiteComposition, int refRecette, int refIngredient) {
        this.refComposition = refComposition;
        this.quantiteComposition = quantiteComposition;
        this.refRecette = refRecette;
        this.refIngredient = refIngredient;
    }

    public int getRefComposition() {
        return refComposition;
    }

    public void setRefComposition(int refComposition) {
        this.refComposition = refComposition;
    }

    public int getQuantiteComposition() {
        return quantiteComposition;
    }

    public void setQuantiteComposition(int quantiteComposition) {
        this.quantiteComposition = quantiteComposition;
    }

    public int getRefRecette() {
        return refRecette;
    }

    public void setRefRecette(int refRecette) {
        this.refRecette = refRecette;
    }

    public int getRefIngredient() {
        return refIngredient;
    }

    public void setRefIngredient(int refIngredient) {
        this.refIngredient = refIngredient;
    }
}