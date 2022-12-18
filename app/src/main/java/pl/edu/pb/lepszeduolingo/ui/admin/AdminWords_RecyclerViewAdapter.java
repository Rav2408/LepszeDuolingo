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

public class AdminWords_RecyclerViewAdapter extends RecyclerView.Adapter<AdminWords_RecyclerViewAdapter.ViewHolder>{
    private AdminWords_RecyclerViewAdapter.onDataListener onDataListener;
    ArrayList<String> wordsData;
    public AdminWords_RecyclerViewAdapter(AdminWords_RecyclerViewAdapter.onDataListener onDataListener, ArrayList<String> wordsData){
        this.onDataListener = onDataListener;
        this.wordsData = wordsData;
    }
    @NonNull
    @Override
    public AdminWords_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adminwords_recycler_view_row, parent, false);
        return new AdminWords_RecyclerViewAdapter.ViewHolder(view, onDataListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminWords_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(wordsData.get(position));
    }

    @Override
    public int getItemCount() {
        return wordsData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AdminWords_RecyclerViewAdapter.onDataListener onDataListener;
        TextView textView;
        ImageButton button;

        public ViewHolder(@NonNull View itemView, AdminWords_RecyclerViewAdapter.onDataListener onDataListener) {
            super(itemView);
            this.onDataListener = onDataListener;
            textView = itemView.findViewById(R.id.adminWordText);
            button = itemView.findViewById(R.id.deleteadminWordBtn);

            textView.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteadminWordBtn:
                    onDataListener.onWordDelete(getAdapterPosition());
                    break;
                case R.id.adminWordText:
                    onDataListener.onWordClick(getAdapterPosition());
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
        void onWordClick(int position);
        void onWordDelete(int position);
    }
}
