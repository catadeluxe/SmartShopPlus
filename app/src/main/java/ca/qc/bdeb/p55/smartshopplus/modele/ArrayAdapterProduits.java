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
import java.util.List;

import ca.qc.bdeb.p55.smartshopplus.R;

/**
 * Created by C A T A on 2016-10-12.
 */

public class ArrayAdapterProduits extends ArrayAdapter<Produit> {

    final String SYMBOLE_MONNAIE_UTILISEE = "$";
    final String SYMBOLE_SEPARATEUR_MONNAIE_SUR_MULTIPLICATEUR_PRIX_UNITAIRE = "/";

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
            holder.ivwImageProduit = (ImageView)
                    convertView.findViewById(R.id.relative_layout_liste_produits_ivw_image_produit);

            convertView.setTag(holder);
        } else {
            holder = (ProduitHolder) convertView.getTag();
        }

        holder.txtNom.setText(rowItem.getNom());
        holder.txtQuantite.setText(rowItem.getQuantite() + " " + rowItem.getTypeQuantite());
        holder.txtPrix.setText(getPrixString(rowItem));
        holder.txtPrixUnitaire.setText(getPrixUnitaireString(rowItem));
        holder.ivwImageProduit.setImageBitmap(rowItem.getImage());

        return convertView;
    }

    public ProduitHolder getHolder() {
        return holder;
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

        // Arrondit à 5 décimales de précision
        DecimalFormat df = new DecimalFormat("#.#####");
        df.setRoundingMode(RoundingMode.CEILING);

        prixUnitaireArrondi = String.valueOf(df.format(produit.getPrix() / produit.getQuantite()
                * Produit.MULTIPLICATEUR_PRIX_UNITAIRE) + " " + SYMBOLE_MONNAIE_UTILISEE +
                SYMBOLE_SEPARATEUR_MONNAIE_SUR_MULTIPLICATEUR_PRIX_UNITAIRE +
                Produit.MULTIPLICATEUR_PRIX_UNITAIRE + " " + produit.getTypeQuantite());

        return prixUnitaireArrondi;
    }

    /**
     * Prend un produit en paramètre et retourne le prix de ce produit selon le format suivant:
     * 1. Le prix du produit à 5 décimales de précision
     * 2. Espace
     * 3. Le symbole de la monnaie utilisée
     *
     * @param produit le produit dont le prix est à convertir en String
     * @return le String de prix qui a été converti selon le frmat expliqué précédemment
     */
    private String getPrixString(Produit produit) {
        String prixString = "";

        DecimalFormat df;
        if (produit.getPrix() % 1.0D != 0) {
            // Arrondit à 5 décimales de précision
            df = new DecimalFormat("0.00###");
            df.setRoundingMode(RoundingMode.CEILING);
            prixString = df.format(produit.getPrix());
        } else {
            df = new DecimalFormat("#.00");
            df.setRoundingMode(RoundingMode.CEILING);
            prixString = df.format(produit.getPrix());
        }

        prixString = String.valueOf(prixString + " " + SYMBOLE_MONNAIE_UTILISEE);
        return prixString;
    }
}
