package com.example.zlp.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zlp.R;


public class LqcRefreshLoadView extends LinearLayout {
	// refresh states
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	// pull state
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;
	
	private int mLastMotionY;
	private View mHeaderView;
	private View mFooterView;
	private int mHeaderViewHeight;
	private int mFooterViewHeight;
	private ImageView mHeaderImageView;
	private ImageView mFooterImageView;
	private TextView mHeaderTextView;
	private TextView mFooterTextView;
	private ImageView mHeaderProgressBar;
	private ImageView mFooterProgressBar;

	private AdapterView<?> mAdapterView;
	private ScrollView mScrollView;
    private RecyclerView mRecyclerView;
	private int mHeaderState;
	private int mFooterState;
	private int mPullState;
	private boolean headEnable=true;
	private boolean footEnable=true;
	//footer refresh listener
	private OnRefreshLoadListener mFreshListener;

	public LqcRefreshLoadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LqcRefreshLoadView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setOrientation(LinearLayout.VERTICAL);
		// header view 在此添加,保证是第一个添加到linearlayout的最上端
		addHeaderView();
	}

	private void addHeaderView() {
		// header view
		mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.lqc_refresh_load_header, this, false);

		mHeaderImageView = (ImageView) mHeaderView.findViewById(R.id.lqc_refresh_image);
		mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.lqc_refresh_text);
		mHeaderProgressBar = (ImageView) mHeaderView.findViewById(R.id.lqc_refresh_progress);
		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,mHeaderViewHeight);
		// 设置topMargin的值为负的header View高度,即将其隐藏在最上方
		params.topMargin = -(mHeaderViewHeight);
		// mHeaderView.setLayoutParams(params1);
		addView(mHeaderView, params);
	}

	private void addFooterView() {
		// footer view
		mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.lqc_refresh_load_footer, this, false);
		mFooterImageView = (ImageView) mFooterView.findViewById(R.id.lqc_load_image);
		mFooterTextView = (TextView) mFooterView.findViewById(R.id.lqc_load_text);
		mFooterProgressBar = (ImageView) mFooterView.findViewById(R.id.lqc_load_progress);
		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,mFooterViewHeight);
		addView(mFooterView, params);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// footer view 在此添加保证添加到linearlayout中的最后
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView,GridView and so on;or init ScrollView
	 * 
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException("Childs must be morn than 3!");
		}
		View view;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}else if (view instanceof ScrollView) {
				mScrollView = (ScrollView) view;
			}else if(view instanceof RecyclerView){
                mRecyclerView= (RecyclerView) view;
            }
		}
		if (mAdapterView == null && mScrollView == null && mRecyclerView==null) {
			throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 是向下运动,< 0是向上运动
			int deltaY = y - mLastMotionY;
			if(deltaY>0 && !headEnable) return false;
			if(deltaY<0 && !footEnable) return false;
			
			if(deltaY<10 && deltaY>-10)return false;
			if (isRefreshViewScroll(deltaY)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}


	int mY;
	/*
	 * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
	 * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (mLock) {
//			return true;
//		}
		int y = (int) event.getRawY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// onInterceptTouchEvent已经记录
			// mLastMotionY = y;
			 mY= (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			int mLastY = (int) event.getY();


			if (mPullState == PULL_DOWN_STATE) {//执行下拉
				if (onRefreshDy != null){
					onRefreshDy.onDistance("gone","x");
				}
				headerPrepareToRefresh(deltaY);
				// setHeaderPadding(-mHeaderViewHeight);
			} else if (mPullState == PULL_UP_STATE) {//执行上拉
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
			if (onRefreshDy != null){
				onRefreshDy.onDistance("x","visible");
			}
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					// 开始刷新
					headerRefreshing(true);
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= mHeaderViewHeight + mFooterViewHeight) {
					// 开始执行footer 刷新
					footerRefreshing(true);
				} else {
					// 还没有执行刷新，重新隐藏
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 是否应该到了父View,即PullToRefreshView滑动
	 * 
	 * @param deltaY
	 *            , deltaY > 0 是向下运动,< 0是向上运动
	 * @return
	 */
	private boolean isRefreshViewScroll(int deltaY){
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			return false;
		}
		//对于ListView和GridView
		if (mAdapterView != null) {
			// 子view(ListView or GridView)滑动到最顶端
			if (deltaY > 0) {

				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 如果mAdapterView中没有数据,不拦截
					return false;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 8) {
					//这里之前用3可以判断,但现在不行,还没找到原因
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
				if (lastChild == null) {
					// 如果mAdapterView中没有数据,不拦截
					return false;
				}
				// 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
				// 等于父View的高度说明mAdapterView已经滑动到最后
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}else if (mScrollView != null) {// 对于ScrollView
			// 子scroll view滑动到最顶端
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY()) {
				mPullState = PULL_UP_STATE;
				return true;
			}
		}else if(mRecyclerView != null){
            RecyclerView.LayoutManager layoutManager=mRecyclerView.getLayoutManager();
            if(layoutManager instanceof LinearLayoutManager){
                LinearLayoutManager manager= (LinearLayoutManager) layoutManager;
                View child = manager.getChildAt(0);
                if (child == null) {
                    // 没有数据,不拦截
                    return false;
                }
                if (manager.findFirstVisibleItemPosition() == 0 && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            }else if(layoutManager instanceof GridLayoutManager){
                GridLayoutManager manager= (GridLayoutManager) layoutManager;
                View child = manager.getChildAt(0);
                if (child == null) {
                    // 没有数据,不拦截
                    return false;
                }
                if (manager.findFirstVisibleItemPosition() == 0 && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
            }
        }
		return false;
	}

	
	/**
	 * header 准备刷新,手指移动过程,还没有释放
	 * 
	 * @param deltaY
	 *            ,手指滑动的距离
	 */
	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			mHeaderTextView.setText(R.string.lqc_refresh_release_label);
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(getFlipAnimation());
			mHeaderState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(getFlipAnimation());
			// mHeaderImageView.
			mHeaderTextView.setText(R.string.lqc_refresh_pull_label);
			mHeaderState = PULL_TO_REFRESH;
		}
	}

	/**
	 * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
	 * 高度是一样，都是通过修改header view的topmargin的值来达到
	 * 
	 * @param deltaY
	 *            ,手指滑动的距离
	 */
	private void footerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 如果header view topMargin 的绝对值大于或等于header + footer 的高度
		// 说明footer view 完全显示出来了，修改footer view 的提示状态
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH) {
			mFooterTextView.setText(R.string.lqc_refresh_footer_release_label);
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(getFlipAnimation());
			mFooterState = RELEASE_TO_REFRESH;
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(getFlipAnimation());
			mFooterTextView.setText(R.string.lqc_refresh_footer_pull_label);
			mFooterState = PULL_TO_REFRESH;
		}
	}

    private Animation getFlipAnimation(){
        RotateAnimation mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        return mFlipAnimation;
    }

	/**
	 * 修改Header view top margin的值
	 * 
	 * @param deltaY
	 */
	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		//这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
		//表示如果是在上拉后一段距离,然后直接下拉
		if(deltaY>0&&mPullState == PULL_UP_STATE&&Math.abs(params.topMargin) <= mHeaderViewHeight){
			return params.topMargin;
		}
		//同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
		if(deltaY<0&&mPullState == PULL_DOWN_STATE&&Math.abs(params.topMargin)>=mHeaderViewHeight){
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}
	
	public void simulationHeadRefreshing(){
		headerRefreshing(false);
	}
	
	public void simulationFooterRefreshing(){
		footerRefreshing(false);
	}

	/**
	 * header refreshing
	 * 
	 */
	private void headerRefreshing(boolean listenerEnable){
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView.setText(R.string.lqc_refresh_refreshing_label);
		if (mFreshListener != null && listenerEnable) {
			mFreshListener.onRefresh();
		}
	}

	/**
	 * footer refreshing
	 * 
	 */
	private void footerRefreshing(boolean listenerEnable){
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		mFooterImageView.setVisibility(View.GONE);
		mFooterImageView.clearAnimation();
		mFooterImageView.setImageDrawable(null);
		mFooterProgressBar.setVisibility(View.VISIBLE);
		mFooterTextView.setText(R.string.lqc_refresh_footer_refreshing_label);
		if (mFreshListener != null && listenerEnable) {
			mFreshListener.onLoad();
		}
	}

	/**
	 * 设置header view 的topMargin的值
	 * 
	 * @param topMargin
	 *            ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
	 */
	private void setHeaderTopMargin(int topMargin){
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	/**
	 * header view 完成更新后恢复初始状态
	 * 
	 */
	public void onHeaderRefreshComplete(){
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderImageView.setVisibility(View.VISIBLE);
		mHeaderImageView.setImageResource(R.mipmap.lqc_arrow_down);
		mHeaderTextView.setText(R.string.lqc_refresh_pull_label);
		mHeaderProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mHeaderState = PULL_TO_REFRESH;
	}

	/**
	 * footer view 完成更新后恢复初始状态
	 */
	public void onFooterRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mFooterImageView.setVisibility(View.VISIBLE);
		mFooterImageView.setImageResource(R.mipmap.lqc_arrow_up);
		mFooterTextView.setText(R.string.lqc_refresh_footer_pull_label);
		mFooterProgressBar.setVisibility(View.GONE);
		mFooterState = PULL_TO_REFRESH;
	}

	/**
	 * 获取当前header view 的topMargin
	 * 
	 */
	private  int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}


	public void setOnRefreshLoadListener(OnRefreshLoadListener listener,boolean headE,boolean footE) {
		mFreshListener = listener;
		headEnable=headE;
		footEnable=footE;
	}

	public interface OnRefreshLoadListener {
		void onRefresh();
		void onLoad();
	}

	/**
	 * 自己写
	 */
	private OnRefreshDy onRefreshDy;
	public void setOnRefreshDy(OnRefreshDy onRefreshDy) {
		this.onRefreshDy = onRefreshDy;
	}


	public interface OnRefreshDy{
		public void onDistance(String gone, String visible);
	}

}
