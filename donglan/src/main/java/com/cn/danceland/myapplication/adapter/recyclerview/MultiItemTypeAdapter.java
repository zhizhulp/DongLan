package com.cn.danceland.myapplication.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cn.danceland.myapplication.adapter.recyclerview.base.ItemViewDelegate;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ItemViewDelegateManager;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.adapter.recyclerview.utils.WrapperUtils;

import java.util.List;

/**
 * Created by shy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private View mEmptyView;
    private int mEmptyLayoutId;

    public void setEmptyView(View emptyView)
    {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId)
    {
        mEmptyLayoutId = layoutId;
    }

    private boolean isEmpty()//判断是否有布局
    {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mDatas.size() == 0;
    }


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public void setDatas(List<T> mDatas){
        this.mDatas=mDatas;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void addDatas(List<T> mDatas){
        this.mDatas.addAll( mDatas);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (isEmpty())//是否没有数据
        {
            ViewHolder holder;
            if (mEmptyView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }




        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {


        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();


            final WrapperUtils.SpanSizeCallback callback= new WrapperUtils.SpanSizeCallback() {
                @Override
                public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                    if (isEmpty())
                    {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (oldLookup != null)
                    {
                        return oldLookup.getSpanSize(position);
                    }
                    return 1;
                }
            };

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }

    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isEmpty())
        {
            WrapperUtils.setFullSpan(holder);
        }
    }



    @Override
    public int getItemViewType(int position)
    {
        if (isEmpty())
        {
            return ITEM_TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }





    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isEmpty())
        {
            return;
        }
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) return 1;
        int itemCount = mDatas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
