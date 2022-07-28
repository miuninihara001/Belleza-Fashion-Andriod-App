package com.example.bellezafashion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Product;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private static final String TAG = "test.belleza.recyclerview.ProductAdapter";
    private Context context;
    private ArrayList<Product> list;

    public ProductAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list,parent,false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);

        return productViewHolder;
    }
    @SuppressLint("LongLogTag")

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder,final int position) {
        Log.d(TAG,"OnBindViewHolder: Called");

        Product pro = list.get(position);
//        holder.in_txt_pid.setText(pro.getPid());
        holder.in_txt_name.setText(pro.getName());
        holder.in_txt_price.setText(pro.getPrice());
        holder.in_txt_designer.setText(pro.getDesigner());
        holder.in_txt_description.setText(pro.getDescription());
        Picasso.get().load(pro.getImage()).into(holder.in_txt_image);

        holder.cardView_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductDetail.class);

                intent.putExtra("pid",pro.getPid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                Toast.makeText(context, "show the details of item", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //ViewHolder Class

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView in_txt_name, in_txt_price, in_txt_designer, in_txt_description;
        ImageView in_txt_image;
        CardView cardView_list;


        public ProductViewHolder(@NonNull  View itemView) {
            super(itemView);

            cardView_list=itemView.findViewById(R.id.in_cardView_List);
//            in_txt_pid = itemView.findViewById(R.id.in_product_pid);
            in_txt_name =(TextView) itemView.findViewById(R.id.in_product_name);
            in_txt_image=(ImageView) itemView.findViewById(R.id.in_product_image);
            in_txt_price=(TextView) itemView.findViewById(R.id.in_product_price);
            in_txt_designer=(TextView) itemView.findViewById(R.id.in_product_designer);
            in_txt_description=(TextView) itemView.findViewById(R.id.in_product_description);

        }
    }
}