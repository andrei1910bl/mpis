package com.example.lab4.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4.R;
import com.example.lab4.model.Element;
import com.example.lab4.ui.DetailActivity;
import java.util.List;

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ElementViewHolder> {
    private List<Element> elements;
    private Context context; // Добавляем контекст

    public ElementAdapter(List<Element> elements, Context context) {
        this.elements = elements;
        this.context = context;
    }

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_element, parent, false);
        return new ElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        Element element = elements.get(position);

        holder.nameText.setText(element.getName());
        holder.usernameText.setText("Username: " + element.getUsername());
        holder.emailText.setText("Email: " + element.getEmail());
        holder.phoneText.setText("Phone: " + element.getPhone());

        // Добавляем обработчик клика
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", element.getName());
                intent.putExtra("username", element.getUsername());
                intent.putExtra("email", element.getEmail());
                intent.putExtra("phone", element.getPhone());
                context.startActivity(intent); // Запускаем новую активность
            }
        });
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public static class ElementViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView usernameText;
        TextView emailText;
        TextView phoneText;

        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            usernameText = itemView.findViewById(R.id.usernameText);
            emailText = itemView.findViewById(R.id.emailText);
            phoneText = itemView.findViewById(R.id.phoneText);
        }
    }
}
