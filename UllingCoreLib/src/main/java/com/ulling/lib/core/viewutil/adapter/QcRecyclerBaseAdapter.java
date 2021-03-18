package com.ulling.lib.core.viewutil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.ulling.lib.core.entities.QcBaseItem;
import com.ulling.lib.core.ui.QcBaseLifeActivity;
import com.ulling.lib.core.ui.QcBaseLifeFragment;
import com.ulling.lib.core.utils.QcDiffCallback;
import com.ulling.lib.core.utils.QcLog;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by P100651 on 2017-07-20.
 * <p>
 * https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java
 * <p>
 * <p>
 * 해결 및 최적화가 가능한지 체크 리스트
 * <p>
 * 1. 공통으로 사용할 수 있는 데이터 리스트? 2. 데이터 모델이 필요할까
 */
public abstract class QcRecyclerBaseAdapter<T> extends RecyclerView.Adapter<QcBaseViewHolder> {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_LOAD_FAIL = -999;
    public static final int TYPE_LOAD_PROGRESS = 999;

    public Context qCon;
    public QcBaseLifeFragment qFragment;
    public Fragment fragment;
    public QcBaseLifeActivity qQcBaseLifeActivity;

    public QcRecyclerItemListener qcRecyclerItemListener;
    /**
     * data set
     */
//    public LiveData<List<T>> itemList;
    public List<T> itemList;

    public interface QcRecyclerItemListener<T> {

        //        void onItemClick(View view, int position, T t, String... transName);
        void onItemClick(View view, int position, T t);

        void onItemLongClick(View view, int position, T t);

        void onItemCheck(boolean checked, int position, T t);

        void onDeleteItem(int itemPosition, T t);

        void onReload();
    }
    /**
     * 필수
     * need~ 시작
     */
    /**
     * 1-1.
     * <p>
     * adapter data 초기화
     */
    protected abstract void needInitToOnCreate();

    /**
     * 1-2. 리셋할 데이터 정의
     */
    protected abstract void needResetData();

    /**
     * 2.
     * 뷰모델 설정
     */
//    public abstract void setViewModel(AndroidViewModel viewModel);

//    public boolean isViewModel() {
//        if (viewModel == null) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public AndroidViewModel getViewModel() {
//        return viewModel;
//    }

    /**
     * 2.
     * <p>
     * View Type 결정
     */
    protected abstract int needLayoutIdFromItemViewType(int position);

    /**
     * 3.
     * <p>
     * 포기션에 맞는 아이템 가져오기
     */
//    protected abstract Object needItemFromPosition(int position);
//    protected abstract void needAddData(LiveData<List<Class>> data);

    /**
     * 5.
     * <p>
     * 접근한 View에 이벤트에 따른 동작 설정
     * 버튼 및 기타 UI이벤트 설정
     */
//    protected abstract void needUIEventListener(int viewTypeResId, ViewDataBinding binding);

    /**
     * 4.
     * <p>
     * UI에서 필요한 데이터 바인딩 View객체에 접근하여 데이터 연결한다.
     */
    protected abstract void needUIBinding(QcBaseViewHolder holder, int position, Object object);

    protected abstract void needUIHeaderBinding(QcBaseViewHolder holder, int position,
        Object object);

    protected abstract void needUILoadFailBinding(QcBaseViewHolder holder, int position,
        Object object);

    protected abstract void needUILoadProgressBinding(QcBaseViewHolder holder, int position,
        Object object);

    protected abstract void needUIOtherBinding(QcBaseViewHolder holder, int position,
        Object object);

    /**
     * 6.
     *
     * 리스너 달기
     * @param qcRecyclerItemListener
     */
//    protected abstract void setEventListener(QcRecyclerItemListener qcRecyclerItemListener);
    /**
     * 옵션
     * opt
     *
     * optAnimationResume
     * optAnimationPause
     */
    /**
     * 애니메이션 시작
     */
    protected void optAnimationResume() {
    }

    /**
     * 애니메이션 정지
     */
    protected void optAnimationPause() {
    }

    /**
     * 아이템을 하나씩 추가
     */
    public void add(T item_) {
        if (null == item_) {
            return;
        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        int start = itemList.size();
        this.itemList.add(item_);

        notifyDataSetChanged();
//        notifyItemChanged(itemList.size(), 0);
//        notifyItemRangeChanged(start, itemList.size());
//        notifyItemRangeInserted(start, itemList.size());
    }

    public void add(int index, T item_) {
        if (null == item_ || index < 0) {
            return;
        }
        QcLog.e("add == ");
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        int start = itemList.size();
        this.itemList.add(index, item_);

        notifyDataSetChanged();
//        notifyItemChanged(itemList.size(), 0);
//        notifyItemRangeChanged(start, itemList.size());
//        notifyItemRangeInserted(start, itemList.size());
    }

    /**
     * 아이템 전체를 추가 기존 아이템 삭제됨 TYPE_DEFAULT으로 설정
     */
    public void addAll(List<T> itemList_) {
//        if (null == itemList_ || itemList_.isEmpty()) {
//            return;
//        }

//        for (T item : itemList) {
//            if (item instanceof QcBaseItem) {
//                QcBaseItem mQcBaseItem = (QcBaseItem) item;
//                mQcBaseItem.setType(QcRecyclerBaseAdapter.TYPE_DEFAULT);
//            }
//        }

        QcLog.i("add == " + itemList_.size());
        this.itemList.clear();
        this.itemList.addAll(itemList_);

        notifyDataSetChanged();
    }

    /**
     * 기존 리스트에 아이템 리스트 추가
     */
    public void addList(List<T> itemList_) {
        if (null == itemList_ || itemList_.isEmpty()) {
            return;
        }
//        for (T item : itemList) {
//            if (item instanceof QcBaseItem) {
//                QcBaseItem mQcBaseItem = (QcBaseItem) item;
//                mQcBaseItem.setType(QcRecyclerBaseAdapter.TYPE_DEFAULT);
//            }
//        }
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
        this.itemList.addAll(itemList_);

        notifyDataSetChanged();
    }


    public void addListDiffResult(final List<T> mNewItemList) {
        addListDiffResult(mNewItemList, null);
    }

    public void addListDiffResult(final List<T> mNewItemList, DiffUtil.Callback mCallback) {
        if (itemList == null) {
//            this.itemList = mNewItemList;
            this.itemList = new ArrayList<T>();
            this.itemList.clear();
            this.itemList.addAll(mNewItemList);
            notifyItemRangeInserted(0, itemList.size());
        } else {
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return itemList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return itemList_.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
////                    return itemList.get(oldItemPosition).getAnswerId() ==
////                            itemList_.get(newItemPosition).getAnswerId();
//                    return itemList.get(oldItemPosition).equals(
//                            itemList_.get(newItemPosition));
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    T newItem = itemList_.get(newItemPosition);
//                    T oldItem = itemList.get(oldItemPosition);
//
//                    return oldItem.equals(newItem);
//                }
//
//
//                @Nullable
//                @Override
//                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//                    return super.getChangePayload(oldItemPosition, newItemPosition);
//                }
//            });

            if (mCallback != null) {
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mCallback);
                this.itemList.clear();
                this.itemList.addAll(mNewItemList);
                diffResult.dispatchUpdatesTo(this);
            } else {
                final QcDiffCallback diffCallback = new QcDiffCallback(this.itemList, mNewItemList);
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
                this.itemList.clear();
                this.itemList.addAll(mNewItemList);
                diffResult.dispatchUpdatesTo(this);
            }

//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    diffResult.dispatchUpdatesTo(this);
//                }
//            });
        }
    }

    public void remove(T item_) {
        if (null == item_) {
            return;
        }
        if (this.itemList != null) {
            QcLog.e("remove == ");
            this.itemList.remove(item_);
            notifyItemChanged(itemList.size(), 0);
        }
    }

    public void addProgress(T item_) {
        QcLog.e("addProgress =====");
        if (getItemCount() == 0) {
            return;
        }
        if (!removeProgress()) {
            return;
        }
        QcLog.e("addProgress =====");
        add(item_);
    }

    public boolean removeProgress() {
        if (getItemCount() == 0) {
            return false;
        }

        QcLog.e("removeProgress == " + itemList.size());
        for (int i = itemList.size() - 1; i >= 0; i--) {
            if (itemList.get(i) instanceof QcBaseItem) {
                return false;
            }

            QcBaseItem mQcBaseItem = (QcBaseItem) itemList.get(i);
            if (mQcBaseItem.getType() == TYPE_LOAD_PROGRESS) {
                itemList.remove(i);
            }
        }
        return true;
    }

    public boolean isProgress() {
        if (getItemCount() == 0) {
            return false;
        }

        for (int i = itemList.size() - 1; i >= 0; i--) {
            if (itemList.get(i) instanceof QcBaseItem) {
                return false;
            }
            QcBaseItem mQcBaseItem = (QcBaseItem) itemList.get(i);
            if (mQcBaseItem.getType() == TYPE_LOAD_PROGRESS) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param item_
     */
    public void addLoadFail(T item_) {
        if (getItemCount() == 0) {
            return;
        }
        if (!removeLoadFail()) {
            return;
        }
        QcLog.e("addLoadFail =====");
        add(item_);
    }

    public boolean removeLoadFail() {
        if (getItemCount() == 0) {
            return false;
        }

        QcLog.e("removeLoadFail == ");

        for (int i = itemList.size() - 1; i >= 0; i--) {
            if (itemList.get(i) instanceof QcBaseItem) {
                return false;
            }
            QcBaseItem mQcBaseItem = (QcBaseItem) itemList.get(i);
            if (mQcBaseItem.getType() == TYPE_LOAD_FAIL) {
                itemList.remove(i);
            }
        }
        return true;
    }

    public boolean isLoadFail() {
        if (getItemCount() == 0) {
            return false;
        }

        for (int i = itemList.size() - 1; i >= 0; i--) {
            if (itemList.get(i) instanceof QcBaseItem) {
                return false;
            }
            QcBaseItem mQcBaseItem = (QcBaseItem) itemList.get(i);
            if (mQcBaseItem.getType() == TYPE_LOAD_FAIL) {
                return true;
            }
        }
        return false;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public QcRecyclerBaseAdapter(QcBaseLifeActivity qQcBaseLifeActivity) {
        super();
        this.qQcBaseLifeActivity = qQcBaseLifeActivity;
        this.qCon = qQcBaseLifeActivity.getBaseContext();
        needInitToOnCreate();
        needResetData();
    }

    public QcRecyclerBaseAdapter(QcBaseLifeActivity qQcBaseLifeActivity,
        QcRecyclerItemListener qcRecyclerItemListener) {
        super();
        this.qQcBaseLifeActivity = qQcBaseLifeActivity;
        this.qCon = qQcBaseLifeActivity.getBaseContext();
        this.qcRecyclerItemListener = qcRecyclerItemListener;
        needInitToOnCreate();
        needResetData();
    }

    public QcRecyclerBaseAdapter(Fragment fragment) {
        super();
        this.fragment = fragment;
        this.qCon = fragment.getContext();
        needInitToOnCreate();
        needResetData();
    }

    /**
     * 아답터 시작
     */
    public QcRecyclerBaseAdapter(QcBaseLifeFragment qFragment) {
        super();
        this.qFragment = qFragment;
        this.qCon = qFragment.getContext();
        needInitToOnCreate();
        needResetData();
    }

    public QcRecyclerBaseAdapter(QcBaseLifeFragment qFragment,
        QcRecyclerItemListener qcRecyclerItemListener) {
        super();
        this.qFragment = qFragment;
        this.qCon = qFragment.getContext();
        this.qcRecyclerItemListener = qcRecyclerItemListener;
        needInitToOnCreate();
        needResetData();
    }

    /**
     * 1. 뷰모델 가져오기 2. 이벤트 리스너 달기
     */
    @Override
    public QcBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewTypeResId) {
//        QcLog.i("onCreateViewHolder == ");

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil
            .inflate(layoutInflater, viewTypeResId, viewGroup, false);

//        needUIEventListener(viewTypeResId, binding);
        return new QcBaseViewHolder(binding);
    }

    public Object needItemFromPosition(int position) {
        if (itemList != null && itemList.size() >= position) {
            return itemList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(QcBaseViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        if (position < 0) {
            return;
        }
        Object object = needItemFromPosition(position);
        if (object == null) {
            return;
        }
        if (object instanceof QcBaseItem) {
            QcBaseItem item = (QcBaseItem) object;
            if (item.getType() == TYPE_LOAD_FAIL) {
//                QcLog.i("TYPE_LOAD_FAIL =====" + position);
                needUILoadFailBinding(holder, position, object);

            } else if (item.getType() == TYPE_LOAD_PROGRESS) {
//                QcLog.i("TYPE_LOAD_PROGRESS =====" + position);
                needUILoadProgressBinding(holder, position, object);

            } else if (item.getType() == TYPE_HEADER) {
//                QcLog.i("TYPE_HEADER =====" + position);
                needUIHeaderBinding(holder, position, object);

            } else if (item.getType() == TYPE_DEFAULT) {
//                QcLog.i("TYPE_DEFAULT =====" + position);
                needUIBinding(holder, position, object);

            } else {
//                QcLog.i("TYPE_OTHER =====" + position);
                needUIOtherBinding(holder, position, object);
            }
        } else {
            needUIBinding(holder, position, object);
        }
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return needLayoutIdFromItemViewType(position);
    }
}