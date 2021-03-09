package com.ulling.lib.core.utils;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class QcDiffCallback<T> extends DiffUtil.Callback {

    private List<T> mOldItemList;
    private List<T> mNewItemList;

    public QcDiffCallback(List<T> mOldItemList, List<T> mNewItemList) {
        this.mOldItemList = mOldItemList;
        this.mNewItemList = mNewItemList;
    }

    // 이전 목록의 개수를 반환합니다.
    @Override
    public int getOldListSize() {
        return mOldItemList != null ? mOldItemList.size() : 0;
    }

    // 새로운 목록의 개수를 반환합니다.
    @Override
    public int getNewListSize() {
        return mNewItemList != null ? mNewItemList.size() : 0;
    }

    // 두 객체가 같은 항목인지 여부를 결정합니다.
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItemList.get(oldItemPosition).equals(
                mNewItemList.get(newItemPosition));
    }

    // 두 항목의 데이터가 같은지 여부를 결정합니다. areItemsTheSame()이 true를 반환하는 경우에만 호출됩니다.
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T newItem = mNewItemList.get(newItemPosition);
        T oldItem = mOldItemList.get(oldItemPosition);

        return oldItem.equals(newItem);
    }

    // 만약 areItemTheSame()이 true를 반환하고
    // areContentsTheSame()이 false를 반환하면 이 메서드가 호출되어 변경 내용에 대한 페이로드를 가져옵니다.
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//        Product newProduct = mNewList.get(newItemPosition);
//        Product oldProduct = mOldList.get(oldItemPosition);
//        Bundle diffBundle = new Bundle();
//        if (newProduct.hasDiscount() != oldProduct.hasDiscount()) {
//            diffBundle.putBoolean(KEY_DISCOUNT, newProduct.hasDiscount());
//        }
//        if (newProduct.getReviews().size() != oldProduct.getReviews().size()) {
//            diffBundle.putInt(Product.KEY_REVIEWS_COUNT, newProduct.getReviews().size());
//        }
//        if (newProduct.getPrice() != oldProduct.getPrice()) {
//            diffBundle.putFloat(Product.KEY_PRICE, newProduct.getPrice());
//        }
//        if (diffBundle.size() == 0) return null;
//        return diffBundle;
//    }

//    @Override
//    public void onBindViewHolder(ProductViewHolder holder, int position, List<Object> payloads) {
//        if(payloads.isEmpty()) return;
//        else{
//            Bundle o = (Bundle) payloads.get(0);
//            for (String key : o.keySet()) {
//                if(key.equals(KEY_DISCOUNT)){
//                    //TODO lets update blink discount textView :)
//                }else if(key.equals(KEY_PRICE)){
//                    //TODO lets update and change price color for some time
//                }else if(key.equals(KEY_REVIEWS_COUNT)){
//                    //TODO just update the review count textview
//                }
//            }
//        }
//    }
}
