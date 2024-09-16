package com.example.imgfilter;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private final Bitmap originalBitmap;
    private final FilterClickListener filterClickListener;
    private final String[] filterNames;

    public interface FilterClickListener {
        void onFilterClick(Bitmap filteredBitmap);
    }

    public FilterAdapter(Bitmap originalBitmap, FilterClickListener filterClickListener) {
        this.originalBitmap = originalBitmap;
        this.filterClickListener = filterClickListener;
        this.filterNames = new String[]{
                "No Filter", "Black & White", "Fair", "Tranquil", "Cyberpunk", "Blues",
                "Fall", "Scent", "Blue Ice", "Black & Gold", "Cool",
                "Grapefruit", "Mint", "1970", "Fashion", "British",
                "LOMO", "Sketch", "Chapin"
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap filteredBitmap = applyFilter(position);
        holder.filterImageView.setImageBitmap(filteredBitmap);
        holder.filterName.setText(filterNames[position]);
        holder.itemView.setOnClickListener(v -> filterClickListener.onFilterClick(filteredBitmap));
    }

    @Override
    public int getItemCount() {
        return filterNames.length; // Number of filters
    }

    private Bitmap applyFilter(int position) {
        Bitmap filteredBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        Paint paint = new Paint();
        switch (position) {
            case 0: // No Filter
                return originalBitmap;
            case 1: // Black & White
                colorMatrix.setSaturation(0);
                break;
            case 2: // Fair
                colorMatrix.setScale(1.1f, 1.0f, 0.9f, 1.0f);
                break;
            case 3: // Tranquil
                colorMatrix.setScale(0.8f, 0.9f, 1.0f, 1.0f);
                break;
            case 4: // Cyberpunk
                colorMatrix.setScale(1.2f, 0.8f, 0.8f, 1.0f);
                break;
            case 5: // Blues
                colorMatrix.setScale(0.7f, 0.7f, 1.2f, 1.0f);
                break;
            case 6: // Fall
                colorMatrix.setScale(1.2f, 1.0f, 0.9f, 1.0f);
                break;
            case 7: // Scent
                colorMatrix.setScale(1.1f, 1.0f, 0.9f, 1.0f);
                break;
            case 8: // Blue Ice
                colorMatrix.setScale(0.9f, 1.1f, 1.1f, 1.0f);
                break;
            case 9: // Black & Gold
                colorMatrix.setScale(1.2f, 1.1f, 0.8f, 1.0f);
                break;
            case 10: // Cool
                colorMatrix.setScale(0.8f, 1.0f, 1.0f, 1.0f);
                break;
            case 11: // Grapefruit
                colorMatrix.setScale(1.0f, 0.9f, 0.9f, 1.0f);
                break;
            case 12: // Mint
                colorMatrix.setScale(0.9f, 1.0f, 1.1f, 1.0f);
                break;
            case 13: // 1970
                colorMatrix.setScale(1.2f, 1.1f, 0.8f, 1.0f);
                break;
            case 14: // Fashion
                colorMatrix.setScale(1.1f, 1.1f, 1.0f, 1.0f);
                break;
            case 15: // British
                colorMatrix.setScale(1.2f, 1.2f, 0.8f, 1.0f);
                break;
            case 16: // LOMO
                colorMatrix.setScale(1.3f, 1.3f, 0.8f, 1.0f);
                break;
            case 17: // Sketch
                colorMatrix.setSaturation(0);
                break;
            case 18: // Chapin
                colorMatrix.setScale(1.0f, 1.0f, 1.1f, 1.0f);
                break;
        }
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        android.graphics.Canvas canvas = new android.graphics.Canvas(filteredBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, paint);
        return filteredBitmap;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView filterImageView;
        TextView filterName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filterImageView = itemView.findViewById(R.id.filterImageView);
            filterName = itemView.findViewById(R.id.filterName);
        }
    }
}
