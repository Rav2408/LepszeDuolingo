package pl.edu.pb.lepszeduolingo.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;

public class AdminCategories_RecyclerViewAdapter extends RecyclerView.Adapter<AdminCategories_RecyclerViewAdapter.ViewHolder>{
    private AdminCategories_RecyclerViewAdapter.onDataListener onDataListener;
    ArrayList<String> categoriesData;
    public AdminCategories_RecyclerViewAdapter(AdminCategories_RecyclerViewAdapter.onDataListener onDataListener, ArrayList<String> categoriesData){
        this.onDataListener = onDataListener;
        this.categoriesData = categoriesData;
    }
    @NonNull
    @Override
    public AdminCategories_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.admincategories_recycler_view_row, parent, false);
        return new AdminCategories_RecyclerViewAdapter.ViewHolder(view, onDataListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCategories_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(categoriesData.get(position));
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AdminCategories_RecyclerViewAdapter.onDataListener onDataListener;
        TextView textView;
        ImageButton button;

        public ViewHolder(@NonNull View itemView, AdminCategories_RecyclerViewAdapter.onDataListener onDataListener) {
            super(itemView);
            this.onDataListener = onDataListener;
            textView = itemView.findViewById(R.id.adminCategoryText);
            button = itemView.findViewById(R.id.deleteadminCategoryBtn);

            textView.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteadminCategoryBtn:
                    onDataListener.onCategoryDelete(getAdapterPosition());
                    break;
                case R.id.adminCategoryText:
                    onDataListener.onCategoryClick(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public interface onDataListener{
        void onCategoryClick(int position);
        void onCategoryDelete(int position);
    }
}
