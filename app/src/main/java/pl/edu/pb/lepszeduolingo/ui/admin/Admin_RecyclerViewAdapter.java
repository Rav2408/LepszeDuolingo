package pl.edu.pb.lepszeduolingo.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import pl.edu.pb.lepszeduolingo.R;

public class Admin_RecyclerViewAdapter extends RecyclerView.Adapter<Admin_RecyclerViewAdapter.ViewHolder>{
    private Admin_RecyclerViewAdapter.onDataListener onDataListener;
    ArrayList<String> data = new ArrayList<String>();
    public Admin_RecyclerViewAdapter(Admin_RecyclerViewAdapter.onDataListener onDataListener){
        this.onDataListener = onDataListener;
        data.add("Word");       //TODO w słowie będzie dodawanie tłumaczeń i pytań
        data.add("Category");
        data.add("Difficulty");
        data.add("Language");
    }
    @NonNull
    @Override
    public Admin_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.admin_recycler_view_row, parent, false);
        return new Admin_RecyclerViewAdapter.ViewHolder(view, onDataListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Admin_RecyclerViewAdapter.onDataListener onDataListener;
        TextView textView;

        public ViewHolder(@NonNull View itemView, Admin_RecyclerViewAdapter.onDataListener onDataListener) {
            super(itemView);
            this.onDataListener = onDataListener;
            textView = itemView.findViewById(R.id.adminRowText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDataListener.onDataClick(getAdapterPosition());
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public interface onDataListener{
        void onDataClick(int position);
    }
}
