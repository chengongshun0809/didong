package zz.itcast.jiujinhui.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTiXianActivity extends BaseActivity {

	
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
         switch (v.getId()) {
         case R.id.tv_back:
 			finish();
 			break;

		default:
			break;
		}
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.metixian_activity;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
 tv_back.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("提现");
		tv__title.setTextSize(22);
	}

}
