package ca.qc.bdeb.p55.smartshopplus.modele;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        public TextView getTxtNom() {
            return txtNom;
        }

        public void setTxtNom(TextView txtNom) {
            this.txtNom = txtNom;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        Produit rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.relative_layout_liste_magasins, null);
            holder = new ProduitHolder();
            holder.txtNom = (TextView) convertView.findViewById(R.id.relative_layout_tvw_nom_magasin);


            convertView.setTag(holder);
        } else {
            holder = (ProduitHolder) convertView.getTag();
        }

        holder.txtNom.setText(rowItem.getNom());

        return convertView;
    }

    public ProduitHolder getHolder() {
        return holder;
    }

}
