package com.ics.hunar.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.hunar.Constant;
import com.ics.hunar.R;
import com.ics.hunar.activity.SubcategoryActivity;
import com.ics.hunar.activity.VideoPlayActivity;
import com.ics.hunar.helper.Session;
import com.ics.hunar.helper.SharedPreferencesUtil;
import com.ics.hunar.helper.Utils;
import com.ics.hunar.model.features.Category;
import com.ics.hunar.model.features.Featured_Courses_Data;
import com.ics.hunar.model.features.Subcategory;

import java.util.List;

public class Categorized_FeaturesAdapter extends RecyclerView.Adapter<Categorized_FeaturesAdapter.NormalViewHolder> {
    private Context context;
    private List<Featured_Courses_Data> categoryList, categoryList1;

    public Categorized_FeaturesAdapter(Context context, List<Featured_Courses_Data> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public NormalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.features_normal_item_layout, parent, false);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NormalViewHolder holder, int position) {

            Featured_Courses_Data category = categoryList.get(position);

            holder.tvFeatureItemName.setText(category.getSection_name());
            Utils.loadImage(holder.ivFeatureItem, category.getImage(), Utils.getCircularProgressDrawable(context, 5, 15));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   Constant.CATE_ID = Integer.parseInt(category.getId());
                    Constant.cate_name = category.getSection_name();
                    Constant.cat_img_url = category.getImage();
                    Intent intent = new Intent(context, SubcategoryActivity.class);
                    context.startActivity(intent);*/

                    Constant.cate_name = category.getSubcategory_name();
                    Constant.SUB_CAT_ID = Integer.parseInt(category.getId());
                    SharedPreferencesUtil.write(SharedPreferencesUtil.SUB_CATEGORY_NAME, category.getSubcategory_name());
                    Session.setSubCategoryName(Session.SUB_CAT_NAME, category.getSubcategory_name(), context);

                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    //intent.putExtra("fromQue", fromQue);
                    context.startActivity(intent);
                }

            });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        //        private RecyclerView rvFeaturesNormal;
        private TextView tvFeatureItemName;
        private ImageView ivFeatureItem;
        private ConstraintLayout clItemParent;
        private CardView cvItemParent;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeatureItemName = itemView.findViewById(R.id.tvFeatureItemName);
            ivFeatureItem = itemView.findViewById(R.id.ivFeatureItem);
            clItemParent = itemView.findViewById(R.id.clItemParent);
            cvItemParent = itemView.findViewById(R.id.cvItemParent);
        }
    }

}
