package ca.qc.bdeb.p55.smartshopplus.modele;

import android.content.Context;

/**
 * Created by Catalin on 2016-09-14.
 */
public class Client { // ERASE CLASS, FOR REFERENCE PURPOSES ONLY
    private long id;
    private String nom;
    private int age;
    private Sexe sexe;
    private String adresse;
    private String ville;
    private String courriel;

    public Client(String nom, int age, Sexe sexe, String adresse, String ville, String courriel) {
        id = -1;
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
        this.adresse = adresse;
        this.ville = ville;
        this.courriel = courriel;
    }

    public Client(long id, String nom, int age, Sexe sexe, String adresse, String ville, String courriel) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
        this.adresse = adresse;
        this.ville = ville;
        this.courriel = courriel;
    }

    public Client() {
    }


    public enum Sexe {
        HOMME(0),
        FEMME(1),
        AUTRE(2),
        INCONNU(-1);

        private int sexe;

        Sexe(int sexe) {
            this.sexe = sexe;
        }

        public int getSexeInt() {
            return sexe;
        }

        public static Sexe getSexeFromEntier(int entier) {
            Sexe sexe = INCONNU;

            switch (entier) {
                case 0:
                    sexe = HOMME;
                    break;
                case 1:
                    sexe = FEMME;
                    break;
                case 2:
                    sexe = AUTRE;
                    break;
                default:
                    sexe = INCONNU;
                    break;
            }
            return sexe;
        }
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public int getSexeInteger() {
        return sexe.getSexeInt();
    }

    public String getSexeString(Context context) {
        String sexeString = "";
        switch (sexe) {
            case HOMME:
           //     sexeString = context.getResources().getString(R.string.client_sexe_homme);
                break;
            case FEMME:
          //      sexeString = context.getResources().getString(R.string.client_sexe_femme);
                break;
            case AUTRE:
            //    sexeString = context.getResources().getString(R.string.client_sexe_autre);
                break;
            default:
           //     sexeString = context.getResources().getString(R.string.client_sexe_inconnu);
                break;
        }

        return sexeString;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
