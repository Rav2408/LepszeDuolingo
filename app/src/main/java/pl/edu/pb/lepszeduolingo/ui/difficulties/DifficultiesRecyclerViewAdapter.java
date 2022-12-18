package pl.edu.pb.lepszeduolingo.ui.difficulties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.edu.pb.lepszeduolingo.R;

public class DifficultiesRecyclerViewAdapter extends RecyclerView.Adapter<DifficultiesRecyclerViewAdapter.ViewHolder>{
    private List<String> Data;
    private Context context;
    private onDifficultyListener onDifficultyListener;
    DifficultiesRecyclerViewAdapter(Context context, List<String> data, onDifficultyListener onDifficultyListener) {
        this.Data = data;
        this.context = context;
        this.onDifficultyListener = onDifficultyListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.difficulties_recycler_view_row, parent, false);
        return new DifficultiesRecyclerViewAdapter.ViewHolder(view, onDifficultyListener);
    }
    // bind data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String difficulties = Data.get(position);
        holder.myTextView.setText(difficulties);
    }
    @Override
    public int getItemCount() {
        return Data.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        onDifficultyListener onDifficultyListener;
        ViewHolder(View itemView, onDifficultyListener onDifficultyListener) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.adminRowText);
            this.onDifficultyListener = onDifficultyListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDifficultyListener.onDifficultyClick(getAdapterPosition());
        }
    }
    public interface onDifficultyListener{
        void onDifficultyClick(int position);
    }
}
