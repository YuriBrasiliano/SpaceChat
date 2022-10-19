package com.example.spacechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProdutosAdapter extends RecyclerView.Adapter<ProdutosAdapter.ProdutosHolder> {

    private ArrayList<Produto> produtos;
    private Context context;
    private ProdutosAdapter.OnProdutosClickListener onProdutosClickListener;

    public ProdutosAdapter(ArrayList<Produto> produtos, Context context, ProdutosAdapter.OnProdutosClickListener onProdutosClickListener) {
        this.produtos = produtos;
        this.context = context;
        this.onProdutosClickListener = onProdutosClickListener;
    }
    interface OnProdutosClickListener{
        void onProdutosClicked(int position);
    }

    @NonNull
    @Override
    public ProdutosAdapter.ProdutosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produto_holder,parent,false);
        return new ProdutosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosAdapter.ProdutosHolder holder, int position) {
        holder.txtNomeProduto.setText(produtos.get(position).getNomeProduto());
        holder.txtPrecoProduto.setText(produtos.get(position).getPrecoProduto());
        Glide.with(context).load(produtos.get(position).getImgProduto()).error(R.drawable.ic_insert_photo).placeholder(R.drawable.ic_insert_photo).into(holder.imageView);

    }

    @Override
    public int getItemCount() {return produtos.size();}

    public class ProdutosHolder extends RecyclerView.ViewHolder {
        TextView txtNomeProduto, txtPrecoProduto;
        ImageView imageView;

        public ProdutosHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProdutosClickListener.onProdutosClicked(getAdapterPosition());
                }
            });
            txtNomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            txtPrecoProduto = itemView.findViewById(R.id.txtPrecoProduto);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
