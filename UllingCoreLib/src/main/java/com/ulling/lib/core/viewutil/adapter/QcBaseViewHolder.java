package com.ulling.lib.core.viewutil.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 19.
 * @description :
 * @since :
 */
public class QcBaseViewHolder extends RecyclerView.ViewHolder  {
    private final ViewDataBinding binding;

    public QcBaseViewHolder(View itemView) {
        super(itemView);
        this.binding = DataBindingUtil.bind(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public QcBaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}