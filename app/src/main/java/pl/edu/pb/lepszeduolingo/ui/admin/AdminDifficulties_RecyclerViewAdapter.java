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

public class AdminDifficulties_RecyclerViewAdapter extends RecyclerView.Adapter<AdminDifficulties_RecyclerViewAdapter.ViewHolder>{
    private AdminDifficulties_RecyclerViewAdapter.onDataListener onDataListener;
    ArrayList<String> difficultiesData;
    public AdminDifficulties_RecyclerViewAdapter(AdminDifficulties_RecyclerViewAdapter.onDataListener onDataListener, ArrayList<String> difficultiesData){
        this.onDataListener = onDataListener;
        this.difficultiesData = difficultiesData;
    }
    @NonNull
    @Override
    public AdminDifficulties_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.admindifficulties_recycler_view_row, parent, false);
        return new AdminDifficulties_RecyclerViewAdapter.ViewHolder(view, onDataListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDifficulties_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(difficultiesData.get(position));
    }

    @Override
    public int getItemCount() {
        return difficultiesData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AdminDifficulties_RecyclerViewAdapter.onDataListener onDataListener;
        TextView textView;
        ImageButton button;

        public ViewHolder(@NonNull View itemView, AdminDifficulties_RecyclerViewAdapter.onDataListener onDataListener) {
            super(itemView);
            this.onDataListener = onDataListener;
            textView = itemView.findViewById(R.id.adminDifficultyText);
            button = itemView.findViewById(R.id.deleteadminDifficultyBtn);

            textView.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteadminDifficultyBtn:
                    onDataListener.onDifficultyDelete(getAdapterPosition());
                    break;
                case R.id.adminDifficultyText:
                    onDataListener.onDifficultyClick(getAdapterPosition());
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
        void onDifficultyClick(int position);
        void onDifficultyDelete(int position);
    }
}
