package zz.itcast.jiujinhui.fragment;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class TradeSaleFragment extends BaseFragment {


	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		ViewUtils.inject(this, view);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
		
		
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.tradesale_fragment;
	}
	
	

}
