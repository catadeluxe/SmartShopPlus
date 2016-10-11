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
 * Created by Catalin on 2016-09-14.
 */
public class ArrayAdapterMagasins extends ArrayAdapter<Magasin> {

    Context context;

    private MagasinHolder holder;

    public ArrayAdapterMagasins(Context context, int ressourceId, List<Magasin> items) {
        super(context, ressourceId, items);
        this.context = context;
    }


    public class MagasinHolder {
        TextView txtNom;
        ImageView img;


        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

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
        Magasin rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.relative_layout, null);
            holder = new MagasinHolder();
            holder.txtNom = (TextView) convertView.findViewById(R.id.relative_layout_tvw_nom_magasin);
            holder.img = (ImageView) convertView.findViewById(R.id.relative_layout_img_magasin);

            convertView.setTag(holder);
        } else {
            holder = (MagasinHolder) convertView.getTag();
        }

        holder.txtNom.setText(rowItem.getNom());
        holder.img.setImageBitmap(rowItem.getImage());

        return convertView;
    }

    public MagasinHolder getHolder() {
        return holder;
    }

}
