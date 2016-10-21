package ca.qc.bdeb.p55.smartshopplus.modele;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import ca.qc.bdeb.p55.smartshopplus.R;

/**
 * Created by C A T A on 2016-10-12.
 */

public class ArrayAdapterProduits extends ArrayAdapter<Produit> {

    final String SYMBOLE_MONNAIE_UTILISEE = "$";
    final String SYMBOLE_SEPARATEUR_MONNAIE_SUR_MULTIPLICATEUR_PRIX_UNITAIRE = "/";
    final String SUFFIXE_QUALITE = "/5";

    Context context;

    private ProduitHolder holder;

    public ArrayAdapterProduits(Context context, int ressourceId, List<Produit> items) {
        super(context, ressourceId, items);
        this.context = context;
    }


    public class ProduitHolder {
        TextView txtNom;
        TextView txtQuantite;
        TextView txtPrix;
        TextView txtPrixUnitaire;
        TextView txtQualite;
        ImageView ivwImageProduit;


        public TextView getTxtNom() {
            return txtNom;
        }

        public void setTxtNom(TextView txtNom) {
            this.txtNom = txtNom;
        }

        public TextView getTxtQuantite() {
            return txtQuantite;
        }

        public void setTxtQuantite(TextView txtQuantite) {
            this.txtQuantite = txtQuantite;
        }

        public TextView getTxtPrix() {
            return txtPrix;
        }

        public void setTxtPrix(TextView txtPrix) {
            this.txtPrix = txtPrix;
        }

        public TextView getTxtPrixUnitaire() {
            return txtPrixUnitaire;
        }

        public void setTxtPrixUnitaire(TextView txtPrixUnitaire) {
            this.txtPrixUnitaire = txtPrixUnitaire;
        }

        public ImageView getIvwImageProduit() {
            return ivwImageProduit;
        }

        public void setIvwImageProduit(ImageView ivwImageProduit) {
            this.ivwImageProduit = ivwImageProduit;
        }

        public TextView getTxtQualite() {
            return txtQualite;
        }

        public void setTxtQualite(TextView txtQualite) {
            this.txtQualite = txtQualite;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        Produit rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.relative_layout_liste_produits, null);
            holder = new ProduitHolder();
            holder.txtNom = (TextView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_tvw_nom_produit);
            holder.txtQuantite = (TextView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_tvw_quantite);
            holder.txtPrix = (TextView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_tvw_prix_produit);
            holder.txtPrixUnitaire = (TextView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_tvw_prix_unitaire);
            holder.txtQualite = (TextView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_tvw_qualite);
            holder.ivwImageProduit = (ImageView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_ivw_image_produit);

            convertView.setTag(holder);
        } else {
            holder = (ProduitHolder) convertView.getTag();
        }

        holder.txtNom.setText(rowItem.getNom());
        holder.txtQuantite.setText(getQuantiteString(rowItem));
        holder.txtPrix.setText(getPrixString(rowItem));
        holder.txtPrixUnitaire.setText(getPrixUnitaireString(rowItem));
        holder.txtQualite.setText(getQualiteString(rowItem));
        holder.ivwImageProduit.setImageBitmap(rowItem.getImage());

        return convertView;
    }

    public ProduitHolder getHolder() {
        return holder;
    }


    /**
     * Prend un produit en paramètre et retourne la quantité de ce produit en la formattant
     * pour qu'elle affiche jusqu'à cinq décimales de précision si et seulement si c'est nécessaire
     * 2. Espace
     * 3. Le type de quantité
     *
     * @param produit le produit dont le prix est à convertir en String
     * @return le String de prix qui a été converti selon le frmat expliqué précédemment
     */
    private String getQuantiteString(Produit produit) {
        String quantiteString = "";

        // Arrondit à 5 décimales de précision.
        // Affiche les premières décimales seulement si elles ne valent pas zéro
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.CANADA);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("0.#####", otherSymbols);
        df.setGroupingUsed(true);
        df.setRoundingMode(RoundingMode.CEILING);
        quantiteString = df.format(produit.getQuantite());

        quantiteString = quantiteString + " " + produit.getTypeQuantite();
        return quantiteString;
    }

    /**
     * Prend un produit en paramètre et retourne le prix unitaire de ce produit
     * selon le format suivant:
     * 1. Prix unitaire à 5 décimales de précision
     * 2. Espace
     * 3. Symbole de la monnaie utilisée
     * 4. Symbole séparateur entre monnaie utilisée et quantité unitaire
     * 5. Quantité unitaire
     * 6. Espace
     * 7. Type de quantité
     *
     * @param produit le produit dont le prix unitaire en format String est à retourner
     * @return String de prixu nitaire selon le format expliqué précédemment
     */
    private String getPrixUnitaireString(Produit produit) {
        String prixUnitaireArrondi = "";

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.CANADA);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("0.#####", otherSymbols);
        df.setGroupingUsed(true);
        df.setRoundingMode(RoundingMode.CEILING);

        prixUnitaireArrondi = String.valueOf(formatterPrixString(produit.getPrix() /
                produit.getQuantite() * Produit.MULTIPLICATEUR_PRIX_UNITAIRE) +
                " " + SYMBOLE_MONNAIE_UTILISEE +
                SYMBOLE_SEPARATEUR_MONNAIE_SUR_MULTIPLICATEUR_PRIX_UNITAIRE +
                df.format(Produit.MULTIPLICATEUR_PRIX_UNITAIRE) + " " + produit.getTypeQuantite());

        return prixUnitaireArrondi;
    }

    /**
     * Prend un produit en paramètre et retourne le prix de ce produit selon le format suivant:
     * 1. Le prix du produit à 5 décimales de précision (dont 2 sont toujours affichées)
     * 2. Espace
     * 3. Le symbole de la monnaie utilisée
     *
     * @param produit le produit dont le prix est à convertir en String
     * @return le String de prix qui a été converti selon le frmat expliqué précédemment
     */
    private String getPrixString(Produit produit) {
        String prixString = formatterPrixString(produit.getPrix());

        prixString = String.valueOf(prixString + " " + SYMBOLE_MONNAIE_UTILISEE);
        return prixString;
    }

    /**
     * Formatte un prix reçu par paramètre
     *
     * @param prix le prox reçu
     * @return le prix formatté
     */
    private String formatterPrixString(double prix) {
        String prixFormatte = "";

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.CANADA);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator(' ');

        // Arrondit à 5 décimales de précision dont 2 toujours affichées
        DecimalFormat df = new DecimalFormat("0.00###", otherSymbols);
        df.setGroupingUsed(true);
        df.setRoundingMode(RoundingMode.CEILING);

        prixFormatte = df.format(prix);
        return prixFormatte;
    }

    /**
     * Retourne un String de format qualité selon le produit reçu en paramètre
     *
     * @param produit le produit pour faire un String de format qualité
     * @return le String de format qualité
     */
    private String getQualiteString(Produit produit) {
        String qualiteString = "";

        qualiteString = context.getResources().getString(R.string.lbl_quality) + " " +
                produit.getQualite() + SUFFIXE_QUALITE;
        return qualiteString;
    }
}
