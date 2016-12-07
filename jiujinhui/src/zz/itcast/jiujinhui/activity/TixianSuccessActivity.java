package zz.itcast.jiujinhui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class TixianSuccessActivity extends BaseActivity {
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.money)
	private TextView money;
	@ViewInject(R.id.shouxumoney)
	private TextView shouxumoney;
	private String countString;
	private String shouxuString;
	@ViewInject(R.id.wancheng)
	private RelativeLayout wancheng;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		countString = bundle.getString("count");
		shouxuString = bundle.getString("shouxufei");
		money.setText(countString);
		shouxumoney.setText(shouxuString);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("提现详情");
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.tixiansuccess;
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.wancheng:
			
			finish();
			break;
		default:
			break;
		}
	}
}
