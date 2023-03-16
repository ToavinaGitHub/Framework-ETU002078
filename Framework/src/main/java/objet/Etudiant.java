package objet;

import etu2078.framework.annotation.Url;

public class Etudiant{
    int idEtudiant;
    String nomEtudiant;
    String prenomEtudiant;

    public Etudiant(int idEtudiant, String nomEtudiant, String prenomEtudiant) {
        this.idEtudiant = idEtudiant;
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
    }



    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    @Url(urlName = "afficher-etudiant")
    public static void AfficherEtudiant()
    {
        System.out.println("Affichage etudiant");
    }
}