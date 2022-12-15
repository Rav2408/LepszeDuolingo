package pl.edu.pb.lepszeduolingo.ui.difficulty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pb.lepszeduolingo.R;

public class DifficultyRecyclerViewAdapter extends RecyclerView.Adapter<DifficultyRecyclerViewAdapter.ViewHolder> {
    private onCategoryListener onCategoryListener;
    private List<String> data;
    private Context context;
    DifficultyRecyclerViewAdapter(Context context, List<String> data, DifficultyRecyclerViewAdapter.onCategoryListener onCategoryListener) {
        this.data = data;
        this.context = context;
        this.onCategoryListener = onCategoryListener;
    }
    @Override
    public DifficultyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diff_recycler_view_row, parent, false);
        return new DifficultyRecyclerViewAdapter.ViewHolder(view, onCategoryListener);
    }
    // bind data
    @Override
    public void onBindViewHolder(@NonNull DifficultyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(data.get(position));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        onCategoryListener onCategoryListener;

        public ViewHolder(@NonNull View itemView, onCategoryListener onCategoryListener) {
            super(itemView);
            this.onCategoryListener = onCategoryListener;
            textView = itemView.findViewById(R.id.wordTranslationText);
            itemView.setOnClickListener(this);
        }
        public TextView getTextView() {
            return textView;
        }
        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }
    public interface onCategoryListener{
        void onCategoryClick(int position);
    }
}
