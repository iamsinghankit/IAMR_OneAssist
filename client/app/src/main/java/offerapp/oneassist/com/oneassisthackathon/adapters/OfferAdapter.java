package offerapp.oneassist.com.oneassisthackathon.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import offerapp.oneassist.com.oneassisthackathon.R;
import offerapp.oneassist.com.oneassisthackathon.models.OffersVo;
import offerapp.oneassist.com.oneassisthackathon.offers.OfferDetailActivity;

/**
 * Created by imran.khan on 09/12/17.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {
    private ArrayList<OffersVo> offerList;

    public OfferAdapter(ArrayList<OffersVo> offerList) {
        this.offerList = offerList;
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_offer_list_layout, viewGroup, false);
        return new OfferViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, int position) {
        OffersVo offersVo=offerList.get(position);
        holder.textview_title_1.setText(offersVo.getRestaurantName());
        holder.textview_title_2.setText(offersVo.getActualOffer());
        holder.textview_valid_till.setText(offersVo.getValidity());
        holder.card_view.setTag(position);
        Picasso.with(holder.textview_title_1.getContext())
                .load(offersVo.getUrl())
                .fit()
                .into(holder.img_merchant_image);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {

        protected ImageView img_merchant_image;
        protected TextView textview_title_1;
        protected TextView textview_title_2;
        protected TextView textview_title_3;
        protected TextView textview_valid_till;
        protected CardView card_view;

        public OfferViewHolder(final View v) {
            super(v);
            v.setClickable(true);
            card_view = (CardView) v.findViewById(R.id.card_view);
            img_merchant_image = (ImageView) v.findViewById(R.id.img_merchant_image);
            textview_title_1 = (TextView) v.findViewById(R.id.textview_title_1);
            textview_title_2 = (TextView) v.findViewById(R.id.textview_title_2);
            textview_title_3 = (TextView) v.findViewById(R.id.textview_title_3);
            textview_valid_till = (TextView) v.findViewById(R.id.textview_valid_till);
            card_view.setClickable(true);
            card_view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(view.getContext(), OfferDetailActivity.class);
                    intent.putExtra("selected_pos",Integer.parseInt(view.getTag().toString()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
