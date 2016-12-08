package zz.itcast.jiujinhui.fragment;

import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TradeAllFragment extends BaseFragment {
	@ViewInject(R.id.cominglistview)
	private ListView cominglistview;
	
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.Rl_jindu)
	private RelativeLayout Rl_jindu;
	
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.tradeall_fragment;
	}

}
