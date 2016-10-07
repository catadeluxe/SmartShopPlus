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
 * Created by Catalin on 2016-09-14.
 */
public class ArrayAdapterMagasins extends ArrayAdapter<Magasin> {

    Context context;

    public ArrayAdapterMagasins(Context context, int ressourceId, List<Magasin> items) {
        super(context, ressourceId, items);
        this.context = context;
    }


    public class ClientHolder {
        TextView txtNom;
        TextView txtAge;
        TextView txtVille;

        public TextView getTxtNom() {
            return txtNom;
        }

        public void setTxtNom(TextView txtNom) {
            this.txtNom = txtNom;
        }

        public TextView getTxtAge() {
            return txtAge;
        }

        public void setTxtAge(TextView txtAge) {
            this.txtAge = txtAge;
        }

        public TextView getTxtVille() {
            return txtVille;
        }

        public void setTxtVille(TextView txtVille) {
            this.txtVille = txtVille;
        }
    }

    private ClientHolder holder;

    public ClientHolder getHolder() {
        return holder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        Magasin rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.relative_layout, null);
            holder = new ClientHolder();
          //  holder.txtNom = (TextView) convertView.findViewById(R.id.relative_layout_txt_nom);
        //    holder.txtAge = (TextView) convertView.findViewById(R.id.relative_layout_txt_age);
         //   holder.txtVille = (TextView) convertView.findViewById(R.id.relative_layout_txt_ville);
            convertView.setTag(holder);
        } else {
            holder = (ClientHolder) convertView.getTag();
        }

        holder.txtNom.setText(rowItem.getNom());
        holder.txtAge.setText(Integer.toString(rowItem.getAge()));
        holder.txtVille.setText(rowItem.getVille());

        return convertView;
    }

}
