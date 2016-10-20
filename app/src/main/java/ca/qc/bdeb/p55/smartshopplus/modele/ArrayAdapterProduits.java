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
        holder.txtPrix.setText(String.valueOf((rowItem.getPrix())) +
                " " + SYMBOLE_MONNAIE_UTILISEE);
        holder.txtPrixUnitaire.setText(getPrixUnitaireString(rowItem));
        holder.ivwImageProduit.setImageBitmap(rowItem.getImage());

        return convertView;
    }

    public ProduitHolder getHolder() {
        return holder;
    }

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
}
