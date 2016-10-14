package ca.qc.bdeb.p55.smartshopplus.modele;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.qc.bdeb.p55.smartshopplus.R;

/**
 * Created by C A T A on 2016-10-12.
 */

public class ArrayAdapterProduits extends ArrayAdapter<Produit> {

    Context context;

    private ProduitHolder holder;

    public ArrayAdapterProduits(Context context, int ressourceId, List<Produit> items) {
        super(context, ressourceId, items);
        this.context = context;
    }


    public class ProduitHolder {
        TextView txtNom;
        TextView txtPrix;
        TextView txtPrixUnitaire;
        ImageView ivwImageProduit;


        public TextView getTxtNom() {
            return txtNom;
        }

        public void setTxtNom(TextView txtNom) {
            this.txtNom = txtNom;
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
        holder.txtPrix.setText(String.valueOf((rowItem.getPrix())));
        holder.txtPrixUnitaire.setText(String.valueOf(rowItem.getPrix() / rowItem.getQuantite() * Produit.MULTIPLICATEUR_PRIX_UNITAIRE));
        holder.ivwImageProduit.setImageBitmap(rowItem.getImage());

        return convertView;
    }

    public ProduitHolder getHolder() {
        return holder;
    }
}
